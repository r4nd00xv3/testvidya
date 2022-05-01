package com.example.vidyateste.repository;

import com.example.vidyateste.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
@Repository
public interface PesssoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {



	@Query("select a from PessoaJuridica a where upper(trim(a.nomeFantasia)) like %?1%")
	List<PessoaJuridica> buscarAcessoDesc(String nome);

}
