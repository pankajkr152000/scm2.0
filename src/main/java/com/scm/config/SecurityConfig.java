package com.scm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.security.handlers.OAuthSuccessHandler;
import com.scm.service.impl.SecurityCustomUserDetailsServiceImpl;


// @Configuration
// public class SecurityConfig {

//     private final SecurityCustomUserDetailsService userDetailsService;
//     private final SecurityFilterChain securityFilterChain;

//     public SecurityConfig(SecurityCustomUserDetailsService userDetailsService, SecurityFilterChain securityFilterChain) {
//         this.userDetailsService = userDetailsService;
//         this.securityFilterChain = securityFilterChain;
//     }

//     // // user create and login using in memory service
//     // @Bean
//     // public UserDetailsService userDetailsService() {

//     //     UserDetails user1 = User
//     //     .withDefaultPasswordEncoder()
//     //                        .withUsername("admin")
//     //                        .password("admin")
//     //                        .build();

//     //     UserDetails user2 = User
//     //                        .withUsername("admin")
//     //                        .password("admin")
//     //                        .roles("ADMIN", "USER")
//     //                        .build();

//     //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);

//     //     return inMemoryUserDetailsManager;
//     // }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//         httpSecurity.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
//             authorizeHttpRequestsCustomizer.requestMatchers("/home");
//         });

//         return httpSecurity.build();
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
//         daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//         daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

//         return daoAuthenticationProvider;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

// }

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final SecurityCustomUserDetailsServiceImpl customUserDetailsServiceImpl;
    private final OAuthSuccessHandler authSuccessHandler;
    private final PasswordEncoderConfig passwordEncoder;

    public SecurityConfig(
            SecurityCustomUserDetailsServiceImpl customUserDetailsServiceImpl,
            OAuthSuccessHandler authSuccessHandler,
            PasswordEncoderConfig passwordEncoder) {

        log.info("Initializing SecurityConfig");

        this.customUserDetailsServiceImpl = customUserDetailsServiceImpl;
        this.authSuccessHandler = authSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authentication Provider configuration
     */
    @SuppressWarnings("deprecation")
    @Bean
    public AuthenticationProvider authenticationProvider() {

        log.info("Creating DaoAuthenticationProvider");

        DaoAuthenticationProvider daoAuthenticationProvider =
                new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(customUserDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());

        log.info("DaoAuthenticationProvider configured with custom UserDetailsService and PasswordEncoder");

        return daoAuthenticationProvider;
    }

    /**
     * Main Security Filter Chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring SecurityFilterChain");
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/**",                 // ✅ API endpoints
                    "/login", "/signup", "/do-signup",
                    "/oauth2/**", 
                    "/home","/", 
                    "/css/**", "/js/**", "/png/**", "/jpeg/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            );
            // .formLogin(Customizer.withDefaults());

        http.formLogin(formLogin -> {
            log.info("Configuring Form Login");

            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/dashboard");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            log.debug("Form login configured with custom login page and parameters");
        });

        /**
         * OAuth2 Login Configuration
         */
        http.oauth2Login(oauth2 -> {
            log.info("Configuring OAuth2 Login");

            oauth2.loginPage("/login");
            oauth2.successHandler(authSuccessHandler);

            log.debug("OAuth2 success handler attached");
        });

        /**
         * CSRF Configuration
         */
        http.csrf(csrf -> {
            log.warn("CSRF protection is DISABLED");
            csrf.disable();
        });

        /**
         * Logout Configuration
         */
        http.logout(logout -> {
            log.info("Configuring Logout");

            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");

            log.debug("Logout configured successfully");
        });

        log.info("SecurityFilterChain configuration completed");

        return http.build();
    }
}
