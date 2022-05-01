package com.example.vidyateste.controller;

import com.example.vidyateste.model.PessoaJuridica;
import com.example.vidyateste.repository.PesssoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RestController
public class PessoaJuridicaController {

	@Autowired
	private PesssoaJuridicaRepository pesssoaJuridicaRepository;

	@PostMapping(value = "salvar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<PessoaJuridica> salvar(@RequestBody PessoaJuridica usuario) { /* Recebe os dados para salvar */

		PessoaJuridica user = pesssoaJuridicaRepository.save(usuario);

		return new ResponseEntity<PessoaJuridica>(user, HttpStatus.CREATED);
}



	@GetMapping(value = "buscaruserid") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<PessoaJuridica> buscaruserid(@RequestParam(name = "iduser") Long iduser) { /* Recebe os dados para consultar */

		PessoaJuridica pessoa = pesssoaJuridicaRepository.findById(iduser).get();

		return new ResponseEntity<PessoaJuridica>(pessoa, HttpStatus.OK);

	}

	@GetMapping(value = "listatodos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<PessoaJuridica>> listaUsuario() {

		List<PessoaJuridica> pessoaJuridicas = (List<PessoaJuridica>) pesssoaJuridicaRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<PessoaJuridica>>(pessoaJuridicas, HttpStatus.OK);/* Retorna a lista em JSON */

	}

	@PutMapping(value = "atualizar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody PessoaJuridica pessoa) { /* Recebe os dados para salvar */

		if (pessoa.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
		}

		PessoaJuridica user = pesssoaJuridicaRepository.saveAndFlush(pessoa);

		return new ResponseEntity<PessoaJuridica>(user, HttpStatus.OK);
}

	@DeleteMapping(value = "delete") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para delete */

		pesssoaJuridicaRepository.deleteById(iduser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

	}}
