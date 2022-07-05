package it.uniroma3.siw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class UserController {

	@Autowired
	CredentialsService credentialsService;

	@Autowired
	CredentialsValidator credentialsValidator;

	@Autowired
	SessionData sessionData;
	

	
// 1)  METODI VALIDI SIA PER ADMIN CHE USER

	//vista home in base se si è admin o user 
	@GetMapping("/home")
	public String home(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials loggedCredentials = sessionData.getLoggedCredentials();
		model.addAttribute("loggedUser", loggedUser);
		//model.addAttribute("loggedCredentials", loggedCredentials);
		if(loggedCredentials.getRuolo().equals(Credentials.ADMIN_ROLE)) {
			return "adminHome";
		}
		return "home";
	}

	//Vista profilo loggato admin o user
	@GetMapping("/user/me")
	public String profile(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials loggedCredentials = sessionData.getLoggedCredentials();
		// System.out.println(loggedCredentials.getPassword());
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("loggedCredentials", loggedCredentials);
		return "userProfile";
	}

	//Vista modifica profilo loggato admin o user
	@GetMapping("/user/update")
	public String updateForm(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);

		model.addAttribute("newCredentials", new Credentials());
		return "updateForm";
	}

	//updateUser effettua il controllo dei dati inseriti nei campi di registrazione che si vogliono aggiornare
	//NB:lo username non puó essere uguale al precedente
	@PostMapping("/user/update")
	public String updateUser(@Valid @ModelAttribute("newCredentials") Credentials newCredentials,
			BindingResult credentialsBindingResult,
			Model model) {

		User loggedUser = sessionData.getLoggedUser();
		Credentials cred = credentialsService.getCredentials(sessionData.getLoggedCredentials().getId());

		this.credentialsValidator.validate(newCredentials, credentialsBindingResult);
		if(!credentialsBindingResult.hasErrors()) {

			cred.setUsername(newCredentials.getUsername());
			cred.setPassword(newCredentials.getPassword());
			credentialsService.update(cred);

			sessionData.setCredentials(cred);

			model.addAttribute("user", loggedUser);
			return "userUpdateSuccessful";
		}		
		return "updateForm";
	}

	
	
	
// 2)  //OPERAZIONI SULLA PAGINA DELL'ADMIN
	
	//vista admin con tutti gli utenti loggati
	@GetMapping("/admin/allUsers")
	public String userList(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", allCredentials);
		return "allUsers";
	}
	
	
	//admin delete utenti loggati
	@PostMapping("/admin/allUsers/{username}/delete")
	public String removeUser(@Valid Model model, @PathVariable String username) {
		this.credentialsService.deleteCredentials(username);
		return "redirect:/admin/allUsers";
	}

	
	
	
	
	

	


}
