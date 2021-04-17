package com.warlley.feminine.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.warlley.feminine.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{
	
	@Modifying
	@Transactional
	@Query(value = "insert into public.usuario (login, senha, liberado) values (:login, :senha, :liberado)", nativeQuery = true)
	void insertUser(@Param("login") String login, @Param("senha") String senha, @Param("liberado") Boolean liberado);
	
	@Modifying
	@Transactional
	@Query(value = "insert into public.usuarios_roles (usuario_id, role_id) values (:login, :role)", nativeQuery = true)
	void insertUserRole(@Param("login") String login, @Param("role") String role);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.usuario SET liberado= :liberado WHERE login = :login", nativeQuery = true)
	void updateUser(@Param("login") String login, @Param("liberado") Boolean liberado);

	Usuario findByLogin(String login);
}
