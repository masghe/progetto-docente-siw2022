package it.uniroma3.siw.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Credentials {

	public static final String DEFAULT_ROLE ="DEFAULT";
	public static final String ADMIN_ROLE ="ADMIN";


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	private String ruolo;

	@NotBlank
	@Column(unique = true)
	private String username;

	@NotBlank
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
