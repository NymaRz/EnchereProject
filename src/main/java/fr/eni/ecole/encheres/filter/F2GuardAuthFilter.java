package fr.eni.ecole.encheres.filter;

import java.io.IOException;

import fr.eni.ecole.encheres.bo.Passport;
import fr.eni.ecole.encheres.helper.HttpServer;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(
		dispatcherTypes = {
				DispatcherType.REQUEST
		},
		urlPatterns = {
				"/jeux/ajouter",
				"/jeux/modifier/*",
				"/jeux/supprimer/*",
				"/deconnexion"
		}
		)
public class F2GuardAuthFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpSession session = rq.getSession();
		Passport passport = (Passport) session.getAttribute("passport");
		
		if(passport==null) {
			HttpServletResponse rs = (HttpServletResponse) response;
			rs.sendRedirect(rq.getContextPath()+"/connexion");
			return;
		}
				
				
		if(!HttpServer.checkIdentity(rq, passport)) {
			
			session.setAttribute("passport", null);
			session.invalidate();
			
			Cookie cookie = HttpServer.getCookie(rq, "rememberMe");
			
			if(cookie!=null) {
				
				String token = cookie.getValue(); 				
				
				HttpServer.removeRememberMe(rq, token);
			}
			HttpServletResponse rs = (HttpServletResponse) response;
			rs.sendRedirect(rq.getContextPath()+"/connexion");
			return;
		}								
		chain.doFilter(request, response);
	}
}
