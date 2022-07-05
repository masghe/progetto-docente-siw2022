package it.uniroma3.siw.authentication;


import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {


	@Autowired
	DataSource datasource;

	/**
	 * Metedo dove vado a scrivere quali sono le risorse protette, a quali ruoli sono associati,
	 * quali sono le pagine di login,logout ecc...
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		//Qui definiziamo chi può accedere a cosa
		.authorizeRequests()
		//chiunque (autenticato o no) può accedere alle pagine index,login,register, ai css e alle immagini
		.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**","/images/**").permitAll()
		//chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register
		.antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
		//solo gli utenti autenticati con il ruolo di ADMIN possono accedere a risorse con path /admin/**
		.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
		.antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
		//Tutti gli utenti autenticati possono accedere alle pagine rimanenti
		.anyRequest().authenticated()


		//Login paragraph : qui definiamo come è gestita l'autenticazione
		//Usiamo il protocollo formLogin
		.and().formLogin()
		//La pagina di login si trova a /login
		//NOTA: Spring gestisce il post di login automaticamente
//		.loginPage("/login")
//		//Se il login ha successo, si viene rediretti al path /home
//		.defaultSuccessUrl("/home")
//
//		//Logout paragraph : qui definiamo il logout
//		.and()
//		.logout()
//		//Il logout è ativato con una richiesta GET a "/logout"
//		.logoutUrl("/logout")
//		//In caso di successo, si vine reindirizzati alla /index page
//		.logoutSuccessUrl("/login").invalidateHttpSession(true).clearAuthentication(true).permitAll();

		
		.loginPage("/login")
		//Se il login ha successo, si viene rediretti al path /home
		.defaultSuccessUrl("/home")

		//Logout paragraph : qui definiamo il logout
		.and()
		.logout()
		//Il logout è ativato con una richiesta GET a "/logout"
		.logoutUrl("/logout")
		//In caso di successo, si vine reindirizzati alla /index page
		.logoutSuccessUrl("/index")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.clearAuthentication(true).permitAll();
	}

	/**
	 *Questo metodo serve a dire DOVE memorizziamo le credenziali E COME facciamo ad accedere alle credenziali
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//facciamo un'autenticazione verso una sorgente jdbc (su un DB sostanzialmente)
		auth.jdbcAuthentication()
		//Indico quale è la datasource. Le infomarmazioni della nostra datasource sono quelle che noi abbiamo definito nel file di configurazione(il nostro DB)
		.dataSource(this.datasource)
		//Query per recuperare l'username e ruolo 
		.authoritiesByUsernameQuery("SELECT username, ruolo FROM credentials WHERE username=?")
		//Query per recuperare l'username e password
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



}
