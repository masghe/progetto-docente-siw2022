
package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;

@Component
public class BuffetValidator implements Validator{



	@Autowired
	BuffetService buffetService;
	
	final Integer MAX_DESCRIZIONE_LENGTH = 60;
    final Integer MIN_DESCRIZIONE_LENGTH = 6;
    final Integer MAX_NAME_LENGTH = 60;
    final Integer MIN_NAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> clazz) {
		return Buffet.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Buffet buffet = (Buffet) target;
		String nome = buffet.getNome().trim();
		String descrizione = buffet.getDescrizione().trim();
		
		if(this.buffetService.alreadyExists(buffet) != null) 
			errors.rejectValue("nome","buffet.duplicato");
		
		if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size.nome");
        
        if (descrizione.length() < MIN_DESCRIZIONE_LENGTH || descrizione.length() > MAX_DESCRIZIONE_LENGTH)
            errors.rejectValue("descrizione", "size.descrizione");

	}

}

