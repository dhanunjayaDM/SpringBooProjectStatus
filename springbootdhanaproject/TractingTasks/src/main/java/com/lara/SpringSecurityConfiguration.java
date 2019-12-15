package com.lara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lara.service.UserDetailsServiceImpl;
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter
{
	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;
	
	 @Autowired
	 private JwtAuthenticationEntryPoint unauthorizedHandler;
	 
	 @Autowired
	 private RestAccessDeniedHandler accessDeniedHandler;
	 
	 @Bean
	 RestAccessDeniedHandler accessDeniedHandler() 
	 {
		return new RestAccessDeniedHandler();
	 }

	 @Bean 
	 public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
	    return new JwtAuthenticationFilter();
	 }	 
	 

	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) {
		  
	        auth.authenticationProvider(authenticationProvider());
	  }
	 
      @Override
      protected void configure(HttpSecurity http) throws Exception 
      {
         http.cors().and()
        .csrf().disable()   
        .authorizeRequests()
        .antMatchers("/loginjwt","/screen/downloadFile/**", "/trackingtasks/downloadTrackingTaskFile/**","/user/username/**", "/saveRole","/user/saveUser").permitAll()
        .antMatchers(HttpMethod.GET, "/project/getAllProjects","/user/getAllUsers", "/module/getAllModules","/screen/getAllScreen","/tasktype/getAllTaskTypes","/trackingtasks/getAllTrackingTask").hasAnyRole("ADMIN","USER")
        .antMatchers("/user/getAllUsers", "/user/saveUser","/project/**", "/module/**","/screen/**","/tasktype/**","/trackingtasks/**","/user/deleteUser").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
      }
      
      @Bean
      DaoAuthenticationProvider authenticationProvider(){
          DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
          System.out.println("daoAuthenticationProvider");
          System.out.println(daoAuthenticationProvider);
          daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
          daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
          return daoAuthenticationProvider;
      }
      
     
	  @Bean
      PasswordEncoder passwordEncoder() {
          return (PasswordEncoder) new BCryptPasswordEncoder();
      }
      

//
//    .antMatchers("/project/**", "/module/**","/screen/**","/tasktype/**").hasRole("ADMIN")
//    .antMatchers(HttpMethod.GET, "/project/getAllProjects", "/module/getAllModules","/screen/getAllScreen","/tasktype/getAllTaskTypes").hasRole("USER")
}
