package com.jentrent.tracker.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.model.Issue;
import com.jentrent.tracker.model.Status;
import com.jentrent.tracker.service.TrackerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/applicationContext.xml")
public class IssueServiceTest extends BaseTest{

	@Test
	public void testCreateIssueWithValidData() throws TrackerException{

		Account account = createAccount();

		Issue issue = createIssueWithoutSaving(account);

		Issue created = getIssueService().createIssue(issue);

		assertNotNull(created);
	}

	@Test(expected = TrackerException.class)
	public void testCreateIssueWithEmtpyIssueData() throws TrackerException{

		getIssueService().createIssue(new Issue());
	}

	@Test
	public void testCreateIssueWithInvalidData() throws TrackerException{

		try{

			getIssueService().createIssue(new Issue());

		}catch(TrackerException e){

			assertTrue(e.hasErrors());

		}

	}

	@Test
	public void testReadIssue() throws TrackerException{

		Account account = createAccount();
		Issue issue = createIssue(account);

		Issue check = getIssueService().readIssue(issue.getIssueId());
		assertNotNull(check);
	}

	@Test
	public void testUpdateIssue() throws TrackerException{

		Account account = createAccount();
		Issue issue = createIssue(account);

		String newText = "Updated Issue" + System.currentTimeMillis();
		issue.setDescription(newText);
		issue.setStatus(Status.CLOSED);

		Issue check = getIssueService().updateIssue(issue);

		assertTrue(check.getDescription().equals(newText));
		assertTrue(check.getStatus().equals(Status.CLOSED));
	}

	public void testDeleteIssue() throws TrackerException{

		Account account = createAccount();
		Issue issue = createIssue(account);

		getIssueService().deleteIssue(issue);

		Issue check = getIssueService().readIssue(issue.getIssueId());

		assertNull(check);

	}

	@Test
	public void testListIssuesForAll() throws TrackerException{

		Account account = createAccount();

		List<Issue> list = new ArrayList<Issue>();
		Issue issue1 = createIssue(account);
		Issue issue2 = createIssue(account);
		Issue issue3 = createIssue(account);
		list.add(issue1);
		list.add(issue2);
		list.add(issue3);

		List<Issue> check = getIssueService().listIssuesForCreator(account);

		assertTrue(list.size() == check.size());

		for(Issue issue: list){

			boolean found = false;

			for(Issue c: check){

				if(issue.getCreatedBy().getAccountId().equals(c.getCreatedBy().getAccountId())){
					found = true;
				}

			}

			assertTrue(found);

		}

	}

}
