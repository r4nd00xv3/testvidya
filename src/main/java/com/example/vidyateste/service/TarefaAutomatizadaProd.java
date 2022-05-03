package com.example.vidyateste.service;

import com.example.vidyateste.model.PessoaJuridica;
import com.example.vidyateste.model.Produto;
import com.example.vidyateste.repository.PesssoaJuridicaRepository;
import com.example.vidyateste.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@Service
public class TarefaAutomatizadaProd {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    //@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo") /*Vai rodar todo dia as 11 horas da manhã horario de Sao paulo*/
    @Scheduled(fixedDelay = 25000   ) /*Roda a cada 2 minutos*/
    public void notifecliente() throws UnsupportedEncodingException, MessagingException, InterruptedException {

        List<Produto> produtos = produtoRepository.notifcliente();

        for (Produto produto : produtos) {


            StringBuilder msg = new StringBuilder();
            msg.append("Olá, ").append(produto.getPessoa().getNomeFantasia()).append("<br/>");
            msg.append("Vc esta sem estoque!").append("<br/>");
            msg.append("Favor Reabasteça. ");

            serviceSendEmail.enviarEmailHtml("SEM ESTOQUE", msg.toString(), produto.getPessoa().getEmail());

            Thread.sleep(6000);

        }
}}
