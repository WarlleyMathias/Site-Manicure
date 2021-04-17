package com.warlley.feminine.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warlley.feminine.model.Agendamento;
import com.warlley.feminine.service.FeminineService;
import com.warlley.feminine.repository.FeminineRepository;

@Service
public class FeminineServiceImpl implements FeminineService{
	
	@Autowired
	FeminineRepository feminineRepository;

	@Override
	public List<Agendamento> findAll() {
		return feminineRepository.findAll();
	}

	@Override
	public Agendamento findById(long id) {
		return feminineRepository.findById(id).get();
	}

	@Override
	public Agendamento save(Agendamento agendamento) {
		return feminineRepository.save(agendamento);
	}

}
