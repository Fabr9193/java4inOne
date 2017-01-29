package feedrss.feedrss;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CorsFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2)
			throws ServletException, IOException {
		arg1.addHeader("Access-Control-Allow-Origin", "*");
   		arg1.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
   		arg1.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-req filterChain.doFilter(arg0, arg1)");
		
	}

}
