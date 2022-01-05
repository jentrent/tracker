begin;
	delete from tracker.assignee;
	delete from tracker.issue;
	delete from tracker.project;
	delete from tracker.account;
	ALTER SEQUENCE tracker.account_account_id_seq RESTART WITH 1;
	ALTER SEQUENCE tracker.project_project_id_seq RESTART WITH 1;
	ALTER SEQUENCE tracker.issue_issue_id_seq RESTART WITH 1;
end;

begin;
	delete from tracker.assignee where issue_id in(select issue_id from tracker.issue where title like 'TEST%');
	delete from tracker.issue where title like 'TEST%';
	delete from tracker.project where name like 'TEST%';
	delete from tracker.account where email like 'TEST%';
end;
