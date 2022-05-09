package com.example.jobsrest.config;

import com.example.jobsrest.entity.UserType;
import com.example.jobsrest.security.CurrentUserDetailServiceImpl;
import com.example.jobsrest.security.JwtAuthenticationEntryPoint;
import com.example.jobsrest.security.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CurrentUserDetailServiceImpl userDetails;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/user/activate/{email}/{token}").permitAll()
                .antMatchers(HttpMethod.POST, "/users").hasAnyAuthority(UserType.EMPLOYER.name())
                .antMatchers(HttpMethod.DELETE, "/user/delete/{id}").hasAnyAuthority("USER", "ADMIN", "EMPLOYER")
                .antMatchers(HttpMethod.GET, "/user/account").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/employer/account").hasAnyAuthority(UserType.EMPLOYER.name())
                .antMatchers(HttpMethod.POST, "/user/updatePassword").hasAnyAuthority("USER", "ADMIN", "EMPLOYER")
                .antMatchers(HttpMethod.POST, "/user/update").hasAnyAuthority("USER", "ADMIN", "EMPLOYER")
                .antMatchers(HttpMethod.GET, "/admin/getUserById/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/edit/{id}").hasAnyAuthority("USER", "ADMIN", "EMPLOYER")
                .antMatchers(HttpMethod.GET, "/admin/allUsers").hasAnyAuthority("ADMIN")
                     .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
}



