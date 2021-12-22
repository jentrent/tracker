package com.jentrent.tracker.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.TrackerException;

@ManagedBean
@SessionScoped
public class AccountView extends BaseView{

	private Account account;

	@Autowired
	private AccountService accountService;

	@PostConstruct
	public void init(){

		super.init();

		if(account == null){
			account = new Account();
		}

	}

	public String submitCreate(){

		try{

			account = accountService.createAccount(account);

			if(account != null){

				set("account", account);

				return "issueList";

			}else{

				return "account";
			}

		}catch(TrackerException e){

			saveErrors(e);

			return "account";
		}

	}

	public String submitUpdate(){

		try{

			account = accountService.updateAccount(account);

			return "issueList";

		}catch(TrackerException e){

			saveErrors(e);

			return "account";
		}

	}

	public String submitCancel(){

		return "issueList";
	}

	public String setForEdit(){

		account = (Account) get("account");

		return "account";
	}

	public Account getAccount(){

		return account;
	}

	public void setAccount(Account account){

		this.account = account;
	}

}
