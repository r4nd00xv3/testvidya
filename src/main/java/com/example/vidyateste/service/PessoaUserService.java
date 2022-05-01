package com.example.vidyateste.service;


import com.example.vidyateste.dto.CepDTO;
import com.example.vidyateste.model.PessoaJuridica;
import com.example.vidyateste.repository.PesssoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PessoaUserService {
	

	
	@Autowired
	private PesssoaJuridicaRepository pesssoaJuridicaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica) {

		//juridica = pesssoaRepository.save(juridica);

		for (int i = 0; i< juridica.getEnderecos().size(); i++) {
			juridica.getEnderecos().get(i).setPessoa(juridica);
		}

		juridica = pesssoaJuridicaRepository.save(juridica);


		return juridica;

	}


	public CepDTO consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
	}


}
