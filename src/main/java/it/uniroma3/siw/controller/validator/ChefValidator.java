package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.service.ChefService;

@Component
public class ChefValidator implements Validator{



	@Autowired
	private ChefService chefService;

	final Integer MAX_NAME_COGNOME_LENGTH = 60;
	final Integer MIN_NAME_COGNOME_LENGTH = 2;
    final Integer MAX_NAZIONALITA_LENGTH = 60;
    final Integer MIN_NAZIONALITA_LENGTH = 1;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Chef.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Chef chef = (Chef) target;
		String nome = chef.getNome().trim();
        String cognome = chef.getCognome().trim();
        String nazionalita = chef.getNazionalita().trim();



		if(this.chefService.alreadyExists(chef) != null) 
			errors.rejectValue("nome","chef.duplicato");
		
        if (nome.length() < MIN_NAME_COGNOME_LENGTH || nome.length() > MAX_NAME_COGNOME_LENGTH)
            errors.rejectValue("nome", "size.nome");
        
        if (cognome.length() < MIN_NAME_COGNOME_LENGTH || cognome.length() > MAX_NAME_COGNOME_LENGTH)
            errors.rejectValue("cognome", "size.cognome");

        if (nazionalita.length() < MIN_NAZIONALITA_LENGTH || nazionalita.length() > MAX_NAZIONALITA_LENGTH)
            errors.rejectValue("nazionalita", "size.nazionalita");
	}

}

