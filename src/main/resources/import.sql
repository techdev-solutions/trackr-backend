/* EMPLOYEES */
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (0, 0, 'admin', 'admin', 'Technical Administrator', 0, 0, 'BERLIN');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, vacationEntitlement) VALUES (1, 0, 'John', 'Johnson', 'Software Engineer', 80, 50000, 'BERLIN', '2014-02-01', 30);
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (2, 0, 'Adam', 'Smith', 'Software Consultant', 85, 55000, 'BERLIN', '2014-07-01');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (3, 0, 'William', 'Hanson', 'CEO', 85, 60000, 'BADEN_WUERTTEMBERG', '2013-09-01');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (4, 0, 'Bill', 'Rust', 'Processes Consultant', 87, 58000, 'HESSEN', '2014-01-01');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (5, 0, 'Jane', 'Dafoe', 'HR', 0, 500000, 'BADEN_WUERTTEMBERG', '2013-10-01');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (6, 0, 'Horace', 'Nottingham', 'External Consultant', 90, 0, 'BERLIN');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, leaveDate) VALUES (7, 0, 'Vladimir', 'Wichowsko', 'Software Engineer', 80, 49000, 'BERLIN', '2013-11-01', '2014-02-15');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (8, 0, 'June', 'Hooper', 'Software Architect', 90, 65000, 'BAYERN', '2014-09-01');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (9, 0, 'Todd', 'Floyd', 'HR', 0, 45000, 'BADEN_WUERTTEMBERG', '2014-03-16');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, leaveDate) VALUES (10, 0, 'Joanne', 'Doughty', 'Practicant', 0, 25000, 'SCHLESWIG_HOLSTEIN', '2013-11-01', '2013-12-15');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (11, 0, 'Sean', 'Robinson', 'Software Engineer', 75, 51000, 'MECKLENBURG_VORPOMMERN', '2014-05-01');

/* CREDENTIALS */
INSERT INTO credential (id, email, enabled, locale) VALUES (0, 'admin@techdev.de', true, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (1, 'moritz.schulze@techdev.de', true, 'de');
INSERT INTO credential (id, email, enabled, locale) VALUES (2, 'viktor.widiker@techdev.de', true, 'de');
INSERT INTO credential (id, email, enabled, locale) VALUES (3, 'alexander.hanschke@techdev.de', true, 'de');
INSERT INTO credential (id, email, enabled, locale) VALUES (4, 'adrian.krion@techdev.de', true, 'de');
INSERT INTO credential (id, email, enabled, locale) VALUES (5, 'angelika.gutjahr@techdev.de', true, 'de');
INSERT INTO credential (id, email, enabled, locale) VALUES (6, 'nikolaj.weise@techdev.de', true, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (7, 'noop1@techdev.de', false, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (8, 'noop2@techdev.de', false, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (9, 'noop3@techdev.de', false, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (10, 'noop4@techdev.de', false, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (11, 'noop5@techdev.de', false, 'en');

/* AUTHORITIES */
INSERT INTO authority (id, authority, authorityOrder) VALUES (12, 'ROLE_ADMIN', 0);
INSERT INTO authority (id, authority, authorityOrder) VALUES (13, 'ROLE_SUPERVISOR', 1);
INSERT INTO authority (id, authority, authorityOrder) VALUES (14, 'ROLE_EMPLOYEE', 2);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (0, 12);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (1, 13);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (2, 12);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (3, 12);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (4, 12);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (5, 12);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (6, 12);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (7, 14);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (8, 14);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (9, 14);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (10, 14);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (11, 14);

/* (COMPANY) ADDRESSES */
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (15, 0, 'Sun Alley', '31', '15489', 'Munich', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (16, 0, 'Berliner Straße', '125', '60139', 'Frankfurt', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (17, 0, 'Holzweg', '2', '10521', 'Berlin', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (18, 0, 'Temple Road', '67', '40931', 'Düsseldorf', 'Deutschland');

/* COMPANIES */
INSERT INTO company (id, version, companyId, name, address_id) VALUES (19, 0, 1000, 'webshop Ltd.', 15);
INSERT INTO company (id, version, companyId, name, address_id) VALUES (20, 0, 1001, 'finance Meier & partners', 16);
INSERT INTO company (id, version, companyId, name, address_id) VALUES (21, 0, 1002, 'Origins', 17);
INSERT INTO company (id, version, companyId, name, address_id) VALUES (22, 0, 1003, 'scalar deployment GmbH', 18);

/* CONTACT PERSONS */
INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company, roles) VALUES (23, 0, 'Robert', 'Lake', 'r.lake@webshop.de', 'Mr', '0178/11234566', 19, 'Sales Manager');
INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company, roles) VALUES (24, 0, 'Lori', 'Carter', 'l.carter@webshop.de', 'Mrs', '0178/68203493', 19, 'Project Managerin, CIO');
INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company) VALUES (25, 0, 'Loyd', 'Boyden', 'loyd.boyden@fi-meyer.com', 'Mr', '0152 12039411', 20);
INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company) VALUES (26, 0, 'Jimmy', 'Green', 'jim.green@origins.de', 'Mr', '0176/60012331', 21);
INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company) VALUES (27, 0, 'Nichole', 'Morgan', 'n.morgan@scalard.net', 'Mrs', '0143/99682738', 22);

/* PROJECTS */
INSERT INTO project (id, version, identifier, name, company_id, volume, fixedPrice, debitor_id) VALUES (28, 0, '1000.2014.1', 'Webshop - Checkout Development', 19, 60, 15000, 20);
INSERT INTO project (id, version, identifier, name, company_id, volume, hourlyRate) VALUES (29, 0, '1000.2013.1', 'Process automization', 19, 30, 80);
INSERT INTO project (id, version, identifier, name, company_id, volume, dailyRate) VALUES (30, 0, '1002.2013.1', 'IaaS Java Development', 21, 100, 670);
INSERT INTO project (id, version, identifier, name, company_id, volume, hourlyRate) VALUES (31, 0, '1002.2013.2', 'Test Manager', 21, 50, 85);
INSERT INTO project (id, version, identifier, name, company_id, volume, hourlyRate) VALUES (32, 0, '1002.2014.1', 'Frontend Development', 21, 30, 70);
INSERT INTO project (id, version, identifier, name, company_id, volume, hourlyRate, debitor_id) VALUES (33, 0, '1003.2014.1', 'ESB Architecture', 22, 90, 80, 20);

/* TRAVEL EXPENSE REPORTS */
INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (34, 0, 1, 'PENDING');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (35, 0, 1, 'SUBMITTED', '2014-06-01 19:41:31');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (36, 0, 1, 'APPROVED', '2014-04-01 20:01:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (37, 0, 1, 'REJECTED', '2014-05-01 17:45:21');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (38, 0, 2, 'PENDING');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (39, 0, 2, 'SUBMITTED', '2014-06-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (40, 0, 2, 'APPROVED', '2014-04-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (41, 0, 2, 'REJECTED', '2014-05-01 13:30:21');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (42, 0, 3, 'PENDING');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (43, 0, 3, 'SUBMITTED', '2014-06-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (44, 0, 3, 'APPROVED', '2014-04-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (45, 0, 3, 'REJECTED', '2014-05-01 13:30:21');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (46, 0, 4, 'PENDING');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (47, 0, 4, 'SUBMITTED', '2014-06-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (48, 0, 4, 'APPROVED', '2014-04-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (49, 0, 4, 'REJECTED', '2014-05-01 13:30:21');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (50, 0, 5, 'PENDING');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (51, 0, 5, 'SUBMITTED', '2014-06-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (52, 0, 5, 'APPROVED', '2014-04-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (53, 0, 5, 'REJECTED', '2014-05-01 13:30:21');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (54, 0, 6, 'PENDING');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (55, 0, 6, 'SUBMITTED', '2014-06-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (56, 0, 6, 'APPROVED', '2014-04-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (57, 0, 6, 'REJECTED', '2014-05-01 13:30:21');

INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (58, 0, 7, 'APPROVED', '2013-11-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (59, 0, 7, 'APPROVED', '2013-12-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (60, 0, 7, 'APPROVED', '2014-01-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (61, 0, 7, 'APPROVED', '2014-02-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (62, 0, 11, 'APPROVED', '2014-05-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (63, 0, 11, 'APPROVED', '2014-06-01 13:30:21');
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate) VALUES (64, 0, 11, 'APPROVED', '2014-07-01 13:30:21');


/* ************************************* FROM HERE ON ENTITIES DON'T NEED A SET ID BECAUSE THEY ARE NOT REFERENCED ******************************************** */

/* WORKTIMES project 1000.2014.1 EMPLOYEE 1 */
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-02', '09:00:00', '17:15:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-03', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-04', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-05', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-06', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-09', '09:00:00', '17:15:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-10', '09:00:00', '17:00:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-11', '08:30:00', '16:45:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-12', '10:30:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-13', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-16', '09:00:00', '17:15:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-17', '08:30:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-18', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-19', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-20', '08:00:00', '16:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-24', '10:00:00', '17:00:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-25', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-26', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-27', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-28', '11:00:00', '18:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 1, '2014-06-30', '12:00:00', '15:00:00');

/* WORKTIMES project 1000.2014.1 EMPLOYEE 2 */
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-02', '08:30:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-03', '09:45:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-04', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-05', '10:15:00', '18:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-06', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-09', '09:00:00', '17:15:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-10', '09:15:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-11', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-12', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-13', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-16', '09:00:00', '17:15:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-17', '08:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-18', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-19', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-20', '09:30:00', '16:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-24', '09:00:00', '17:15:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-25', '08:45:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-26', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-27', '09:45:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-28', '09:00:00', '17:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 28, 2, '2014-06-30', '12:00:00', '15:00:00');

/* WORKTIMES project 1002.2014.1 EMPLOYEE 3 */
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-02', '12:00:00', '18:00:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-03', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-04', '09:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-05', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-06', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-09', '12:00:00', '18:00:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-10', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-11', '07:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-12', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-13', '09:00:00', '16:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-16', '13:00:00', '18:00:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-17', '08:00:00', '17:45:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-18', '09:45:00', '18:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-19', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-20', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-24', '11:00:00', '18:15:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-25', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-26', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-27', '11:00:00', '19:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-28', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 3, '2014-06-30', '12:00:00', '17:00:00');

/* WORKTIMES project 1002.2014.1 EMPLOYEE 4 */
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-02', '11:00:00', '18:00:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-03', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-04', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-05', '10:15:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-06', '07:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-09', '12:00:00', '18:00:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-10', '08:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-11', '07:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-12', '09:00:00', '17:45:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-13', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-16', '13:00:00', '18:00:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-17', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-18', '09:00:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-19', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-20', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-24', '11:00:00', '18:15:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-25', '09:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-26', '08:30:00', '16:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-27', '10:00:00', '17:30:00');
INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-28', '09:00:00', '15:00:00');

INSERT INTO workTime (version, project, employee, date, startTime, endTime) VALUES (0, 32, 4, '2014-06-30', '12:00:00', '17:00:00');

/* HOLIDAYS */
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-25', '1. Weihnachtsfeiertag', 'BERLIN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-26', '2. Weihnachtsfeiertag', 'BERLIN');
INSERT INTO holiday (day, name, federalState) VALUES ('2014-01-01', 'Neujahr', 'BERLIN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-25', '1. Weihnachtsfeiertag', 'HESSEN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-26', '2. Weihnachtsfeiertag', 'HESSEN');
INSERT INTO holiday (day, name, federalState) VALUES ('2014-01-01', 'Neujahr', 'HESSEN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-25', '1. Weihnachtsfeiertag', 'BADEN_WUERTTEMBERG');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-26', '2. Weihnachtsfeiertag', 'BADEN_WUERTTEMBERG');
INSERT INTO holiday (day, name, federalState) VALUES ('2014-01-01', 'Neujahr', 'BADEN_WUERTTEMBERG');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-25', '1. Weihnachtsfeiertag', 'BAYERN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-26', '2. Weihnachtsfeiertag', 'BAYERN');
INSERT INTO holiday (day, name, federalState) VALUES ('2014-01-01', 'Neujahr', 'BAYERN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-25', '1. Weihnachtsfeiertag', 'MECKLENBURG_VORPOMMERN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-26', '2. Weihnachtsfeiertag', 'MECKLENBURG_VORPOMMERN');
INSERT INTO holiday (day, name, federalState) VALUES ('2014-01-01', 'Neujahr', 'MECKLENBURG_VORPOMMERN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-25', '1. Weihnachtsfeiertag', 'SCHLESWIG_HOLSTEIN');
INSERT INTO holiday (day, name, federalState) VALUES ('2013-12-26', '2. Weihnachtsfeiertag', 'SCHLESWIG_HOLSTEIN');
INSERT INTO holiday (day, name, federalState) VALUES ('2014-01-01', 'Neujahr', 'SCHLESWIG_HOLSTEIN');


/* VACATION REQUESTS */
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 1, '2013-12-18', '2014-01-03', 10, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 1, '2014-07-10', '2014-07-18', 7, 'APPROVED', '2014-06-01 11:00:00', 2, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 1, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 5, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 2, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 2, '2014-07-14', '2014-07-17', 4, 'APPROVED', '2014-06-01 11:00:00', 3, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 2, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 4, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 3, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 3, '2014-07-10', '2014-07-18', 8, 'APPROVED', '2014-06-01 11:00:00', 1, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 3, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 6, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 4, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 4, '2014-07-14', '2014-07-18', 5, 'APPROVED', '2014-06-01 11:00:00', 2, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 4, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 5, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 5, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 5, '2014-07-14', '2014-07-15', 2, 'APPROVED', '2014-06-01 11:00:00', 1, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 5, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 1, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 6, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 6, '2014-07-14', '2014-07-14', 1, 'APPROVED', '2014-06-01 11:00:00', 4, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 6, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 3, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 9, '2014-04-24', '2014-04-29', 5, 'APPROVED', '2014-01-03 11:00:00', '2014-01-10 04:00:00');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 9, '2014-05-05', '2014-05-05', 1, 'APPROVED', '2014-04-01 10:00:00', '2014-04-08 04:00:00');
INSERT INTO vacationRequest (version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (0, 9, '2014-06-17', '2014-06-24', 6, 'REJECTED', '2014-05-03 16:00:00', 1, '2014-05-03 16:10:01');


INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 34, '2014-07-01', '2014-07-01', 31.34, 19, '2014-04-16 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 34, '2014-07-01', '2014-07-04', 350, 19, '2014-07-16 10:00:30', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 35, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 35, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 36, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 36, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 37, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 37, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL');

INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 38, '2014-07-01', '2014-07-01', 15.16, 19, '2014-04-16 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 38, '2014-07-01', '2014-07-04', 250, 19, '2014-07-16 10:00:30', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 39, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 39, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 40, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 40, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 41, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 41, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL');

INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 42, '2014-07-01', '2014-07-01', 131.34, 19, '2014-04-16 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 42, '2014-07-01', '2014-07-04', 1350, 19, '2014-07-16 10:00:30', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 43, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 43, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 44, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 44, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 45, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 45, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL');

INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 46, '2014-07-01', '2014-07-01', 85.34, 19, '2014-04-16 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 46, '2014-07-01', '2014-07-04', 250, 19, '2014-07-16 10:00:30', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 47, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 47, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 48, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 48, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 49, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 49, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL');

INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 50, '2014-07-01', '2014-07-01', 16.34, 19, '2014-04-16 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 50, '2014-07-01', '2014-07-04', 370, 19, '2014-07-16 10:00:30', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 51, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 51, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 52, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 52, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 53, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 53, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL');

INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 54, '2014-07-01', '2014-07-01', 30.54, 19, '2014-04-16 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 54, '2014-07-01', '2014-07-04', 330, 19, '2014-07-16 10:00:30', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 55, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 55, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 56, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 56, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 57, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 57, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL');

/* TRAVEL EXPENSES */
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 58, '2013-11-01', '2013-11-30', 210.31, 19, '2013-12-03 07:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 58, '2013-11-01', '2013-11-30', 2000, 19, '2013-12-03 07:01:44', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 59, '2013-12-01', '2013-12-24', 190.45, 19, '2013-12-26 07:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 59, '2013-12-01', '2013-12-24', 2100, 19, '2013-12-26 07:01:44', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 60, '2014-01-04', '2014-01-31', 250.99, 19, '2014-02-01 12:40:51', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 60, '2014-01-04', '2014-01-31', 1900, 19, '2014-02-01 12:40:51', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 61, '2014-02-01', '2014-02-28', 150.56, 19, '2014-04-01 08:31:12', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 61, '2014-02-01', '2014-02-28', 1500, 19, '2014-04-01 08:33:12', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 62, '2014-05-01', '2014-05-31', 450, 19, '2014-06-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 62, '2014-05-01', '2014-05-31', 2030, 19, '2014-06-01 10:00:30', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 63, '2014-06-01', '2014-06-30', 390, 19, '2014-07-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 63, '2014-06-01', '2014-06-30', 3000, 19, '2014-07-01 10:00:30', 'HOTEL');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 64, '2014-07-01', '2014-07-31', 200, 19, '2014-06-01 10:00:30', 'TAXI');
INSERT INTO travelExpense (version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (0, 64, '2014-07-01', '2014-07-31', 1500, 19, '2014-06-01 10:00:30', 'HOTEL');

/* BILLABLE TIMES */
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-02', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-03', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-04', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-05', 450);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-06', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-09', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-10', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-11', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-12', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-13', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-16', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-17', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-18', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-19', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-20', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-24', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-25', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-26', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-27', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-28', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 1, 28, '2014-06-30', 480);

INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-02', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-03', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-04', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-05', 450);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-06', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-09', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-10', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-11', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-12', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-13', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-16', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-17', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-18', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-19', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-20', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-24', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-25', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-26', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-27', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-28', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 2, 28, '2014-06-30', 480);

INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-02', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-03', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-04', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-05', 450);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-06', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-09', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-10', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-11', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-12', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-13', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-16', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-17', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-18', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-19', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-20', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-24', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-25', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-26', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-27', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-28', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 3, 32, '2014-06-30', 480);

INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-02', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-03', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-04', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-05', 450);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-06', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-09', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-10', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-11', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-12', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-13', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-16', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-17', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-18', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-19', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-20', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-24', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-25', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-26', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-27', 480);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-28', 360);
INSERT INTO billableTime (version, employee, project, date, minutes) VALUES (0, 4, 32, '2014-06-30', 480);

INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1003.2014.1-2014.01-1', 22, '2014-01-04', 1500.00, 'PAID', '2014-02-01');
INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1003.2014.1-2014.01-2', 22, '2014-01-04', 2300.00, 'PAID', '2014-02-01');
INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1003.2014.1-2014.01-3', 22, '2014-01-04', 6000.00, 'PAID', '2014-02-01');
INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1003.2014.1-2014.02-1', 22, '2014-02-15', 3000.00, 'PAID', '2014-03-20');
INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1003.2014.1-2014.02-2', 22, '2014-02-15', 4020.00, 'PAID', '2014-03-20');

INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1000.2014.1-2014.01-1', 19, '2014-01-15', 12570.00, 'PAID', '2014-03-01');

INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1000.2014.1-2014.06-1', 20, '2014-06-03', 7400.00, 'PAID', '2014-07-01');
INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1000.2014.1-2014.06-2', 20, '2014-06-03', 7600.00, 'PAID', '2014-07-01');
INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1002.2014.1-2014.06-1', 21, '2014-06-01', 13000.00, 'PAID', '2014-06-15');
INSERT INTO invoice (version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, '1002.2014.1-2014.06-2', 21, '2014-06-01', 25100.00, 'PAID', '2014-07-15');

DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START WITH 10000;