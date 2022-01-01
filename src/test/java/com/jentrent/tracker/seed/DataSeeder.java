package com.jentrent.tracker.seed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.Priority;
import com.jentrent.tracker.model.Project;
import com.jentrent.tracker.model.Role;
import com.jentrent.tracker.model.Status;
import com.jentrent.tracker.model.Type;
import com.jentrent.tracker.service.AccountService;
import com.jentrent.tracker.service.IssueService;
import com.jentrent.tracker.service.ProjectService;
import com.jentrent.tracker.service.TrackerException;

public class DataSeeder{

	@Autowired
	private AccountService accountService;

	@Autowired
	private IssueService issueService;

	@Autowired
	private ProjectService projectService;

	private File dataDirectory;

	private Logger log = Logger.getLogger(DataSeeder.class.getName());

	public DataSeeder(String dataDirectory){

		log.info("DataSeeder starting...");

		this.dataDirectory = new File(dataDirectory);

		log.info("Loading data from " + this.dataDirectory);

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/webapp/WEB-INF/applicationContext.xml");

		accountService = context.getBean("AccountService", AccountService.class);
		issueService = context.getBean("IssueService", IssueService.class);
		projectService = context.getBean("ProjectService", ProjectService.class);

		context.close();

	}

	private void run(){

		try{

			seedAccounts();

			seedProjects();

			seedIssues();

			log.info("DataSeeder finished!");

			System.exit(-1);

		}catch(Exception e){

			e.printStackTrace();

			if(e instanceof TrackerException){

				TrackerException te = (TrackerException) (e);

				for(String s: te.getErrors()){
					log.log(Level.SEVERE, s);
				}

			}

		}

	}

	private void seedAccounts() throws Exception{

		log.info("Running Accounts");

		File f = new File(dataDirectory, "/accounts.csv");

		BufferedReader reader = new BufferedReader(new FileReader(f));

		String line = reader.readLine();

		while(line != null){

			log.info(line);

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

			log.info("Created account with email: " + a.getEmail());
			line = reader.readLine();

		}

		reader.close();

	}

	private void seedProjects() throws Exception{

		log.info("Running Projects");

		File f = new File(dataDirectory + "/projects.csv");

		BufferedReader reader = new BufferedReader(new FileReader(f));

		String line = reader.readLine();
		line = reader.readLine();

		int count = 0;

		while(line != null){

			count++;

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

			log.info("Created project with name: " + p.getName());
			line = reader.readLine();

		}

		reader.close();

	}

	private void seedIssues() throws Exception{

		log.info("Running Issues");

		File f = new File(dataDirectory, "/issues.csv");

		BufferedReader reader = new BufferedReader(new FileReader(f));

		String line = reader.readLine();
		line = reader.readLine();

		while(line != null){

			String[] atts = line.split(",");

			Issue i = new Issue();
			i.setTitle(atts[0]);
			i.setDescription(atts[1]);

			i.setType(Type.getValue(Integer.parseInt(atts[2])));
			i.setPriority(Priority.getValue(Integer.parseInt(atts[3])));
			i.setStatus(Status.getValue(Integer.parseInt(atts[4])));

			for(int k = 1; k <= 5; k++){

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

			log.info("Created issue with title: " + i.getTitle());

			line = reader.readLine();
		}

		reader.close();

	}

	public static void main(String[] args){

		DataSeeder dataSeeder = new DataSeeder(args[0]);

		dataSeeder.run();
	}

}
