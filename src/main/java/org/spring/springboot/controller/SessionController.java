package org.spring.springboot.controller;

import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SessionController {

	@Autowired
	private SessionDAO sessionDAO ;
	@RequestMapping("/sessionList")
	public ModelAndView getSessionList(Model model ){
		
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		ModelAndView modelAndView = new ModelAndView("/sessionList");
		modelAndView.addObject("sessions", sessions);
		modelAndView.addObject("sessionSize",sessions.size());
		model.addAttribute("test", sessions.size());
		
		System.out.println(sessions+"session的大小："+sessions.size());
		return modelAndView;
	}
	
	
	@RequestMapping("/sessions")  
    public String forceLogout(String sessionId,   
        RedirectAttributes redirectAttributes) {  
        try {  
        	System.out.println("下线===="+sessionId);
            Session session = sessionDAO.readSession(sessionId);  
            
            if(session != null) {  
                session.setAttribute("sessionlogout", Boolean.TRUE);
            }  
        } catch (Exception e) {/*ignore*/}  
        redirectAttributes.addFlashAttribute("msg", "强制退出成功！");  
        return "redirect:/sessionList";  
    }  
}
