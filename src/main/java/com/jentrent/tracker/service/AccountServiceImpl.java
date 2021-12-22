package com.jentrent.tracker.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jentrent.tracker.dao.AccountDAO;
import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.AccountStats;

@Component("AccountService")
public class AccountServiceImpl extends BaseService implements AccountService{

	@Autowired
	private AccountDAO accountDAO;

	public Account validateAccount(String email, String password) throws TrackerException{

		Account account = readAccount(email);

		if(account != null && account.getPassword().equals(password)){
			return account;
		}else{
			throw new TrackerException("User ID and/or password is incorrect");
		}

	}

	public Account createAccount(Account account) throws TrackerException{

		Date d = new Date();
		account.setCreated(d);
		account.setModified(d);

		validate(account);

		checkForDuplicateAccount(account);
		return accountDAO.createAccount(account);
	}

	public Account readAccount(Integer id){

		return accountDAO.readAccount(id);
	}

	public Account readAccount(String email){

		return accountDAO.readAccount(email);
	}

	public Account updateAccount(Account updatedAccount) throws TrackerException{

		updatedAccount.setModified(new Date());

		validate(updatedAccount);

		Account checkEmailAccount = readAccount(updatedAccount.getAccountId());

		updatedAccount.setCreated(checkEmailAccount.getCreated());

		if(updatedAccount.getEmail().equals(checkEmailAccount.getEmail())){
			return accountDAO.updateAccount(updatedAccount);
		}else{
			checkForDuplicateAccount(updatedAccount);
			return accountDAO.updateAccount(updatedAccount);
		}

	}

	public void deleteAccount(Account account){

		accountDAO.deleteAccount(account);
	}

	private void checkForDuplicateAccount(Account account) throws TrackerException{

		Account check = accountDAO.readAccount(account.getEmail());

		if(check != null && !check.getAccountId().equals(account.getAccountId())){
			throw new TrackerException("The following email is already associated with an existing account: " + account.getEmail());
		}

	}

	public void setAccountDAO(AccountDAO accountDAO){

		this.accountDAO = accountDAO;
	}

	public List<Account> listAccountsForAll(){

		return accountDAO.listAccountsForAll();
	}

	public Map<Integer, AccountStats> listAccountStatsForAll(){

		return accountDAO.listAccountStatsForAll();
	}

}
