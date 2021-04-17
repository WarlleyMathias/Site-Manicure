package com.warlley.feminine.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementsUserDetailsService UserDetailsService;

		    @Override
		    protected void configure(HttpSecurity http) throws Exception{
		        http.csrf().disable().authorizeRequests()
		                .antMatchers().permitAll()
		                .antMatchers(HttpMethod.GET, "/agendar").hasAnyRole("USER","ADMIN")
		                .antMatchers(HttpMethod.POST, "/agendar").hasAnyRole("USER","ADMIN")
		                .antMatchers(HttpMethod.GET, "/agendamentos").hasRole("ADMIN")
		                .antMatchers(HttpMethod.POST, "/agendamentos").hasRole("ADMIN")
		                .and().formLogin().loginPage("/login").permitAll()
		                .failureUrl("/loginError")
		                .and().logout().logoutSuccessUrl("/");
		    }
		    
		    @Override
			protected void configure(AuthenticationManagerBuilder auth) throws Exception{
				auth.userDetailsService(UserDetailsService)
				.passwordEncoder(new BCryptPasswordEncoder());
			}
		    
		    @Override
		    public void configure(WebSecurity web) throws Exception{
		        web.ignoring().antMatchers("/css/principal.css");
		        web.ignoring().antMatchers("/imagens/salao.jpg");
		    }
}
