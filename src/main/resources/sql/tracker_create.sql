
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
