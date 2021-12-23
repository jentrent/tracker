package com.jentrent.tracker.view;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SecurityFilter implements Filter{

	private String accountPage;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

		HttpServletRequest req = (HttpServletRequest) request;

		if(req.getSession().getAttribute("account") == null && !req.getPathInfo().equals(accountPage)){

			RequestDispatcher dispatcher = req.getSession().getServletContext().getRequestDispatcher("/login.xhtml");
			dispatcher.forward(request, response);

		}else{

			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException{

		accountPage = filterConfig.getInitParameter("accountPage");
	}

	public void destroy(){

	}

}
