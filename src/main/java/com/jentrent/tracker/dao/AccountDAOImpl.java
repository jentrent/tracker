package com.jentrent.tracker.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.AccountStats;

@Component("AccountDAO")
public class AccountDAOImpl extends BaseDAO implements AccountDAO{

	public Account createAccount(Account account){

		account.setPassword(PasswordUtil.encrypt(account.getPassword()));

		beginTrx();

		getEm().persist(account);

		commitTrx();

		return readAccount(account.getEmail());
	}

	public Account readAccount(Integer accountId){

		getEm().clear();

		Query q = getEm().createQuery("select a from Account a where a.accountId = :accountId");
		q.setParameter("accountId", accountId);

		Account account = null;

		try{
			account = (Account) q.getSingleResult();
		}catch(Exception ignored){
		}

		if(account != null){

			try{
				String pwClear = PasswordUtil.decrypt(account.getPassword());
				account.setPassword(pwClear);
			}catch(Exception e){
				throw new RuntimeException(e);
			}

		}

		return account;
	}

	public Account readAccount(String email){

		getEm().clear();

		Query q = getEm().createQuery("select a from Account a where a.email = :email");
		q.setParameter("email", email);
		Account account = null;

		try{
			account = (Account) q.getSingleResult();
		}catch(NoResultException ignored){
		}

		if(account != null){

			try{
				account.setPassword(PasswordUtil.decrypt(account.getPassword()));
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}

		return account;
	}

	public Account updateAccount(Account account){

		account.setPassword(PasswordUtil.encrypt(account.getPassword()));

		beginTrx();

		getEm().merge(account);

		commitTrx();

		return readAccount(account.getEmail());
	}

	public void deleteAccount(Account account){

		beginTrx();

		if(getEm().contains(account)){
			getEm().remove(account);
		}else{
			getEm().remove(getEm().merge(account));
		}

		commitTrx();

	}

	public List<Account> listAccountsForAll(){

		getEm().clear();

		TypedQuery<Account> q = getEm().createQuery("select a from Account a order by a.lastName", Account.class);

		return (List<Account>) q.getResultList();

	}

	public Map<Integer, AccountStats> listAccountStatsForAll(){

		getEm().clear();

		List<Object[]> results = getEm().createQuery(
				"select a.account.accountId, count(a), count(distinct(a.issue.project)) AS total from Assignee as a group by a.account.accountId")
				.getResultList();

		Map<Integer, AccountStats> map = new HashMap<Integer, AccountStats>();

		for(Object[] result: results){

			AccountStats as = new AccountStats();
			as.setAccountId((Integer) result[0]);
			as.setIssueCount(Integer.parseInt(((Long) result[1]).toString()));
			as.setProjectCount(Integer.parseInt(((Long) result[2]).toString()));

			map.put(as.getAccountId(), as);
		}

		return map;

	}

}
