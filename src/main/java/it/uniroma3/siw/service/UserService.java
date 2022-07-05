package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void saveUser(User u) {
		userRepository.save(u);
	}

	public User getUser(Long id) {
		return this.userRepository.findById(id).get();
	}

	public User alreadyExists(User user) {
		return userRepository.findByNomeAndCognome(user.getNome(), user.getCognome());
	}




	public List<User> findAllUsers() {
		List<User> users = new ArrayList<User>();

		for(User u : this.userRepository.findAll()) {
			users.add(u);
		}
		return users;
	}

}
