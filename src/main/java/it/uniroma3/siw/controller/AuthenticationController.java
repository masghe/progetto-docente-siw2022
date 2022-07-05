package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	SessionData sessionData;

	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegisterForm(@Valid Model model) {
		model.addAttribute("userForm",new User());
		model.addAttribute("credentialsForm",new Credentials());
		return "registerUserForm";

	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("userForm") User user,
			BindingResult userBindingResult, 
			@Valid @ModelAttribute("credentialsForm") Credentials credentials,
			BindingResult credentialsBindingResult, 
			Model model) {

		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		this.userValidator.validate(user, userBindingResult);

		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			model.addAttribute("user", user);
			return "registrationUserSuccessful";
		}
		return "registerUserForm";
	}




}
