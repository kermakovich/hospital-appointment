package solvd.laba.ermakovich.ha.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import solvd.laba.ermakovich.ha.web.security.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    private static final String[] permitGetPatient;
    private static final String[] permitDeletePatient;
    private static final String[] permitPostPatient;
    private static final String[] permitPutPatient;
    private static final String[] permitGetDoctor;
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
                .requestMatchers(HttpMethod.GET, permitGetDoctor).hasAnyRole("DOCTOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, permitGetPatient).hasAnyRole("PATIENT", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, permitDeletePatient).hasAnyRole("PATIENT", "ADMIN")
                .requestMatchers(HttpMethod.POST, permitPostPatient).hasAnyRole("PATIENT", "ADMIN")
                .requestMatchers(HttpMethod.PUT, permitPutPatient).hasAnyRole("PATIENT", "ADMIN")
                .anyRequest().hasRole("ADMIN")
                .and()
                .addFilterAfter(jwtFilter, ExceptionTranslationFilter.class)
                .build();
    }

    static {
        permitGetPatient = new String[] {
                "/api/v1/patients/*/appointments",
                "/api/v1/reviews/*"};
        permitDeletePatient = new String[] {
                "/api/v1/appointments/*",
                "/api/v1/reviews/*"};
        permitPostPatient = new String[] {
                "/api/v1/patients/*/appointments",
                "/api/v1/reviews"};
        permitPutPatient = new String[] {"/api/v1/reviews/*"};
        permitGetDoctor = new String[] {"/api/v1/doctors/*/appointments"};
        permitGetAll = new String[] {
                "/api/v1/doctors/*/reviews",
                "/api/v1/doctors",
                "/api/v1/doctors/*/schedule"
        };
        permitPostAll = new String[] {
                "/api/v1/users/login",
                "/api/v1/users/refresh",
                "/api/v1/patients"
        };
    }

    public static String[] getPermitAll() {
        int fal = permitPostAll.length;
        int sal = permitGetAll.length;
        String[] result = new String[fal + sal];
        System.arraycopy(permitPostAll, 0, result, 0, fal);
        System.arraycopy(permitGetAll, 0, result, fal, sal);
        return result;
    }


}
