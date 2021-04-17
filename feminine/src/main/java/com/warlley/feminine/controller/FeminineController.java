package com.warlley.feminine.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.warlley.feminine.envio.Mailer;
import com.warlley.feminine.envio.Mensagem;
import com.warlley.feminine.model.Agendamento;
import com.warlley.feminine.repository.FeminineRepository;
import com.warlley.feminine.repository.UsuarioRepository;
import com.warlley.feminine.service.FeminineService;
import com.warlley.feminine.service.UsuarioService;

@Controller
public class FeminineController {
	
	@Autowired
	FeminineService feminineService;
	
	@Autowired
	UsuarioRepository ur;
	
	@Autowired
	FeminineRepository fr;
	
	@Autowired
	UsuarioService us;
	
	@Autowired
	Mailer mailer;
	
	@RequestMapping(value="/agendamentos", method=RequestMethod.GET)
	public ModelAndView getAgendamentos() {
		 ModelAndView mv = new ModelAndView("agendamentos");
		 String s = SecurityContextHolder.getContext().getAuthentication().getName();
		 List<Agendamento> ag = feminineService.findAll();
		 mv.addObject("ag", ag);
	     mv.addObject("logado", s);
	     mv.addObject("module", "agendamentos");
		 return mv;
	}
	
	@RequestMapping(value="/agendamentos", method=RequestMethod.POST)
	public String postAgendamentos(Long id, RedirectAttributes attributes) {
		if(id == null) {
			attributes.addFlashAttribute("mensagem1", "Selecione o agendamento que deseja excluir!");
			return "redirect:/agendamentos";
		}
		 fr.deleteAgendamento(id);
		 return "redirect:/agendamentos";
	}
	
	@RequestMapping(value="/agendamentos2", method=RequestMethod.POST)
    public String saveAgendamento(@Valid Agendamento agendamento, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors() || agendamento.getHoras().equals("horas") || agendamento.getDia() == null || agendamento.getDia().equals("") || agendamento.getCliente().equals("") || agendamento.getCliente() == null){
            attributes.addFlashAttribute("mensagem", "Selecione o dia e hora do seu agendamento!");
            return "redirect:/agendamentos";
        }
		LocalDate da = LocalDate.parse(agendamento.getDia(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		agendamento.setDia(da.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
        feminineService.save(agendamento);
        return "redirect:/agendamentos";
    }
	
	@RequestMapping(value="/loginError", method=RequestMethod.GET)
	public String getLoginError(RedirectAttributes attributes) {
		attributes.addFlashAttribute("mensagem", "login ou senha invalidos!");
		 return "redirect:/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView getLogin() {
		 ModelAndView mv = new ModelAndView("login");
		 String s = SecurityContextHolder.getContext().getAuthentication().getName();
	     mv.addObject("logado", s);
		 return mv;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView postLogin() {
		 ModelAndView mv = new ModelAndView("login");
		 return mv;
	}
	
	@RequestMapping(value="/cadastrar", method=RequestMethod.GET)
	public ModelAndView getCadastrar() {
		 ModelAndView mv = new ModelAndView("cadastrar");
		 String s = SecurityContextHolder.getContext().getAuthentication().getName();
	     mv.addObject("logado", s);
		 return mv;
	}
	
	@RequestMapping(value="/confirmacao", method=RequestMethod.GET)
	public ModelAndView getConfirmacao() {
		 ModelAndView mv = new ModelAndView("confirmacao");
		 String s = SecurityContextHolder.getContext().getAuthentication().getName();
	     mv.addObject("logado", s);
		 return mv;
	}
	
	@RequestMapping(value="/novoadmin", method=RequestMethod.GET)
	public ModelAndView getNovoadmin() {
		 ModelAndView mv = new ModelAndView("novoadmin");
		 return mv;
	}
	
	@RequestMapping(value="/novoadmin", method=RequestMethod.POST)
	public String postNovoadmin(String login, String senha, RedirectAttributes attributes) {
		 if(ur.findByLogin(login) != null) {
			 attributes.addFlashAttribute("mensagem", "email já cadastrado!");
	            return "redirect:/novoadmin";
		 }
		 ur.insertUser(login, new BCryptPasswordEncoder().encode(senha), false);
		 ur.insertUserRole(login, "ROLE_ADMIN");
		 ur.updateUser(login, true);
			return "redirect:/";
	}
	
	@RequestMapping(value="/cadastrar", method=RequestMethod.POST)
	public String postCadastrar(String login, String senha, RedirectAttributes attributes) throws UnsupportedEncodingException {
		 if(ur.findByLogin(login) != null) {
			 attributes.addFlashAttribute("mensagem", "email já cadastrado!");
	            return "redirect:/cadastrar";
		 }
		 ur.insertUser(login, new BCryptPasswordEncoder().encode(senha), false);
		 ur.insertUserRole(login, "ROLE_USER");
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		 textEncryptor.setPassword("seguranca");
			mailer.enviar(new Mensagem("<warlleymathias1.1@gmail.com>", 
					Arrays.asList(login)
					, "E-mail de confirmação", "http://feminine-dev.us-west-2.elasticbeanstalk.com/liberar/"+ URLEncoder.encode(textEncryptor.encrypt(login), "ISO-8859-1")));
			return "redirect:/confirmacao";
	}
	
	@RequestMapping(value={"/liberar/{1}"}, method=RequestMethod.GET)
	public String getLiberar(HttpServletRequest request) throws UnsupportedEncodingException{
		 BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		 textEncryptor.setPassword("seguranca");
		 String str = textEncryptor.decrypt(URLDecoder.decode(request.getRequestURL().toString().substring(59), "ISO-8859-1"));
		 ur.updateUser(str, true);
        return "redirect:/";
    }

	@RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView getPosts(){
        ModelAndView mv = new ModelAndView("principal");
        String s = SecurityContextHolder.getContext().getAuthentication().getName();
        mv.addObject("logado", s);
        return mv;
    }
	
	@RequestMapping(value="/agendar", method=RequestMethod.GET)
    public ModelAndView getAgendar(){
        ModelAndView mv = new ModelAndView("agendar");
        List<Agendamento> ag = feminineService.findAll();
        String str = ag.stream()
        	    .map(agend -> (agend.getDia()))
        	    .collect(Collectors.joining(",", "", ""));
        mv.addObject("agendamento", str);
        String s = SecurityContextHolder.getContext().getAuthentication().getName();
        mv.addObject("logado", s);
        for(Agendamento a : ag)
	 		if(a.getCliente().equals(s))
	 			mv.addObject("agendado", a.getCliente());
        return mv;
    
    }
	
	  @ResponseBody
	  @RequestMapping("/ag")  
	    public List<Agendamento> listjson(HttpServletRequest req) {  
		  List<Agendamento> ag = feminineService.findAll(); 
	        return ag;  
	    }  
	  
	
	 @RequestMapping(value="/agendar", method=RequestMethod.POST)
	    public String savePost(@Valid Agendamento agendamento, BindingResult result, RedirectAttributes attributes){
		 agendamento.setCliente(SecurityContextHolder.getContext().getAuthentication().getName());
		 	List<Agendamento> ag = feminineService.findAll();
		 	for(Agendamento a : ag)
		 		if(a.getCliente().equals(agendamento.getCliente())) {
		 			attributes.addFlashAttribute("mensagem", "Você já tem um horário marcado!");
		            return "redirect:/agendar";
		 		}
	        if(result.hasErrors() || agendamento.getHoras().equals("horas") || agendamento.getDia() == null || agendamento.getDia().equals("")){
	            attributes.addFlashAttribute("mensagem", "Selecione o dia e hora do seu agendamento!");
	            return "redirect:/agendar";
	        }
	        feminineService.save(agendamento);
	        return "redirect:/agendar";
	    }
	
	@RequestMapping(value="/contato", method=RequestMethod.GET)
    public ModelAndView getContato(){
        ModelAndView mv = new ModelAndView("contato");
        String s = SecurityContextHolder.getContext().getAuthentication().getName();
        mv.addObject("logado", s);
        mv.addObject("module", "contato");
        return mv;
    }
	
	@RequestMapping(value="/localizacao", method=RequestMethod.GET)
    public ModelAndView getLocalizacao(){
        ModelAndView mv = new ModelAndView("localizacao");
        String s = SecurityContextHolder.getContext().getAuthentication().getName();
        mv.addObject("logado", s);
        mv.addObject("module", "localizacao");
        return mv;
    }
	
	@RequestMapping(value="/trabalhos", method=RequestMethod.GET)
    public ModelAndView getTrabalhos(){
        ModelAndView mv = new ModelAndView("trabalhos");
        String s = SecurityContextHolder.getContext().getAuthentication().getName();
        mv.addObject("logado", s);
        mv.addObject("module", "trabalhos");
        return mv;
    }
	
	@RequestMapping(value="/cancelar", method=RequestMethod.GET)
	public String cancelar() {
		 fr.deleteAgendamentoCliente(SecurityContextHolder.getContext().getAuthentication().getName());
		 return "redirect:/agendar";
	}
	
}

