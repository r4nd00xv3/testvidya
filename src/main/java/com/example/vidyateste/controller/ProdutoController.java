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
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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



	@GetMapping(value = "buscaruseridProduto/{id}") /* mapeia a url */
	@ResponseBody /* Descricao da resposta */
	public ResponseEntity<Produto> buscarprodid(@PathVariable(name = "id") Long id) { /* Recebe os dados para consultar */

		Produto produto = produtoRepository.findById(id).get();

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
			return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.UNAUTHORIZED);
		}

		Produto user = produtoRepository.saveAndFlush(produto);

		return new ResponseEntity<Produto>(user, HttpStatus.CREATED);
}



	@ResponseBody
	@DeleteMapping(value = "/deleteProdPorId/{id}")
	public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) {

		produtoRepository.deleteById(id);

		return new ResponseEntity("Acesso Removido",HttpStatus.OK);
	}

	@PostMapping("/produto/anexo")
	public ResponseEntity<String> uploadAnexo(@RequestParam MultipartFile anexo) throws IOException {
		OutputStream out = new FileOutputStream(
				"/Users/randrade/Desktop/teste dev/anexo.pdf" + anexo.getOriginalFilename());
		out.write(anexo.getBytes());
		out.close();
		return new ResponseEntity<String>("Enviado com sucesso.", HttpStatus.CREATED);

	}}
