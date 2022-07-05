package it.uniroma3.siw.controller;

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
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.PiattoService;

@Controller
public class IngredienteController {

	@Autowired
	SessionData sessionData;

	@Autowired
	IngredienteService ingredienteService;
	
	@Autowired
	IngredienteValidator ingredienteValidator;


	@Autowired
	PiattoService piattoService;

	//Get vista per la form di registrazione di un nuovo 
	@GetMapping("/admin/{id}/registerIngrediente")
	public String ingredienteForm(@Valid Model model, @PathVariable Long id) {
		User loggedUser = sessionData.getLoggedUser();

		Piatto piatto = piattoService.findById(id);
		model.addAttribute("ingredienteForm", new Ingrediente());
		model.addAttribute("loggedPiatto", piatto);
		model.addAttribute("loggedUser", loggedUser);

		return "registerIngredienteForm";
	}

	//Post  registrazione nuovo Ingrediente
	@PostMapping("/admin/{id}/registerIngrediente")
	public String registerIngrediente(@Valid @ModelAttribute("ingredienteForm") Ingrediente ingrediente, @PathVariable Long id,
			BindingResult ingredienteBindingResult,
			Model model) {

		Piatto piatto = piattoService.findById(id);
		model.addAttribute("loggedPiatto", piatto);

		this.ingredienteValidator.validate(ingrediente, ingredienteBindingResult);

		
		if(!ingredienteBindingResult.hasErrors()) {
			ingrediente.setPiatto(piatto);
			this.ingredienteService.save(ingrediente);
			return "registrationIngredienteSuccessful";
		}
		return "registerIngredienteForm";
	}


	//delete ingrediente tramite id Piatto
	@PostMapping("/admin/{idPiatto}/allIngredienti/{idIngrediente}/delete")
	public String removeIngrediente(@Valid Model model, @PathVariable Long idPiatto,@PathVariable Long idIngrediente) {
		Ingrediente ingrediente = ingredienteService.findById(idIngrediente);
		this.ingredienteService.delete(ingrediente);
		return "redirect:/admin/allPiatti/{idPiatto}/profile";
	}

}