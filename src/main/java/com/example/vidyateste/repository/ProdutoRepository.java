package com.example.vidyateste.repository;

import com.example.vidyateste.model.PessoaJuridica;
import com.example.vidyateste.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "select u from Produto u where u.qnt = 0")
    List<Produto> notifcliente();

}
