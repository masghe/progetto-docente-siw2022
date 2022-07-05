package it.uniroma3.siw.service;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.BuffetRepository;

@Service
public class BuffetService {

	//@Transactional solo quando faccio operazioni di aggiornamento su DB
	//Per tutte le altre operazioni non serve @Transactional

	@Autowired
	private BuffetRepository buffetRepository;

	@Transactional
	public void save (Buffet buffet) {
		buffetRepository.save(buffet);
	}

	@Transactional
	public void delete(Buffet buffet) {
		buffetRepository.delete(buffet);
	}

	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}

	//restituisce tutti i buffet
	public List<Buffet> getAllBuffet(){
		List<Buffet> buffet = new ArrayList<>();
		for(Buffet b : buffetRepository.findAll()) {
			buffet.add(b);
		}
		return buffet;
	}

	//restituisce tutti i buffet tramite id dello chef
	public List<Buffet> getAllBuffet(Chef loggedChef ){
		Iterable<Buffet> iterable = this.buffetRepository.findByChef(loggedChef);
		List<Buffet> buffet = new ArrayList<>();
		for(Buffet b : iterable) {
			buffet.add(b);
		}
		return buffet;
	}

	public Buffet alreadyExists(Buffet buffet) {
		return buffetRepository.findByNome(buffet.getNome());
	}


}
