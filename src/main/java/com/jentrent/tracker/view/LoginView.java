package com.jentrent.tracker.view;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.TrackerException;

@ManagedBean
@SessionScoped
public class LoginView extends BaseView implements Serializable{

	private String email;
	private String password;

	@Autowired
	private AccountService service;

	@PostConstruct
	public void init(){

		super.init();
	}

	public String submit(){

		try{

			Account account = service.validateAccount(email, password);

			if(account != null){

				set("account", account);

				return "issueList";

			}else{

				return "login";
			}

		}catch(TrackerException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public void logout() throws IOException{

		set("account", null);

		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		ctx.invalidateSession();
		ctx.redirect("/tracker?faces-redirect=true");

	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public String getPassword(){

		return password;
	}

	public void setPassword(String password){

		this.password = password;
	}

}
