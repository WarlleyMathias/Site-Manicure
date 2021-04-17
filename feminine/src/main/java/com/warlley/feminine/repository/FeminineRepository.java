package com.warlley.feminine.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warlley.feminine.model.Agendamento;

@Repository
public interface FeminineRepository extends JpaRepository<Agendamento, Long>{
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM public.tb_agendamento WHERE id = :id", nativeQuery = true)
	void deleteAgendamento(@Param("id") long id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM public.tb_agendamento WHERE cliente = :cliente", nativeQuery = true)
	void deleteAgendamentoCliente(@Param("cliente") String cliente);

}
