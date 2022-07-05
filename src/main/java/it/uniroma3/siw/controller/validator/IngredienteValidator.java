	package it.uniroma3.siw.controller.validator;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.service.IngredienteService;


	@Component
	public class IngredienteValidator implements Validator{



		@Autowired
		IngredienteService ingredienteService;
		
		final Integer MAX_DESCRIZIONE_ORIGINE_LENGTH = 60;
	    final Integer MIN_DESCRIZIONE_ORIGINE_LENGTH = 6;
	    final Integer MAX_NAME_LENGTH = 60;
	    final Integer MIN_NAME_LENGTH = 2;

		@Override
		public boolean supports(Class<?> clazz) {
			return Ingrediente.class.equals(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			Ingrediente ingrediente = (Ingrediente) target;
			
			String nome = ingrediente.getNome().trim();
			String descrizione = ingrediente.getDescrizione().trim();
			String origine = ingrediente.getOrigine().trim();

			
			
			if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
	            errors.rejectValue("nome", "size.nome");
	        
	        if (descrizione.length() < MIN_DESCRIZIONE_ORIGINE_LENGTH || descrizione.length() > MAX_DESCRIZIONE_ORIGINE_LENGTH)
	            errors.rejectValue("descrizione", "size.descrizione");
	        
	        if (origine.length() < MIN_DESCRIZIONE_ORIGINE_LENGTH || origine.length() > MAX_DESCRIZIONE_ORIGINE_LENGTH)
	            errors.rejectValue("origine", "size.origine");

		}

	}



