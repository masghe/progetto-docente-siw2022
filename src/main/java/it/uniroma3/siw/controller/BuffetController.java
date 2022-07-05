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
import it.uniroma3.siw.controller.validator.BuffetValidator;
import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BuffetService;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class BuffetController {

	@Autowired
	SessionData sessionData;

	@Autowired
	BuffetService buffetService;

	@Autowired
	ChefService chefService;

	@Autowired
	PiattoService piattoService;

	@Autowired
	BuffetValidator buffetValidator;

	//visualizza tutti i buffet
	@GetMapping("/admin/allBuffet")
	public String showListBuffet(@Valid Model model) {
		User loggedUser = sessionData.getLoggedUser();

		List<Buffet> allBuffet = this.buffetService.getAllBuffet();
		model.addAttribute("loggedUser", loggedUser);

		model.addAttribute("buffetList", allBuffet);
		return "allBuffet";
	}


	//Get vista per la form di registrazione di un nuovo Buffet
	@GetMapping("/admin/{id}/registerBuffet")
	public String buffetForm(@Valid Model model, @PathVariable Long id) {
		User loggedUser = sessionData.getLoggedUser();
		Chef chef = chefService.findById(id);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("buffetForm", new Buffet());
		model.addAttribute("loggedChef", chef);
		return "registerBuffetForm";
	}

	//Post  registrazione nuovo Buffet
	@PostMapping("/admin/{id}/registerBuffet")
	public String registerBuffet(@Valid @ModelAttribute("buffetForm") Buffet buffet, @PathVariable Long id,
			BindingResult buffetBindingResult,
			Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("loggedChef", chef);

		this.buffetValidator.validate(buffet, buffetBindingResult);

		if(!buffetBindingResult.hasErrors()) {
			buffet.setChef(chef);
			this.buffetService.save(buffet);
			return "registrationBuffetSuccessful";
		}
		return "registerBuffetForm";
	}


	//delete buffet tramite id chef
	@PostMapping("/admin/{idChef}/allBuffet/{idBuffet}/delete")
	public String removeBuffet(@Valid Model model, @PathVariable Long idChef,@PathVariable Long idBuffet) {
		Buffet buffet = buffetService.findById(idBuffet);
		this.buffetService.delete(buffet);
		return "redirect:/admin/allChef/{idChef}/profile";
	}

	//delete buffet nella lista buffet
	@PostMapping("/admin/allBuffet/{id}/delete")
	public String removeBuffet(@Valid Model model,@PathVariable Long id) {
		Buffet buffet = buffetService.findById(id);
		this.buffetService.delete(buffet);
		return "redirect:/admin/allBuffet";
	}


	//vista ADMIN profilo buffet con i relativi piatti
	@GetMapping("/admin/allBuffet/{id}/profile")
	public String showProfileBuffet(@Valid Model model,@PathVariable Long id) {
		User loggedUser = sessionData.getLoggedUser();

		Buffet buffet = buffetService.findById(id);
		List<Piatto> allPiatti = this.piattoService.getAllPiatti(buffet);
		model.addAttribute("piattoList", allPiatti);
		model.addAttribute("loggedBuffet", buffet);
		model.addAttribute("loggedUser", loggedUser);

		return "buffetProfile";
	}
	
	//vista USER profilo buffet con i relativi piatti
	@GetMapping("/user/allBuffet/{id}/profile")
	public String viewInformationBuffet(@Valid Model model,@PathVariable Long id) {
		User loggedUser = sessionData.getLoggedUser();

		Buffet buffet = buffetService.findById(id);
		List<Piatto> allPiatti = this.piattoService.getAllPiatti(buffet);
		model.addAttribute("loggedUser", loggedUser);

		model.addAttribute("piattoList", allPiatti);
		model.addAttribute("loggedBuffet", buffet);
		return "viewInformationBuffet";
	}

}
