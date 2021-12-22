package com.jentrent.tracker.seed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

public class DataSeeder{

	private AccountService accountService;
	private IssueService issueService;
	private ProjectService projectService;

	private String rootDir = "/Users/khiggins/Dev/eclipse/tracker/src/main/resources/";

	public DataSeeder(){

		accountService = new AccountServiceImpl();
		issueService = new IssueServiceImpl();
		projectService = new ProjectServiceImpl();

	}

	private void run(){

		try{
			File f = new File(rootDir + "accounts.csv");

			BufferedReader reader = new BufferedReader(new FileReader(f));

			String line = reader.readLine();

			while(line != null){
				System.out.println(line);

				String[] atts = line.split(",");

				Account a = new Account();
				a.setFirstName(atts[0]);
				a.setLastName(atts[1]);
				a.setPassword(atts[2]);
				a.setEmail(atts[3]);
				a.setRole(Role.getValue(Integer.parseInt(atts[4])));
				a.setCreated(new Date());
				a.setModified(new Date());

				accountService.createAccount(a);

				System.out.println("Created account for: " + a.getEmail());
				line = reader.readLine();

			}

			f = new File(rootDir + "projects.csv");

			reader = new BufferedReader(new FileReader(f));

			line = reader.readLine();
			line = reader.readLine();

			int count = 0;

			while(line != null){

				count++;
				System.out.println(line);

				String[] atts = line.split(",");

				Project p = new Project();
				p.setName(atts[0]);
				p.setDescription(atts[1]);

				p.setCreatedBy(accountService.readAccount(Integer.parseInt(atts[2])));

				p.setIsOpen(Boolean.TRUE);

				if(count % 5 == 0){
					p.setIsOpen(Boolean.FALSE);
				}

				p.setCreated(new Date());
				p.setModified(new Date());
				projectService.createProject(p);

				System.out.println("Created project for: " + p.getName());
				line = reader.readLine();

			}

			f = new File(rootDir + "issues.csv");

			reader = new BufferedReader(new FileReader(f));

			line = reader.readLine();
			line = reader.readLine();

			while(line != null){
				System.out.println(line);

				String[] atts = line.split(",");
//TITLE	DESCRIPTION	TYPE	PRIORITY	STATUS	  ANALSYST	DEVELOPER TESTER	SYSADMIN	PM	PROJECT
//  0      1         2        3           4        5            6         7        8          9    10

				Issue i = new Issue();
				i.setTitle(atts[0]);
				i.setDescription(atts[1]);

				i.setType(Type.getValue(Integer.parseInt(atts[2])));
				i.setPriority(Priority.getValue(Integer.parseInt(atts[3])));
				i.setStatus(Status.getValue(Integer.parseInt(atts[4])));

				for(int k = 1; k <= 5; k++){

					System.out.println("Looking up accountId: " + atts[k + 4]);
					Account a = accountService.readAccount(Integer.parseInt(atts[k + 4]));

					Role role = null;

					if(k == 1){
						role = Role.ANALYST;
					}else if(k == 2){
						role = Role.DEVELOPER;
					}else if(k == 3){
						role = Role.TESTER;
					}else if(k == 4){
						role = Role.SYSADMIN;
					}else if(k == 5){
						role = Role.PM;
						i.setCreatedBy(a);
					}

					i.addAssignee(a, role);
				}

				i.setProject(projectService.readProject(Integer.parseInt(atts[10])));
				i.setCreated(new Date());
				i.setModified(new Date());

				issueService.createIssue(i);

				System.out.println("Created project for: " + i.getTitle());
				line = reader.readLine();

			}

		}catch(Exception e){
			e.printStackTrace();

			if(e instanceof TrackerException){

				TrackerException te = (TrackerException) (e);

				for(String s: te.getErrors()){
					System.out.println(s);
				}

			}

		}

	}

	public Account createAccount() throws TrackerException{

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

	public Project createProject(Account account) throws TrackerException{

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

	public Issue createIssue(Account account, Project project) throws TrackerException{

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

		issue.setProject(project);

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

			issue.setAssignees(assignees);
		}

		return issue;

	}

	public static void main(String[] args){

		DataSeeder ds = new DataSeeder();
		ds.run();
	}

}
