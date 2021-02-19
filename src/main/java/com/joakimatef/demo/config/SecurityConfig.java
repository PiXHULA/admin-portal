package com.joakimatef.demo.config;

import com.joakimatef.demo.filters.JwtRequestFilter;
import com.joakimatef.demo.service.security.JpaUserDetailService;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *  Class used to configure Spring security
 * @author  Joakim Önnhage
 * @version 1.0
 * @since   2021-02-15
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Used for configuring {@link AuthenticationManager}
     */
    private final JpaUserDetailService jpaUserDetailService;
    /**
     * Used for configuring {@link HttpSecurity} to add filter for JWT
     */
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JpaUserDetailService jpaUserDetailService, JwtRequestFilter jwtRequestFilter){
        this.jpaUserDetailService = jpaUserDetailService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configuring the authentication manager with {@link JpaUserDetailService}
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaUserDetailService);
    }

    /**
     * Security method configuring the requests done form http
     * Filter added for JWT
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize ->
                authorize
                .antMatchers("/",
                        "/resources/**",
                        "/index*",
                        "/static/**",
                        "/*.js",
                        "/*.json",
                        "/*.ico",
                        "/*.png").permitAll()
                .mvcMatchers(HttpMethod.POST,"/authenticate").permitAll())
                .cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactory.createDelegatingPasswordEncoder();
    }


}
