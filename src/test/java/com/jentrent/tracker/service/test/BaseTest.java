package com.jentrent.tracker.service.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.security.auth.login.AccountException;

import org.junit.BeforeClass;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Assignee;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.Priority;
import com.jentrent.tracker.model.Project;
import com.jentrent.tracker.model.Role;
import com.jentrent.tracker.model.Status;
import com.jentrent.tracker.model.Type;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.AccountServiceImpl;
import com.jentrent.tracker.service.IssueService;
import com.jentrent.tracker.service.IssueServiceImpl;
import com.jentrent.tracker.service.ProjectService;
import com.jentrent.tracker.service.ProjectServiceImpl;
import com.jentrent.tracker.service.TrackerException;

public class BaseTest{

	private static AccountService accountService;

	private static IssueService issueService;

	private static ProjectService projectService;

	@BeforeClass
	public static void setUp() throws AccountException{

		accountService = new AccountServiceImpl();
		issueService = new IssueServiceImpl();
		projectService = new ProjectServiceImpl();

	}

	protected AccountService getAccountService(){

		return accountService;
	}

	protected IssueService getIssueService(){

		return issueService;
	}

	protected ProjectService getProjectService(){

		return projectService;
	}

	protected Account createAccount() throws TrackerException{

		Account account = accountService.createAccount(createAccountWithoutSaving());
		return account;
	}

	protected Account createAccountWithoutSaving() throws TrackerException{

		String seed = Long.toString(System.currentTimeMillis());
		Account account = new Account();
		account.setEmail("TEST_" + seed + "@test.com");
		account.setFirstName("firstName_" + seed);
		account.setLastName("lastName_" + seed);
		account.setPassword("pw_" + seed);
		account.setRole(Role.PM);
		account.setCreated(new Date());
		account.setModified(new Date());
		return account;

	}

	protected Project createProject(Account account) throws TrackerException{

		String seed = Long.toString(System.currentTimeMillis());
		Project project = new Project();
		project.setIsOpen(false);
		project.setCreatedBy(account);
		project.setName("TEST_" + seed + "_title");
		project.setDescription(seed + "_Description");

		project.setCreated(new Date());
		project.setModified(new Date());

		return projectService.createProject(project);
	}

	protected Issue createIssue(Account account) throws TrackerException{

		Issue issue = createIssueWithoutSaving(account);

		return issueService.createIssue(issue);
	}

	protected Issue createIssueWithoutSaving(Account account) throws TrackerException{

		String seed = Long.toString(System.currentTimeMillis());
		Issue issue = new Issue();
		issue.setType(Type.BUG);
		issue.setStatus(Status.CLOSED);
		issue.setPriority(Priority.HIGH);
		issue.setTitle("TEST_" + seed + "_title");
		issue.setDescription(seed + "_Description");
		issue.setCreatedBy(account);

		issue.setCreated(new Date());
		issue.setModified(new Date());

		issue.setAssignees(addAssigneesWithoutSaving(issue));

		return issue;
	}

	private List<Assignee> addAssigneesWithoutSaving(Issue issue) throws TrackerException{

		List<Assignee> assignees = new LinkedList<Assignee>();

		for(int i = 0; i < 5; i++){

			Assignee a = new Assignee();
			a.setAccount(createAccount());

			if(i == 0){
				a.setRole(Role.DEVELOPER);
			}

			if(i == 1){
				a.setRole(Role.ANALYST);
			}

			if(i == 2){
				a.setRole(Role.TESTER);
			}

			if(i == 3){
				a.setRole(Role.PM);
			}

			if(i == 4){
				a.setRole(Role.SYSADMIN);
			}

			a.setIssue(issue);
			a.setCreated(new Date());
			a.setModified(new Date());
			assignees.add(a);
		}

		return assignees;
	}

}
