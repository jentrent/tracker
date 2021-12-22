package com.jentrent.tracker.dao;

import java.util.List;
import java.util.Map;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.AccountStats;

public interface AccountDAO{

	public Account createAccount(Account account);

	public Account readAccount(Integer accountId);

	public Account readAccount(String email);

	public Account updateAccount(Account account);

	public void deleteAccount(Account account);

	public List<Account> listAccountsForAll();

	public Map<Integer, AccountStats> listAccountStatsForAll();

}
