package com.example.vidyateste.controller;

import com.example.vidyateste.model.PessoaJuridica;
import com.example.vidyateste.model.Produto;
import com.example.vidyateste.repository.PesssoaJuridicaRepository;
import com.example.vidyateste.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RestController
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@PostMapping(value = "salvarProduto") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Produto> salvar(@RequestBody Produto produto) { /* Recebe os dados para salvar */

		Produto produto1 = produtoRepository.save(produto);

		return new ResponseEntity<Produto>(produto1, HttpStatus.CREATED);
}



	@GetMapping(value = "buscaruseridProduto") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Produto> buscarprodid(@RequestParam(name = "idprod") Long idprod) { /* Recebe os dados para consultar */

		Produto produto = produtoRepository.findById(idprod).get();

		return new ResponseEntity<Produto>(produto, HttpStatus.OK);

	}

	@GetMapping(value = "listatodosProdutos") /* Nosso primeiro método de API */
	@ResponseBody /* Retorna os dados par ao corpo da resposta */
	public ResponseEntity<List<Produto>> listprod() {

		List<Produto> produtos = (List<Produto>) produtoRepository.findAll();/* executa a consulta no banco de dados */

		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);/* Retorna a lista em JSON */

	}

	@PutMapping(value = "atualizarProduto") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Produto produto) { /* Recebe os dados para salvar */

		if (produto.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
		}

		Produto user = produtoRepository.saveAndFlush(produto);

		return new ResponseEntity<Produto>(user, HttpStatus.OK);
}

	@DeleteMapping(value = "deleteProduto") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<String> delete(@RequestParam Long iduser) { /* Recebe os dados para delete */

		produtoRepository.deleteById(iduser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

	}}
