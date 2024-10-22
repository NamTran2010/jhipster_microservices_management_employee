1. Business Requirement

- Make an application that can support the helpdesk doing a basic task
- Helpdesk can edit any employee information.
- When the helpdesk edit an employee's job, the helpdesk can track all the employee's job history
- Helpdesk can create/edit/update documents related to employees. Only helpdesk has admin role can do this action

2. Task List

   Task 1: Use Jhipster to generate code

- Make sure you're using Jhipster v7.3.0
- Make sure to use only 1 JDL file to generate Employee and Gateway service
  - AuthenticationType = JWT (We only use this in our app)
  - serviceDiscoveryType = eureka
  - buildTool = maven
- Make sure using Spring Boot as backend code
- Make sure using Angular as frontend code
- Make sure Employee Entities description will be same as tables in hr DB
- Make sure every API in RestController calls to Service Component, not Repository Component
- Make sure every listing API can do pagination and sorting
- Make sure API is protected by a gateway
- Make sure CRUD API related to document is only valid for Role_Admin and employee

  Task 2: Add Salary Field in entity Job History data

- Create a custom method to handle tracking jobs
- Update employee create new job history
- Make sure when we fail in creating job history, no update for employee
- Create new employee, need create job history?

  Task 3: Add document service in our JDL file

  - Document Table and DocumentType Table
  - Make sure document service using h2 as dev database

  Task 4: Create frontend for helpdesk can do CRUD Document operation on Employee while editing the employee entity

  Task 5: Update backend codebase for Document service

3.  Solution & Tips
