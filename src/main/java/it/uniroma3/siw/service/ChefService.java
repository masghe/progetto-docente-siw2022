package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository chefRepository;

	// @Transactional solo quando faccio operazioni di aggiornamento su DB
	// Per tutte le altre operazioni non serve @Transactional

	@Transactional
	public void save(Chef chef) {
		chefRepository.save(chef);
	}

	@Transactional
	public void delete(Chef chef) {
		this.chefRepository.delete(chef);
	}

	public Chef findById(Long id) {
		return chefRepository.findById(id).get();
	}

	public Chef alreadyExists(Chef chef) {
		return chefRepository.findByNomeAndCognome(chef.getNome(), chef.getCognome());
	}

	public List<Chef> getAllChef() {
		List<Chef> chef = new ArrayList<>();
		for (Chef c : chefRepository.findAll()) {
			chef.add(c);
		}
		return chef;
	}
}
