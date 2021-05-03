package com.bolsadeideas.springboot.form.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Role;

@Service
public class RoleServiceImpl implements RoleService{
	
	private List<Role> roles;
	
	public RoleServiceImpl() {
		this.roles = new ArrayList<>();
		this.roles.add(new Role(1,"Administrador", "ROL_ADMIN"));
		this.roles.add(new Role(2,"Usuario", "ROL_USER"));
		this.roles.add(new Role(3,"Moderador", "ROL_MODERATOR"));
	}

	@Override
	public List<Role> listar() {
		return roles;
	}

	@Override
	public Role obtenerPorId(Integer id) {
		Role resultado = null;
		for (Role rol : roles) {
			if (id == rol.getId()) {
				resultado = rol;
				break;
			}
		}
		return resultado;
	}

}
