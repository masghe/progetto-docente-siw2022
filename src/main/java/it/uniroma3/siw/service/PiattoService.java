package it.uniroma3.siw.service;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	PiattoRepository piattoRepository;

	//@Transactional solo quando faccio operazioni di aggiornamento(scrittura) su DB
	//Per tutte le altre operazioni di lettura non serve @Transactional


	@Transactional
	public void save (Piatto piatto) {
		piattoRepository.save(piatto);
	}

	@Transactional
	public void delete(Piatto piatto) {
		piattoRepository.delete(piatto);
	}

	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}

	//restituisce tutti i piatti
	public List<Piatto> getAllPiatti(){
		List<Piatto> piatti = new ArrayList<>();
		for(Piatto p : piattoRepository.findAll()) {
			piatti.add(p);
		}
		return piatti;
	}


	//restituisce tutti i piatti tramite l'Id del Buffet
	public List<Piatto> getAllPiatti(Buffet loggedBuffet){
		Iterable<Piatto> iterable = this.piattoRepository.findByBuffet(loggedBuffet);
		List<Piatto> piatti = new ArrayList<>();
		for(Piatto p : iterable) {
			piatti.add(p);
		}
		return piatti;
	}

	//PROVARE QUESTO METODO
//	public List<Ingrediente> getAllIngredienti(Piatto loggedPiatto){
//		List<Ingrediente> listaIngredienti = loggedPiatto.getIngredienti();
//		List<Ingrediente> ingredienti = new ArrayList<>();
//		for(Ingrediente i : listaIngredienti) {
//			ingredienti.add(i);
//		}
//		return listaIngredienti;
//	}
	
	public Piatto alreadyExists(Piatto piatto) {
		return piattoRepository.findByNome(piatto.getNome());
	}



}
