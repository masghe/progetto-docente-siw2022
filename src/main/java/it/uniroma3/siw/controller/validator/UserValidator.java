package it.uniroma3.siw.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator{

	@Autowired
	private UserService userService;

    final Integer MAX_NAME_COGNOME_LENGTH = 60;
    final Integer MIN_NAME_COGNOME_LENGTH = 2;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		String nome = user.getNome().trim();
        String cognome = user.getCognome().trim();
        
        if(this.userService.alreadyExists(user) != null) 
			errors.rejectValue("nome","user.duplicato");
        
        if (nome.length() < MIN_NAME_COGNOME_LENGTH || nome.length() > MAX_NAME_COGNOME_LENGTH)
            errors.rejectValue("nome", "size.nome");
        
        if (cognome.length() < MIN_NAME_COGNOME_LENGTH || cognome.length() > MAX_NAME_COGNOME_LENGTH)
            errors.rejectValue("cognome", "size.cognome");
        
		
		

	}

}
