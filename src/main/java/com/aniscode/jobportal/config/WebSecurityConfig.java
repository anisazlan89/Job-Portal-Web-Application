package com.aniscode.jobportal.config;

import com.aniscode.jobportal.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomAuthSuccessHandler customAuthSuccessHandler;
    private final String[] allowedPaths = {
            "/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/img/**"
    };

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthSuccessHandler customAuthSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;

        this.customAuthSuccessHandler = customAuthSuccessHandler;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //provider
        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(auth ->
            auth
                    .requestMatchers(allowedPaths).permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    .anyRequest().authenticated()
        )
                .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
                );

        http.formLogin(form -> form.loginPage("/login").permitAll()
                .successHandler(customAuthSuccessHandler))
                .logout(logout ->
                        logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")).cors(Customizer.withDefaults()).csrf(csrf->csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        //create an instance of DaoAuthentication"Provider"
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
        //tells Spring how to check the raw password from the form against the stored hash in database.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
