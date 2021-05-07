package com.xmeme.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/xmeme/delete/**").hasAuthority("ADMIN")
            .antMatchers("/xmeme/edit/**").hasAnyAuthority("ADMIN")
            .antMatchers("/xmeme/post").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/user/register").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().permitAll().defaultSuccessUrl("/xmeme/memes", true)
            .and()
            .logout().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/403");
    }
    
	
	 @Override public void configure(WebSecurity web) throws Exception {
		 web.ignoring().antMatchers("/xmeme"); 
		 web.ignoring().antMatchers("/xmeme/memes"); 
		 web.ignoring().antMatchers("/xmeme/meme/**"); 
	 }
	
}
