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
    
	 @Override public void configure(WebSecurity web) throws Exception {		 
		 web.ignoring().antMatchers("/xmeme"); 
		 web.ignoring().antMatchers("/xmeme/memes"); 
		 web.ignoring().antMatchers("/xmeme/meme/**");
		 web.ignoring().antMatchers("/user/register");
		 web.ignoring().antMatchers("/v2/api-docs");
	 }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers("/xmeme/delete/**").hasAuthority("ADMIN")
			.antMatchers("/xmeme/edit").hasAuthority("ADMIN")
			.antMatchers("/user/get").hasAuthority("ADMIN")
			.antMatchers("/user/activate/**").hasAuthority("ADMIN")
			.antMatchers("xmeme/post").hasAnyAuthority("ADMIN", "USER")
			.anyRequest().authenticated()
			.and() 
			.formLogin().permitAll().defaultSuccessUrl("/xmeme/memes")
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/403");	
    }
   
}
