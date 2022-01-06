<img src="readme_assets/tracker_readme.jpg" 
	 alt="Tracker" 
	 width="150" height="45">
	 
<a href="#key-features">Video Demo</a> •
<a href="http://jentrent.com/tracker" target="_blank">Live Site</a> 

## Table of Contents
- [Description](#description)
	- [Features](#features)
	- [Technologies](#technologies)
- [Install](#install)
	- [Dependencies](#dependencies)
	- [Setup](#setup)
	- [Data Seeder](#data-seeder)
- [Future Features](#future-features)
- [Author](#author)

## Description
**Tracker** is a full-stack Java web app for managing IT/software issues and bugs. It's built on Java/JEE, JSF/Primefaces,
Spring, JPA/Hibernate, and PostgreSQL. The application is designed using the Model-View-Controller (MVC) approach to web apps as well as other well-established object-oriented design patterns.

### Features
Tracker was inspired by various well-known issue/bug management software. As such, Tracker includes the basic tracking features needed to group, assign, and manage issues/bugs through an established software development lifecylce (SDLC) workflow.  It's key features include the following:
- Create, update, and delete an issue/bug
- Assign issues/bugs to various team members
- Workflow an issue/bug through the SDLC process
- Create, update, and delete a project
- Create and update a user account
- List, filter, and find issues/bugs
- List, filter, and find projects

### Technologies
- Back-end: Java/JEE, JPA/Hibernate/JDBC/SQL, Spring, JUnit
- Front End: JSF, JSTL, PrimeFaces, HTML, CSS
- Default app server: Tomcat
- Default DB server: PostgreSQL

## Install
There is <a href="#key-features">Video Demo</a> and <a href="http://jentrent.com/tracker" target="_blank">Live Site</a>. To setup and run the project yourself, use the following:

### Dependencies

- Java 8 or higher
- Maven
- Java web app server
- PostgreSQL database server

### Setup
1. Create a postgres user account for DB access. 
2. Log into postgres and run the Tracker DB create script [`tracker_create.sql`](src/main/resources/sql/tracker_create.sql)
3. Add the DB account/pw to the Test [`persistence.xml`](src/test/resources/META-INF/persistence.xml)
4. Add the DB account/pw to the main deployment [`persistence.xml`](src/main/resources/META-INF/persistence.xml)
5. Build the application using `mvn clean install`
6. Deploy the `target/tracker-1.0.war' to your app server.

The build will run the complete set of [JUnit Tests](src/test/java/com/jentrent/tracker/service/test/) verify the setup is working as expected. To skip the tests, add the following to the maven build command:  `-Dmaven.test.skip=true`

### Data Seeder
A [`Data Seeder`](src/test/java/com/jentrent/tracker/seed/DataSeeder.java) is provided that loads the DB with the appropriate data (Issues, Projects, Accounts) to enable the viewing and use of Tracker for evaluation purposes. To run the process, using the following: `mvn exec:java`

## Future Features
There are a number of future features that will be added to Tracker, including the following:
- Ability to add notes to an issue, including screenprints and updates
- Email notifications for assigned issues and tracking status changes
- Rest API for all entity CRUD operations
- Web look-and-feel updates, including styling

## Author

Jennifer Trent

<a href="http://jentrent.com" target="_blank">Website</a>

[![Follow me on GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/jentrent) 
[![Follow me on LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/jenniferltrent/)
[![Email me](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:Jltrent12@gmail.com)

</div>





---
