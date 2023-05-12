//package comon.interceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.servlet.HandlerInterceptor;
//
//public class LoginCheckInterceptor implements HandlerInterceptor {
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		if (request.getRequestURI().indexOf("/re") >= 0 && request.getSession().getAttribute("user") == null) {
//			request.getSession().setAttribute("message", "로그인 후 사용하실 수 있습니다.");
//			response.sendRedirect("/login.do");
//			return false;
//		} else {
//			return true;
//		}
//	}
//	
//}
