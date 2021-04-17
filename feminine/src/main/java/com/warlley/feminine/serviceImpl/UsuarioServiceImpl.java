package com.warlley.feminine.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warlley.feminine.model.Usuario;
import com.warlley.feminine.repository.UsuarioRepository;
import com.warlley.feminine.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	UsuarioRepository ur;

	@Override
	public Usuario save(Usuario usuario) {
		return ur.save(usuario);
	}

}
