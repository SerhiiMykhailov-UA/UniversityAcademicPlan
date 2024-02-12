package ua.foxminded.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ua.foxminded.service.UsersDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UsersDetailsService usersDetailsService;

	public SecurityConfig(UsersDetailsService usersDetailsService) {
		this.usersDetailsService = usersDetailsService;
	}
	
	//tune spring security and authorization
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
        		.antMatchers("/showUserPage/", "/admin/**").hasRole("ADMIN")
        		.antMatchers("/showUserPage/", "/teacher/**").hasRole("TEACHER")
        		.antMatchers("/showUserPage/", "/student/**").hasRole("STUDENT")
        		.antMatchers("/showUserPage/", "/stuff/**").hasRole("STUFF")
                .antMatchers("/auth/login", "/error", "/newApplicantsPage").permitAll()
                .anyRequest().hasAnyRole("ADMIN", "STUFF", "STUDENT", "TEACHER"))
             .formLogin(login -> login
            	.loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/showUserPage", true)
                .failureUrl("/auth/login?error"))
             .logout(logout -> logout
            		 .logoutUrl("/logout")
            		 .logoutSuccessUrl("/auth/login"));
		return http.build();
	}
	
	// tune our authentication
	@Bean
	protected DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(usersDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}
	
	// tune password encoder
	@Bean
	protected PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
