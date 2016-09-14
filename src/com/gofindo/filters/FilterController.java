package com.gofindo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class FilterController implements Filter{

	

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization,x-requested-with,x-requested-with,Origin, Accept, x-auth-token, Content-Type, Access-Control-Request-Method, Access-Control-Request-Header");
		final String authHeader = request.getHeader("Authorization");
		String requestMethodType = request.getMethod();
		try {
			if (!requestMethodType.equalsIgnoreCase("OPTIONS")) {
				if (authHeader == null || !authHeader.startsWith("Basic ")) {
					response.sendError(401, "Invalid Authorization Token.");
				} else {
					final String token = authHeader.substring(6); // The part after "Bearer "
					try {
						final Claims claims = Jwts.parser().setSigningKey("Constants.SECRET_KEY").parseClaimsJws(token) .getBody();
						request.setAttribute("claims", claims);
						chain.doFilter(req, response);
					} catch (final SignatureException e) {
						response.sendError(401, "Invalid Authorization.");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.sendError(401, "Invalid Authorization.");
			} catch (IOException sendErrorException) {
				sendErrorException.printStackTrace();
			}
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("Filter is destroyed.");
		
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("Filter is initiated.");
		
	}

}
