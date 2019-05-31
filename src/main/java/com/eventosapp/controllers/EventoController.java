package com.eventosapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventosapp.models.Evento;
import com.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {
		// Método que salva os dados do form no banco através do método POST
		er.save(evento);
		
		return "redirect:/eventos";
	}
	
	@RequestMapping("/eventos") //Tipo de requisição
	public ModelAndView listaEventos() {
		// Objeto que recebe a pagina onde serão renderizados os dados do evento
		ModelAndView mv = new ModelAndView("index");
		
		// Iterable pois é uma lista de Evento.
		// Usando a variável EventoRepository chamando a função de busca.
		Iterable<Evento> eventos = er.findAll();
		
		// Método que adicionará ao objeto mv, a lista de eventos.
		// PARAMS: Nome e variável
		mv.addObject("eventos", eventos);		
		return mv;		
	}
	
	@RequestMapping("/editEvento/{codigo}")
	public String formEdit(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/editEvento");
		//mv.addObject("evento", evento);
		return form(evento);	
	}
	
	// Método que retorna o código do evento
	// PathVariable >> Vai receber o código da View
	@RequestMapping("/{codigo}")
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		
		// <div th:each="evento : ${evento}"> MESMO EVENTO ENTRE CHAVES
		mv.addObject("evento", evento);
		return mv;
	}
	
}
