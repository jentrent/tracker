
CREATE TABLE tracker.ACCOUNT (
account_id serial PRIMARY KEY,
email VARCHAR ( 50 ) UNIQUE NOT NULL,
first_name VARCHAR ( 50 ) NOT NULL,
last_name VARCHAR ( 50 ) NOT NULL,
role INTEGER NOT NULL,
pw_hash VARCHAR NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE tracker.project(
project_id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL,
description VARCHAR NOT NULL,
is_open BOOLEAN DEFAULT TRUE,
created_by int REFERENCES tracker.account(account_id),
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE tracker.ISSUE (
issue_id SERIAL PRIMARY KEY,
title VARCHAR(50) NOT NULL,
type INTEGER NOT NULL,
status INTEGER,
priority INTEGER constraint valid_number 
      check (priority <= 5),
description VARCHAR NOT NULL,
due DATE,
project_id int REFERENCES tracker.project(project_id),
created_by integer REFERENCES tracker.account(account_id),
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE tracker.ASSIGNEE (
assignee_id serial PRIMARY KEY,
role INTEGER NOT NULL,
issue_id INTEGER REFERENCES tracker.issue(issue_id) NOT NULL,
account_id INTEGER REFERENCES tracker.account(account_id) NOT NULL,
created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

ALTER TABLE tracker.issue
ADD COLUMN project int REFERENCES tracker.project(project_id);

ALTER TABLE tracker.issue
ALTER COLUMN status TYPE INTEGER
USING status::integer;

ALTER TABLE tracker.issue
ALTER COLUMN type TYPE INTEGER
USING type::integer;


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
