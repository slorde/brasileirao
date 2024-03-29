package com.fsoft.brasileirao.dto;

import com.fsoft.brasileirao.model.Dono;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DonoDTO {

	private Long id;
	private String nome;
	
	public DonoDTO(Dono dono) {
		this.id = dono.getId();
		this.nome = dono.getNome();
	}
	
}
