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
import it.uniroma3.siw.controller.validator.IngredienteValidator;
import it.uniroma3.siw.controller.validator.PiattoValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired
	PiattoService piattoService;

	@Autowired
	BuffetService buffetService;

	@Autowired
	PiattoValidator piattoValidator;

	@Autowired
	SessionData sessionData;

	@Autowired
	IngredienteService ingredienteService;
	
	@Autowired
	IngredienteValidator ingredienteValidator;


	//visualizza tutti i Piatti
	@GetMapping("/admin/allPiatti")
	public String showListPiatti(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();

		List<Piatto> allPiatti = this.piattoService.getAllPiatti();
		model.addAttribute("loggedUser", loggedUser);

		model.addAttribute("piattoList", allPiatti);
		return "allPiatti";
	}


	//Get vista per la form di registrazione di un nuovo Piatto
	@GetMapping("/admin/{id}/registerPiatto")
	public String piattoForm(@Valid Model model, @PathVariable Long id) {
		User loggedUser = sessionData.getLoggedUser();
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("piattoForm", new Piatto());
		model.addAttribute("loggedBuffet", buffet);
		return "registerPiattoForm";
	}

	//Post  registrazione nuovo Piatto
	@PostMapping("/admin/{id}/registerPiatto")
	public String registerPiatto(@Valid @ModelAttribute("piattoForm") Piatto piatto, @PathVariable Long id,
			BindingResult piattoBindingResult,
			Model model) {

		Buffet buffet = buffetService.findById(id);
		model.addAttribute("loggedBuffet", buffet);

		this.piattoValidator.validate(piatto, piattoBindingResult);

		if(!piattoBindingResult.hasErrors()) {
			piatto.setBuffet(buffet);
			this.piattoService.save(piatto);
			return "registrationPiattoSuccessful";
		}
		return "registerPiattoForm";
	}



	//delete piatto tramite id Buffet
	@PostMapping("/admin/{idBuffet}/allPiatti/{idPiatto}/delete")
	public String removePiatto(@Valid Model model, @PathVariable Long idBuffet,@PathVariable Long idPiatto) {
		Piatto piatto = piattoService.findById(idPiatto);
		this.piattoService.delete(piatto);
		return "redirect:/admin/allBuffet/{idBuffet}/profile";
	}


	//vista ADMIN profilo piatto con i relativi ingredienti
	@GetMapping("/admin/allPiatti/{id}/profile")
	public String showProfileBuffet(@Valid Model model,@PathVariable Long id) {
		User loggedUser = sessionData.getLoggedUser();

		Piatto piatto = piattoService.findById(id);
		List<Ingrediente> allIngredienti = this.ingredienteService.getAllIngredienti(piatto);
		model.addAttribute("ingredienteList", allIngredienti);
		model.addAttribute("loggedPiatto", piatto);
		model.addAttribute("loggedUser", loggedUser);

		return "piattoProfile";
	}

	//vista User profilo piatto con i relativi ingredienti
	@GetMapping("/user/allPiatti/{id}/profile")
	public String viewInformationPiatto(@Valid Model model,@PathVariable Long id) {
		User loggedUser = sessionData.getLoggedUser();

		Piatto piatto = piattoService.findById(id);
		List<Ingrediente> allIngredienti = this.ingredienteService.getAllIngredienti(piatto);
		model.addAttribute("ingredienteList", allIngredienti);
		model.addAttribute("loggedPiatto", piatto);
		model.addAttribute("loggedUser", loggedUser);

		return "viewInformationPiatto";
	}





}
