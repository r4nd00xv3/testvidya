package com.example.vidyateste.controller;

import com.example.vidyateste.dto.CepDTO;
import com.example.vidyateste.model.Endereco;
import com.example.vidyateste.model.PessoaJuridica;
import com.example.vidyateste.repository.EnderecoRepository;
import com.example.vidyateste.repository.PesssoaJuridicaRepository;
import com.example.vidyateste.service.PessoaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;


@Controller
@RestController
public class PessoaJuridicaController {

	@Autowired
	private PesssoaJuridicaRepository pesssoaJuridicaRepository;

	@Autowired
	private PessoaUserService pessoaUserService;

	@Autowired
	private EnderecoRepository enderecoRepository;



	@PostMapping(value = "salvar") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<PessoaJuridica> salvar(@RequestBody PessoaJuridica usuario) { /* Recebe os dados para salvar */

		PessoaJuridica user = pesssoaJuridicaRepository.save(usuario);

		return new ResponseEntity<PessoaJuridica>(user, HttpStatus.CREATED);
}



	@GetMapping(value = "buscaruserid/{id}") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<PessoaJuridica> buscaruserid(@PathVariable(name = "id") Long id) { /* Recebe os dados para consultar */

		PessoaJuridica pessoa = pesssoaJuridicaRepository.findById(id).get();

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

	}

	@ResponseBody
	@GetMapping(value = "/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep){

		return new ResponseEntity<CepDTO>(pessoaUserService.consultaCep(cep), HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping(value = "/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws Exception{

		/*if (pessoaJuridica.getNome() == null || pessoaJuridica.getNome().trim().isEmpty()) {
			throw new ExceptionMentoriaJava("Informe o campo de nome");
		}*/


		if (pessoaJuridica == null) {
			throw new Exception("Pessoa juridica nao pode ser NULL");
		}



		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {

			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

				CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

				pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());

			}
		}else {

			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {

				Endereco enderecoTemp =  enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();

				if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {

					CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());

					pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
				}
			}
		}
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}
}
