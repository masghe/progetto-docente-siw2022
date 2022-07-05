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
import it.uniroma3.siw.controller.validator.ChefValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.ChefService;

@Controller
public class ChefController {



	@Autowired
	SessionData sessionData;


	@Autowired
	ChefService chefService;

	@Autowired
	ChefValidator chefValidator;

	@Autowired
	BuffetService buffetService;

	//vista admin  dove puo vedere tutti gli chef a disposizione
	@GetMapping("/admin/allChef")
	public String adminChefList(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Chef> allChef = this.chefService.getAllChef();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("chefList", allChef);
		return "adminAllChef";
	}


	//vista user dove puo vedere tutti gli chef a disposizione
	@GetMapping("/user/allChef")
	public String chefList(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Chef> allChef = this.chefService.getAllChef();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("chefList", allChef);
		return "allChef";
	}

	//vista registrazione chef
	@GetMapping("/admin/registerChef")
	public String chefForm(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("chefForm", new Chef());
		model.addAttribute("loggedUser", loggedUser);
		return "registerChefForm";
	}
	//form registrazione chef
	@PostMapping("/admin/registerChef")
	public String registerUser(@Valid @ModelAttribute("chefForm") Chef chef,
			BindingResult chefBindingResult, 
			Model model) {

		this.chefValidator.validate(chef, chefBindingResult);

		if(!chefBindingResult.hasErrors()) {
			chefService.save(chef);
			model.addAttribute("chef", chef);
			return "registrationChefSuccessful";
		}
		return "registerChefForm";
	}

	//delete chef
	@PostMapping("/admin/allChef/{id}/delete")
	public String removeUser(@Valid Model model, @PathVariable Long id) {
		Chef chef = chefService.findById(id);

		this.chefService.delete(chef);
		return "redirect:/admin/allChef";
	}


	//Vista ADMIN profilo che con i relativi Buffet
	@GetMapping("/admin/allChef/{id}/profile")
	public String showProfileChef(@Valid Model model,@PathVariable Long id) {
		User loggedUser = sessionData.getLoggedUser();
		Chef chef = chefService.findById(id);
		List<Buffet> allBuffet = this.buffetService.getAllBuffet(chef);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("buffetList", allBuffet);
		model.addAttribute("loggedChef", chef);
		return "chefProfile";
	}
	
	//vista USER che visualizza tutte le informazioni relative allo chef selezionato 
	@GetMapping("/user/allChef/{id}/profile")
	public String viewInformationChef(@Valid Model model,@PathVariable Long id) {
		Chef chef = chefService.findById(id);
		List<Buffet> allBuffet = this.buffetService.getAllBuffet(chef);
		model.addAttribute("buffetList", allBuffet);
		model.addAttribute("loggedChef", chef);
		return "viewInformationChef";
	}

	//Vista modifica profilo loggato
	//	@GetMapping("/admin/{id}/chefUpdate")
	//	public String updateForm(@Valid Model model, @PathVariable Long id) {
	//		Chef chef = chefService.findById(id);
	//		model.addAttribute("loggedChef", chef);
	//		return "updateChefForm";
	//	}
	//	
	//	//aggiornamento profilo chef
	//	@PostMapping("/admin/chefUpdate")
	//	public String updateUser(@Valid @ModelAttribute("newCredentials") Credentials newCredentials,
	//			BindingResult credentialsBindingResult,
	//			Model model) {
	//
	//		User loggedUser = sessionData.getLoggedUser();
	//		Credentials cred = credentialsService.getCredentials(sessionData.getLoggedCredentials().getId());
	//
	//		this.credentialsValidator.validate(newCredentials, credentialsBindingResult);
	//		if(!credentialsBindingResult.hasErrors()) {
	//
	//			cred.setUsername(newCredentials.getUsername());
	//			cred.setPassword(newCredentials.getPassword());
	//			credentialsService.update(cred);
	//
	//			sessionData.setCredentials(cred);
	//
	//			model.addAttribute("user", loggedUser);
	//			return "userUpdateSuccessful";
	//		}		
	//		return "updateForm";
	//	}

}
