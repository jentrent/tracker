package com.jentrent.tracker.service;

import java.util.List;
import java.util.Map;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.AccountStats;

public interface AccountService{

	public Account validateAccount(String email, String password) throws TrackerException;

	public Account createAccount(Account account) throws TrackerException;

	public Account readAccount(Integer accountId);

	public Account readAccount(String email);

	public Account updateAccount(Account updatedAccount) throws TrackerException;

	public void deleteAccount(Account account);

	public List<Account> listAccountsForAll();

	public Map<Integer, AccountStats> listAccountStatsForAll();

}
