package com.warlley.feminine.service;

import java.util.List;

import com.warlley.feminine.model.Agendamento;

public interface FeminineService {
	
	List<Agendamento> findAll();
	Agendamento findById(long id);
    Agendamento save(Agendamento agendamento);
	
}
