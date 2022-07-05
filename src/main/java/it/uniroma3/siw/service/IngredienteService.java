package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.IngredienteRepository;

@Service
public class IngredienteService {

	@Autowired
	IngredienteRepository ingredienteRepository;


	@Transactional
	public void save (Ingrediente ingrediente) {
		ingredienteRepository.save(ingrediente);
	}

	@Transactional
	public void delete(Ingrediente ingrediente) {
		ingredienteRepository.delete(ingrediente);
	}

	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}

	//restituisce tutti gli ingredienti
	public List<Ingrediente> getAllIngredienti(){
		List<Ingrediente> ingredienti = new ArrayList<>();
		for(Ingrediente i : ingredienteRepository.findAll()) {
			ingredienti.add(i);
		}
		return ingredienti;
	}


	//restituisce tutti gli ingredienti tramite l'Id del Piatto
	public List<Ingrediente> getAllIngredienti(Piatto loggedPiatto){

		Iterable<Ingrediente> iterable = this.ingredienteRepository.findByPiatto(loggedPiatto);
		List<Ingrediente> ingredienti = new ArrayList<>();
		for(Ingrediente i : iterable) {
			ingredienti.add(i);
		}
		return ingredienti;
	}
}
