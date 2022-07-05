package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Chef;


public interface ChefRepository extends CrudRepository<Chef, Long> {

	public Chef findByNomeAndCognome(String nome,String cognome);

}
