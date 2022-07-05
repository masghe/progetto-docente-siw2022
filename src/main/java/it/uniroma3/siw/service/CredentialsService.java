package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired
	private CredentialsRepository credentialsRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Transactional
	public void saveCredentials(Credentials credentials) {
		credentials.setRuolo(Credentials.DEFAULT_ROLE);

		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));

		credentialsRepository.save(credentials);
	}

	public Credentials getCredentials(long id) {
		return credentialsRepository.findById(id).get();
	}

	public Credentials getCredentials(String username) {
		return credentialsRepository.findByUsername(username);
	}

	@Transactional
	public void deleteCredentials(String username) {
		Credentials credentials = this.credentialsRepository.findByUsername(username);
		this.credentialsRepository.delete(credentials);
	}

	@Transactional
	public void update(Credentials credentials) {	
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		credentialsRepository.save(credentials);
	}

	public List<Credentials> getAllCredentials() {

		Iterable<Credentials> iterable = this.credentialsRepository.findAll();
		List<Credentials> result = new ArrayList<>();
		for(Credentials credentials : iterable)
			if(credentials.getRuolo().equals(Credentials.DEFAULT_ROLE))
				result.add(credentials);
		return result;
	}
}
