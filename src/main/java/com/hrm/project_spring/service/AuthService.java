package com.hrm.project_spring.service;

import com.hrm.project_spring.dto.auth.AuthResponse;
import com.hrm.project_spring.dto.auth.ChangePasswordRequest;
import com.hrm.project_spring.dto.auth.LoginRequest;
import com.hrm.project_spring.dto.user.UpdateProfileRequest;
import com.hrm.project_spring.dto.user.UserRequest;
import com.hrm.project_spring.dto.user.UserResponse;
import com.hrm.project_spring.entity.User;
import com.hrm.project_spring.repository.RoleRepository;
import com.hrm.project_spring.repository.UserRepository;
import com.hrm.project_spring.security.JwtService;
import com.hrm.project_spring.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .status("ACTIVE")
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthResponse.builder()
                .token(jwtToken)
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, " USER NOT FOUND"));
        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthResponse.builder()
                .token(jwtToken)
                .message("User logged in successfully")
                .build();
    }

    public AuthResponse loginWithGoogle(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
            Map<String, Object> userInfo = restTemplate.getForObject(url, Map.class);

            if (userInfo == null || userInfo.get("email") == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token Google không hợp lệ");
            }

            String email = (String) userInfo.get("email");
            String name = (String) userInfo.get("name");
            String googleId = (String) userInfo.get("sub");

            User user = userRepository.findByProviderIdAndProvider(googleId, "GOOGLE")
                .orElseGet(() -> userRepository.findByEmail(email)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setEmail(email);
                        newUser.setUsername(email.split("@")[0] + "_" + System.currentTimeMillis());
                        newUser.setFullName(name);
                        newUser.setStatus("ACTIVE");
                        newUser.setEmailVerified(true);
                        newUser.setProvider("GOOGLE");
                        newUser.setProviderId(googleId);
                        
                        // Gán vai trò mặc định là STUDENT
                        roleRepository.findByCode("STUDENT").ifPresent(role ->
                            newUser.setRoles(Collections.singleton(role))
                        );

                        return userRepository.save(newUser);
                    }));

            String jwtToken = jwtService.generateToken((UserDetails) user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .message("Đăng nhập Google thành công")
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi xác thực Google: " + e.getMessage());
        }
    }

    public UserResponse getProfile() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());
        response.setRoles(
               user.getRoles()
                   .stream()
                   .map(role -> role.getCode())
                   .toList());
        response.setPermissions(
               user.getRoles()
                   .stream()
                   .flatMap(role -> role.getPermissions().stream())
                   .map(permission -> permission.getCode())
                   .distinct()
                   .toList());
        return response;
    }

    public AuthResponse logout() {
        SecurityContextHolder.clearContext();
        return AuthResponse.builder()
                .message("Logged out successfully")
                .build();
    }


    public void changePassword(ChangePasswordRequest request) {


        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mật khẩu cũ không chính xác");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public UserResponse updateProfile(UpdateProfileRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không tồn tại"));

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email đã được sử dụng");
        }
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        return getProfile();
    }
}
