package com.metacube.training.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration 
@EnableWebMvcSecurity 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// To implement In Memory authentication	
    @Autowired							
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception 
    {
    	// In memory authentication with user name and password		
    	auth.inMemoryAuthentication().withUser("bhavika@gmail.com").password("123456").roles("USER");
    }
    
    // To implement Http Basic Security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
   	
    	http
    	.authorizeRequests().antMatchers("/admin/*").access("hasRole('ROLE_USER')")
    	.and().formLogin()
	    	.loginPage("/login") 
	    	.loginProcessingUrl("/Login") 
	        .defaultSuccessUrl("/admin/home")
	        .usernameParameter("username")
	        .passwordParameter("password")
	        .failureUrl("/error")	        
        .and()
			.logout().logoutSuccessUrl("/login?logout")
			.logoutUrl("/Logout")
		.and()
			.csrf();
    }
}