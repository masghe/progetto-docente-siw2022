package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.PiattoService;

@Component
public class PiattoValidator implements Validator{



	@Autowired
	PiattoService piattoService;
	
	final Integer MAX_DESCRIZIONE_LENGTH = 60;
    final Integer MIN_DESCRIZIONE_LENGTH = 6;
    final Integer MAX_NAME_LENGTH = 60;
    final Integer MIN_NAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> clazz) {
		return Piatto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Piatto piatto = (Piatto) target;
		
		String nome = piatto.getNome().trim();
		String descrizione = piatto.getDescrizione().trim();

		if(this.piattoService.alreadyExists(piatto) != null) 
			errors.rejectValue("nome","piatto.duplicato");
		
		if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size.nome");
        
        if (descrizione.length() < MIN_DESCRIZIONE_LENGTH || descrizione.length() > MAX_DESCRIZIONE_LENGTH)
            errors.rejectValue("descrizione", "size.descrizione");

	}

}

