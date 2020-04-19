package jwdesign.configuration;

import jwdesign.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity webSecurity) {
		webSecurity
				.ignoring()
				.antMatchers("/resources/**");
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeRequests()
				.antMatchers(
						"/",
						"/login",
						"/register",
						"/console",
						"/open/**").permitAll()
				.antMatchers(
						"/user/**").hasRole("USER")
				.antMatchers(
						"/admin/**").hasRole("ADMIN")



				.and()
				.csrf().ignoringAntMatchers("/**")

				.and()
				.headers().frameOptions().sameOrigin()

				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/redirectLogin")
				.failureUrl("/login")
				.and()
				.logout()
				.logoutSuccessUrl("/logout")
				.logoutUrl("/logout")
				.invalidateHttpSession(true);



	}
	@Autowired
	public AppUserService appUserService;

	@Autowired
	public DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		auth
				.userDetailsService(appUserService)
				.passwordEncoder(encoder)

				.and()
				.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(encoder);
	}
}
