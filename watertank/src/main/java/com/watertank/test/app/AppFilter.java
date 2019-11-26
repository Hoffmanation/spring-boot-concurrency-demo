package com.watertank.test.app;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Component;

import com.watertank.test.rest.LoginRestResource;

/**
 * An Http Filter class that will intercept every http client request and
 * validate if an http session already exist and if the user is trying to
 * retrieve a a secured resource from out app
 * 
 * @author The Hoff
 *
 */
@Component
public class AppFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(AppFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String url = req.getRequestURL().toString();

		if (url.contains("index") && (session == null || session.getAttribute(LoginRestResource.USER) == null)) {
			log.info("A client is tryed to access a secured resources with no credentials");
			res.sendRedirect(req.getContextPath() + "/registration.html");
		}
		
		
		else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}