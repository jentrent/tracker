package com.jentrent.tracker.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jentrent.tracker.model.Account;
import com.jentrent.tracker.service.TrackerException;

public class AccountServiceTest extends BaseTest{

	@Test
	public void testCreateAccountWithValidData() throws TrackerException{

		assertNotNull(createAccount());
	}

	@Test
	public void testCreateAccountWithInValidData(){

		Account notAllFields = new Account();

		try{
			getAccountService().createAccount(notAllFields);
		}catch(TrackerException e){
			assertTrue(e.hasErrors());
		}

	}

	@Test
	public void testValidateUserWithValidCredentials() throws TrackerException{

		Account account = createAccount();
		Account check = getAccountService().validateAccount(account.getEmail(), account.getPassword());
		assertNotNull(check.getAccountId());
	}

	@Test(expected = TrackerException.class)
	public void testValidateUserWithInvalidCredentials() throws TrackerException{

		Account account = createAccount();
		getAccountService().validateAccount(account.getEmail(), System.currentTimeMillis() + "");
	}

	@Test
	public void testReadAccountInteger() throws TrackerException{

		Account account = createAccount();

		Account check = getAccountService().readAccount(account.getAccountId());
		assertNotNull(check);
	}

	@Test
	public void testReadAccountString() throws TrackerException{

		Account check = getAccountService().readAccount(createAccount().getEmail());
		assertNotNull(check);
	}

	@Test
	public void testUpdateAccount() throws TrackerException{

		Account account = createAccount();
		String newName = System.currentTimeMillis() + "newName";
		account.setFirstName(newName);
		Account check = getAccountService().updateAccount(account);
		assertTrue(check.getFirstName().equals(newName));
	}

	public void testDeleteAccount() throws TrackerException{

		Account account = createAccount();

		getAccountService().deleteAccount(account);

		Account check = getAccountService().readAccount(account.getAccountId());

		assertNull(check);

	}

}
