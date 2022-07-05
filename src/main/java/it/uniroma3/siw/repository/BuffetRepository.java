package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;

public interface BuffetRepository extends CrudRepository<Buffet, Long>{


	public List<Buffet> findByChef(Chef chef);

	public Buffet findByNome(String nome);



}
