package com.fsoft.brasileirao.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.dto.CompeticaoDTO;
import com.fsoft.brasileirao.dto.DonoDTO;
import com.fsoft.brasileirao.model.Competicao;
import com.fsoft.brasileirao.model.Resultado;
import com.fsoft.brasileirao.repository.CompeticaoRepository;

@Service
public class CompeticaoService {

	@Autowired
	private CompeticaoRepository repository;
	
	@Autowired
	private ResultadoService resultadoService;

	public List<Competicao> competicoesAtivas() {
		return repository.findByFinalizadaFalse();
	}

	public List<Resultado> resultados(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Competição não encontrada"))
				.getResultados();
	}

	public List<Integer> anosComCompeticao() {
		return repository.findAll().stream().map(competicao -> competicao.getAno()).sorted()
				.collect(Collectors.toList());
	}
	
	public CompeticaoDTO create(Competicao competicao) {
		CompeticaoDTO competicaoDTO = new CompeticaoDTO(competicao);
		
		List<DonoDTO> participantes = competicao.getResultados().stream()
		.filter(resultado -> !resultado.getDono().getIsResultado())
		.map(resultado -> new DonoDTO(resultado.getDono(), resultadoService.create(resultado)))
		.sorted(Comparator.comparingInt(DonoDTO::getPontuacao))
		.collect(Collectors.toList());
		
		competicaoDTO.setParticipantes(participantes);
		
		return competicaoDTO;
	}
}
