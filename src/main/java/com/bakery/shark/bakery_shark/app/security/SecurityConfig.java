package com.bakery.shark.bakery_shark.app.security;

import com.bakery.shark.bakery_shark.app.user.SpringDataUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/js/**", "/images/**", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
        http.authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/", "/user/create-user","/activate/*", "/offer").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/main").hasAnyRole("ADMIN")
                .antMatchers("/bakery/ingredients").hasAnyRole("ADMIN")
                .antMatchers("/bakery/dashboard").hasAnyRole("ADMIN")
                .antMatchers("/kitchen/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/cashRegister/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/lostProducts/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/ingredient/**").hasAnyRole("ADMIN")
                .antMatchers("/recipe/**").hasAnyRole("ADMIN")
                .antMatchers("/recipeItem/**").hasAnyRole("ADMIN")
                .antMatchers("/product/**").hasAnyRole("ADMIN")
                .antMatchers("/stock/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/user/askForRights").hasAnyRole("USER")
                .antMatchers("/user/askForUserRights").hasAnyRole("GUEST")
                .antMatchers("/admin/addAdminRights/**").hasAnyRole("ADMIN")
                .antMatchers("/admin/createUser/**").hasAnyRole("ADMIN")
                .antMatchers("/dashboard/**").hasAnyRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .and().logout().logoutSuccessUrl("/login")
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");
    }
}
