package br.com.caelum.ingresso.model.form;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
}
