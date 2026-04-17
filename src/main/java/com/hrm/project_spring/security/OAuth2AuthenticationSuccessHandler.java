package com.hrm.project_spring.security;

import com.hrm.project_spring.entity.Role;
import com.hrm.project_spring.entity.User;
import com.hrm.project_spring.repository.RoleRepository;
import com.hrm.project_spring.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub");

        if (email == null) {
            response.sendRedirect("http://localhost:3000/login?error=email_not_found");
            return;
        }
        // 1. Tìm user theo providerId + provider
        User user = userRepository.findByProviderIdAndProvider(googleId, "GOOGLE")
            .orElse(null);

        if (user == null) {
            // 2. Nếu không thấy, tìm theo email để liên kết tài khoản
            user = userRepository.findByEmail(email).orElse(null);
            
            if (user != null) {
                // Liên kết tài khoản hiện có với Google
                user.setProvider("GOOGLE");
                user.setProviderId(googleId);
                if (user.getFullName() == null) user.setFullName(name);
                user = userRepository.save(user);
            } else {
                // 3. Nếu vẫn không thấy, tạo user mới
                User newUser = new User();
                newUser.setEmail(email);
                // Tạo username duy nhất từ email
                String baseUsername = email.split("@")[0];
                newUser.setUsername(baseUsername + "_" + System.currentTimeMillis());
                newUser.setFullName(name);
                newUser.setStatus("ACTIVE");
                newUser.setEmailVerified(true);
                newUser.setProvider("GOOGLE");
                newUser.setProviderId(googleId);
                
                // Gán vai trò mặc định là STUDENT
                roleRepository.findByCode("STUDENT").ifPresent(role -> 
                    newUser.setRoles(Collections.singleton(role))
                );

                user = userRepository.save(newUser);
            }
        }

        String token = jwtService.generateToken((UserDetails) user);
        
        String targetUrl = "http://localhost:3000/login?token=" + token;
        response.sendRedirect(targetUrl);
    }
}
