package solvd.laba.ermakovich.ha.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import solvd.laba.ermakovich.ha.web.security.expression.CustomMethodSecurityExpressionHandler;
import solvd.laba.ermakovich.ha.web.security.jwt.JwtFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity( prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final ApplicationContext applicationContext;
    private static final String[] permitGetAll;
    private static final String[] permitPostAll;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, permitGetAll).permitAll()
                .requestMatchers(HttpMethod.POST, permitPostAll).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(jwtFilter, ExceptionTranslationFilter.class)
                .build();
    }

    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler() {
        var expressionHandler = new CustomMethodSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    static {
        permitGetAll = new String[] {
                "/api/v1/doctors/*/reviews",
                "/api/v1/doctors",
                "/api/v1/doctors/*/schedule",
                "/swagger-ui/**",
                "/v3/api-docs/**"
        };
        permitPostAll = new String[] {
                "/api/v1/users/login",
                "/api/v1/users/refresh",
                "/api/v1/patients",
        };
    }

}
