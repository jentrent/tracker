package com.jentrent.tracker.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.AccountStats;
import com.jentrent.tracker.model.Role;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.IssueService;

@ManagedBean
@SessionScoped
public class AccountListView extends BaseView implements Serializable{

	private List<Account> accounts;

	private List<Account> filteredAccounts;

	private boolean globalFilterOnly;

	private Map<Integer, AccountStats> accountStatsMap = new HashMap<Integer, AccountStats>();

	@Autowired
	private AccountService accountService;

	@Autowired
	private IssueService issueService;

	private static Role[] roles;

	static{

		List<Role> list = new LinkedList<Role>();
		list.add(Role.ANALYST);
		list.add(Role.DEVELOPER);
		list.add(Role.TESTER);
		list.add(Role.SYSADMIN);
		list.add(Role.PM);
		roles = list.toArray(new Role[list.size()]);

	}

	@PostConstruct
	public void init(){

		super.init();

		globalFilterOnly = false;

		accounts = accountService.listAccountsForAll();
	}

	public void toggleGlobalFilter(){

		setGlobalFilterOnly(!isGlobalFilterOnly());
	}

	public boolean isGlobalFilterOnly(){

		return globalFilterOnly;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly){

		this.globalFilterOnly = globalFilterOnly;
	}

	public List<Account> getFilteredAccounts(){

		return filteredAccounts;
	}

	public void setFilteredAccounts(List<Account> filteredAccounts){

		this.filteredAccounts = filteredAccounts;
	}

	public Role[] getRoles(){

		return roles;
	}

	public List<Account> getAccounts(){

		accountStatsMap = accountService.listAccountStatsForAll();

		return accounts;
	}

	public Integer getIssueCountForAccount(Integer accountId){

		AccountStats stats = accountStatsMap.get(accountId);
		return stats != null ? stats.getIssueCount() : 0;

	}

	public Integer getProjectCountForAccount(Integer accountId){

		AccountStats stats = accountStatsMap.get(accountId);
		return stats != null ? stats.getProjectCount() : 0;

	}

}
