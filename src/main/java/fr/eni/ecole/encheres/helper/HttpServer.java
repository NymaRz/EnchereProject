//package fr.eni.ecole.encheres.helper;
//
//import java.util.Map;
//
//import fr.eni.gamemanager.bo.Passport;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//
//public class HttpServer {
//
//	public static Cookie getCookie(HttpServletRequest request,String name) {		
//		Cookie [] cookies = request.getCookies();			
//		if(cookies!=null) for (Cookie cookie : cookies) {
//			if(cookie.getName().equals(name)) {
//				return cookie;
//			}
//		}
//		return null;
//	}
//	
//	
//	public static void removeRememberMe(HttpServletRequest request,String token) {
//		@SuppressWarnings("unchecked")
//		Map<String, Passport> sessions = (Map<String, Passport>) request.getServletContext().getAttribute("sessions");
//		sessions.remove(token);
//	}
//	
//	public static boolean checkIdentity(HttpServletRequest request, Passport passport) {
//
//		if( 
//			passport.getIpv4().equals(request.getRemoteAddr()) &&
//			passport.getUserAgent().equals(request.getHeader("User-Agent"))	
//				) {
//			return true;
//		}
//		return false;
//	}
//	
//	
//	public static Passport getPassport(HttpServletRequest request,String token) {
//		@SuppressWarnings("unchecked")
//		Map<String, Passport> sessions = (Map<String, Passport>) request.getServletContext().getAttribute("sessions");
//		
//		return sessions==null?null: sessions.get(token);
//	}
//	
//	
//}
