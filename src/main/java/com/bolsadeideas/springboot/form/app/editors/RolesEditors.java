package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.RoleService;

@Component
public class RolesEditors extends PropertyEditorSupport{

	@Autowired
	private RoleService service;
	
	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		try {
			setValue(service.obtenerPorId(Integer.parseInt(id)));
		} catch (NumberFormatException e) {
			setValue(null);
		}
	}
	
	

}
