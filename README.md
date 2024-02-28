# Project Summary
- RESTful API for car advertisements
- Used Gradle for modularization

# Tomcat section
- Made use of a Gradle script to build a .war file and deploy it into the CATALINA_HOME folder
- Used connection pooling to manage the database connection
- Implemented Factory design pattern to create an array based DAO and a database DAO for prod profile
- Session based authentication
- Used FreeMarker Template Language for rendering

# Spring section
- Has three distinct profiles: one using an in-memory DAO, one using JDBC, and finally one using JPA and Hibernate
- Profiles are set based on the contents of yml files
- For the JDBC profile, implemented CRUD functions for two different models following DRY principles, and used pooling with HikariCP for database connection
- Created exception-handling controllers
