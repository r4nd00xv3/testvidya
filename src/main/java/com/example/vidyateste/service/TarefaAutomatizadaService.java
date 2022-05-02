package com.example.vidyateste.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.example.vidyateste.model.PessoaJuridica;
import com.example.vidyateste.repository.PesssoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Service
public class TarefaAutomatizadaService {
	
	@Autowired
	private PesssoaJuridicaRepository pesssoaJuridicaRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	
	//@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo") /*Vai rodar todo dia as 11 horas da manhã horario de Sao paulo*/
	@Scheduled(fixedDelay = 60000 ) /*Roda a cada 5 minutos*/
	public void notificarUserValidLic() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		
		List<PessoaJuridica> usuarios = pesssoaJuridicaRepository.datavalidadelicenca();
		
		for (PessoaJuridica usuario : usuarios) {
			
			
			StringBuilder msg = new StringBuilder();
			msg.append("Olá, ").append(usuario.getNomeFantasia()).append("<br/>");
			msg.append("Está na hora de renovar sua liceça, já passou 1 dia de validade.").append("<br/>");
			msg.append("Troque sua licença para continuar usando nossos serviços");
			
			serviceSendEmail.enviarEmailHtml("Favor renovar licença", msg.toString(), usuario.getEmail());
			
			Thread.sleep(100);
			
		}



	}

}
