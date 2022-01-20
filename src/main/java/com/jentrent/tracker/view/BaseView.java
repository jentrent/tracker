package com.jentrent.tracker.view;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.AccountServiceImpl;
import com.jentrent.tracker.service.TrackerException;

public class BaseView{

	private HttpSession session;

	private Account account;

	public BaseView(){

		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	@PostConstruct
	public void init(){

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext servletContext = (ServletContext) externalContext.getContext();
		WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getAutowireCapableBeanFactory().autowireBean(this);
	}

	protected Account getAccount(){

		if(get("account") == null){

			AccountService accountService = new AccountServiceImpl();
			account = accountService.readAccount("test@test.com");
			set("account", account);

		}else{
			return (Account) get("account");
		}

		return account;
	}

	protected void saveErrors(TrackerException e){

		for(String s: e.getErrors()){

			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null);

			FacesContext.getCurrentInstance().addMessage("msg", facesMsg);
		}

	}

	protected void saveError(String error){

		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null);

		FacesContext.getCurrentInstance().addMessage("msg", facesMsg);

	}

	protected Object getRequestParam(String name){

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		return req.getParameter(name);
	}

	protected void setRequestAttr(String name, Object value){

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		req.setAttribute(name, value);
	}

	protected Object getRequestAttr(String name){

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		return req.getParameter(name);
	}

	protected void set(String name, Object value){

		session.setAttribute(name, value);

	}

	protected Object get(String name){

		return session.getAttribute(name);
	}

	protected FacesContext getFacesContext(){

		return FacesContext.getCurrentInstance();
	}

}
