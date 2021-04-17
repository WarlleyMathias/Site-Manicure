package com.warlley.feminine.configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.warlley.feminine.model.Agendamento;
import com.warlley.feminine.repository.FeminineRepository;
import com.warlley.feminine.service.FeminineService;

@Component @EnableScheduling 
public class LimpadorDeAgendamentos {
	
	@Autowired
	FeminineService f;
	
	@Autowired
	FeminineRepository fr;

    private static final String TIME_ZONE = "America/Sao_Paulo";
     // atributos

    @Scheduled(cron = "0 0 00 * * *", zone = TIME_ZONE) public void apagaAgendamento() { 
        List<Agendamento> ag = f.findAll();
        for(Agendamento a : ag) {
        	System.out.println(a.getDia().compareTo(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        	if(a.getDia().compareTo(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) < 0)
        		fr.deleteAgendamento(a.getId());
        }
    }

}