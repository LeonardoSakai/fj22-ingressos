package br.com.caelum.ingresso.controller;

import java.time.LocalTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Controller
public class SessaoController {
	@Autowired
	private SalaDao salaDao;
	@Autowired
	private FilmeDao filmeDao;
	@Autowired
	private SessaoDao sessaoDao;

	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {
		ModelAndView modelAndView = new ModelAndView("sessao/sessao");
		modelAndView.addObject("sala", salaDao.findOne(salaId));
		modelAndView.addObject("filmes", filmeDao.findAll());
		modelAndView.addObject("form", form(null));
		return modelAndView;
	}

	private Object form(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostMapping(value = "/admin/sessao")
	@Transactional
	public ModelAndView salva(@Valid SessaoForm form, BindingResult result) {
		if (result.hasErrors())
			return form(form.getSalaId(), form);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");
		Sessao sessao = form.toSessao(salaDao, filmeDao);
		sessaoDao.save(sessao);
		return modelAndView;
	}

	public class SessaoForm {
		private Integer id;
		@NotNull
		private Integer salaId;
		@DateTimeFormat(pattern = "HH:mm")
		@NotNull
		private LocalTime horario;
		@NotNull
		private Integer filmeId;

		// getters e setters
		public Sessao toSessao(SalaDao salaDao, FilmeDao filmeDao) {
			Filme filme = filmeDao.findOne(filmeId);
			Sala sala = salaDao.findOne(salaId);
			Sessao sessao = new Sessao(horario, filme, sala);
			sessao.setId(id);
			return sessao;
		}

		public Integer getSalaId() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
