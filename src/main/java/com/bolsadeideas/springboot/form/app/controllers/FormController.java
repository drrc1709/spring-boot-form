package com.bolsadeideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditors;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;

@Controller
@SessionAttributes("usuario")
public class FormController {
	
	@Autowired
	private UsuarioValidador validador;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private RoleService rolService;
	
	@Autowired
	private PaisPropertyEditor paisEditor;
	
	@Autowired
	private RolesEditors rolEditor;
	
	@InitBinder
	public void initBinde(WebDataBinder binder) {
		binder.addValidators(validador);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());
		
		binder.registerCustomEditor(Pais.class, "pais", paisEditor);
		binder.registerCustomEditor(Role.class, "roles", rolEditor);
	}
	
	 @ModelAttribute("listaRoles")
	 public List<Role> listaRoles(){
		 return this.rolService.listar();
	 }
	
	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises(){
		return paisService.listar();
	}
	
	@ModelAttribute("listaRolesString")
	public List<String> listaRolesString() {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		return roles;
	}
	
	@ModelAttribute("listaRolesMap")
	public Map<String, String> listaRolesMap(){
		Map<String, String> roles = new HashMap<String, String>();
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");
		
		return roles;
	}
	
	
	@ModelAttribute("paises")
	public List<String> paises(){
		return Arrays.asList("España", "México", "Chile", "Argentina", "Perú", "Colombia", "Venezuela");
	}
	
	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap(){
		Map<String, String> paises = new HashMap<String, String>();
		paises.put("ES", "España");
		paises.put("MX", "México");
		paises.put("CL", "Chile");
		paises.put("AR", "Argentina");
		paises.put("PE", "Perú");
		paises.put("CO", "Colombia");
		paises.put("VE", "Venezuela");
		
		return paises;
	}
	

	@GetMapping("/form")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("John");
		usuario.setApellido("Doe");
		usuario.setIdentificador("143.453.453-K");
		usuario.setHabilitar(true);
		model.addAttribute("titulo", "Formulario usuarios");
		model.addAttribute("usuario", usuario);
		
		return "form";
	}

	@PostMapping("/form")
	public String procesar(
			@Valid Usuario usuario,
			BindingResult result,
			Model model,
			SessionStatus status
			) {
		//validador.validate(usuario, result);
		model.addAttribute("titulo", "Resultado FORM");
		
		if (result.hasErrors()) {		
					
			return "form";
		}
				
		model.addAttribute("Usuario", usuario);
		status.setComplete();
		
		return "resultado";
	}
}
