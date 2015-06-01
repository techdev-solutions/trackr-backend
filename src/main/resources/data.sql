CREATE TABLE IF NOT EXISTS uuid_mapping (id int, uuid varchar);
/* EMPLOYEES */
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, vacationEntitlement) VALUES (1, 0, 'moritz.schulze@techdev.de', 'John', 'Johnson', 'Software Engineer', 80, 50000, 'BERLIN', '2014-02-01', 30);
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (2, 0, 'viktor.widiker@techdev.de', 'Adam', 'Smith', 'Software Consultant', 85, 55000, 'BERLIN', '2014-07-01');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (3, 0, 'alexander.hanschke@techdev.de', 'William', 'Hanson', 'CEO', 85, 60000, 'BADEN_WUERTTEMBERG', '2013-09-01');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (4, 0, 'adrian.krion@techdev.de','Bill', 'Rust', 'Processes Consultant', 87, 58000, 'HESSEN', '2014-01-01');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (5, 0, 'angelika.gutjahr@techdev.de', 'Jane', 'Dafoe', 'HR', 0, 500000, 'BADEN_WUERTTEMBERG', '2013-10-01');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (6, 0, 'nikolaj.weise@techdev.de', 'Horace', 'Nottingham', 'External Consultant', 90, 0, 'BERLIN');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, leaveDate) VALUES (7, 0, 'noop1@techdev.de', 'Vladimir', 'Wichowsko', 'Software Engineer', 80, 49000, 'BERLIN', '2013-11-01', '2014-02-15');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (8, 0, 'noop2@techdev.de', 'June', 'Hooper', 'Software Architect', 90, 65000, 'BAYERN', '2014-09-01');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (9, 0, 'noop3@techdev.de', 'Todd', 'Floyd', 'HR', 0, 45000, 'BADEN_WUERTTEMBERG', '2014-03-16');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, leaveDate) VALUES (10, 0, 'noop4@techdev.de', 'Joanne', 'Doughty', 'Practicant', 0, 25000, 'SCHLESWIG_HOLSTEIN', '2013-11-01', '2013-12-15');
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (11, 0, 'supervisor@techdev.de', 'Sean', 'Robinson', 'Software Engineer', 75, 51000, 'MECKLENBURG_VORPOMMERN', '2014-05-01');

INSERT INTO settings (id, type, value, employee_id) VALUES (0, 'LOCALE', 'en', 1);
INSERT INTO settings (id, type, value, employee_id) VALUES (1, 'LOCALE', 'de', 2);
INSERT INTO settings (id, type, value, employee_id) VALUES (2, 'LOCALE', 'de', 3);
INSERT INTO settings (id, type, value, employee_id) VALUES (3, 'LOCALE', 'en', 4);
INSERT INTO settings (id, type, value, employee_id) VALUES (4, 'LOCALE', 'en', 5);
INSERT INTO settings (id, type, value, employee_id) VALUES (5, 'LOCALE', 'en', 6);
INSERT INTO settings (id, type, value, employee_id) VALUES (6, 'LOCALE', 'en', 7);
INSERT INTO settings (id, type, value, employee_id) VALUES (7, 'LOCALE', 'en', 8);
INSERT INTO settings (id, type, value, employee_id) VALUES (8, 'LOCALE', 'en', 9);
INSERT INTO settings (id, type, value, employee_id) VALUES (9, 'LOCALE', 'en', 10);
INSERT INTO settings (id, type, value, employee_id) VALUES (10, 'LOCALE', 'en', 11);

/* (COMPANY) ADDRESSES */
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (15, 0, 'Sun Alley', '31', '15489', 'Munich', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (16, 0, 'Berliner Straße', '125', '60139', 'Frankfurt', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (17, 0, 'Holzweg', '2', '10521', 'Berlin', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (18, 0, 'Temple Road', '67', '40931', 'Düsseldorf', 'Deutschland');

/* COMPANIES */
INSERT INTO company (id, version, companyId, name, address_id, timeForPayment) VALUES (19, 0, 1000, 'webshop Ltd.', 15, 30);
INSERT INTO company (id, version, companyId, name, address_id) VALUES (20, 0, 1001, 'finance Meier & partners', 16);
INSERT INTO company (id, version, companyId, name, address_id, timeForPayment) VALUES (21, 0, 1002, 'Origins', 17, 14);
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
INSERT INTO travelExpenseReport (id, version, employee_id, status, debitor_id) VALUES (34, 0, 1, 'PENDING', 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (35, 0, 1, 'SUBMITTED', '2014-06-01 19:41:31', 20);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (36, 0, 1, 'APPROVED', '2014-04-01 20:01:21', '2014-04-04 15:40:00', 8, 21);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (37, 0, 1, 'REJECTED', '2014-05-01 17:45:21', 22);

INSERT INTO travelExpenseReport (id, version, employee_id, status, debitor_id) VALUES (38, 0, 2, 'PENDING', 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (39, 0, 2, 'SUBMITTED', '2014-06-01 13:30:21', 20);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (40, 0, 2, 'APPROVED', '2014-04-01 13:30:21', '2014-04-06 08:30:12', 3, 21);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (41, 0, 2, 'REJECTED', '2014-05-01 13:30:21', 22);

INSERT INTO travelExpenseReport (id, version, employee_id, status, debitor_id) VALUES (42, 0, 3, 'PENDING', 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (43, 0, 3, 'SUBMITTED', '2014-06-01 13:30:21', 20);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (44, 0, 3, 'APPROVED', '2014-04-01 13:30:21', '2014-04-01 18:20:53', 1, 21);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (45, 0, 3, 'REJECTED', '2014-05-01 13:30:21', 22);

INSERT INTO travelExpenseReport (id, version, employee_id, status, debitor_id) VALUES (46, 0, 4, 'PENDING', 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (47, 0, 4, 'SUBMITTED', '2014-06-01 13:30:21', 20);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (48, 0, 4, 'APPROVED', '2014-04-01 13:30:21', '2014-04-05 14:00:12', 8, 21);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (49, 0, 4, 'REJECTED', '2014-05-01 13:30:21', 22);

INSERT INTO travelExpenseReport (id, version, employee_id, status, debitor_id) VALUES (50, 0, 5, 'PENDING', 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (51, 0, 5, 'SUBMITTED', '2014-06-01 13:30:21', 20);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (52, 0, 5, 'APPROVED', '2014-04-01 13:30:21', '2014-04-02 10:30:12', 7, 21);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (53, 0, 5, 'REJECTED', '2014-05-01 13:30:21', 22);

INSERT INTO travelExpenseReport (id, version, employee_id, status, debitor_id) VALUES (54, 0, 6, 'PENDING', 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (55, 0, 6, 'SUBMITTED', '2014-06-01 13:30:21', 20);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (56, 0, 6, 'APPROVED', '2014-04-01 13:30:21', '2014-04-02 10:30:12', 5, 21);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id) VALUES (57, 0, 6, 'REJECTED', '2014-05-01 13:30:21', 22);

INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (58, 0, 7, 'APPROVED', '2013-11-01 13:30:21', '2013-11-02 10:30:12', 8, 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (59, 0, 7, 'APPROVED', '2013-12-01 13:30:21', '2013-12-04 10:30:12', 8, 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (60, 0, 7, 'APPROVED', '2014-01-01 13:30:21', '2014-01-07 10:30:12', 8, 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (61, 0, 7, 'APPROVED', '2014-02-01 13:30:21', '2014-02-10 10:30:12', 8, 19);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (62, 0, 11, 'APPROVED', '2014-05-01 13:30:21', '2014-05-02 10:30:12', 8, 20);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (63, 0, 11, 'APPROVED', '2014-06-01 13:30:21', '2014-06-02 10:30:12', 8, 20);
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, approvalDate, approver_id, debitor_id) VALUES (64, 0, 11, 'APPROVED', '2014-07-01 13:30:21', '2014-07-02 10:30:12', 8, 20);


/* WORKTIMES project 1000.2014.1 EMPLOYEE 1 */
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (1, 0, 28, 1, '2014-06-02', '09:00:00', '17:15:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (2, 0, 28, 1, '2014-06-03', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (3, 0, 28, 1, '2014-06-04', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (4, 0, 28, 1, '2014-06-05', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (5, 0, 28, 1, '2014-06-06', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (6, 0, 28, 1, '2014-06-09', '09:00:00', '17:15:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (7, 0, 28, 1, '2014-06-10', '09:00:00', '17:00:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (8, 0, 28, 1, '2014-06-11', '08:30:00', '16:45:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (9, 0, 28, 1, '2014-06-12', '10:30:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (10, 0, 28, 1, '2014-06-13', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (11, 0, 28, 1, '2014-06-16', '09:00:00', '17:15:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (12, 0, 28, 1, '2014-06-17', '08:30:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (13, 0, 28, 1, '2014-06-18', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (14, 0, 28, 1, '2014-06-19', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (15, 0, 28, 1, '2014-06-20', '08:00:00', '16:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (16, 0, 28, 1, '2014-06-24', '10:00:00', '17:00:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (17, 0, 28, 1, '2014-06-25', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (18, 0, 28, 1, '2014-06-26', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (19, 0, 28, 1, '2014-06-27', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (20, 0, 28, 1, '2014-06-28', '11:00:00', '18:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (21, 0, 28, 1, '2014-06-30', '12:00:00', '15:00:00');

/* WORKTIMES project 1000.2014.1 EMPLOYEE 2 */
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (22, 0, 28, 2, '2014-06-02', '08:30:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (23, 0, 28, 2, '2014-06-03', '09:45:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (24, 0, 28, 2, '2014-06-04', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (25, 0, 28, 2, '2014-06-05', '10:15:00', '18:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (26, 0, 28, 2, '2014-06-06', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (27, 0, 28, 2, '2014-06-09', '09:00:00', '17:15:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (28, 0, 28, 2, '2014-06-10', '09:15:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (29, 0, 28, 2, '2014-06-11', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (30, 0, 28, 2, '2014-06-12', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (31, 0, 28, 2, '2014-06-13', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (32, 0, 28, 2, '2014-06-16', '09:00:00', '17:15:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (33, 0, 28, 2, '2014-06-17', '08:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (34, 0, 28, 2, '2014-06-18', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (35, 0, 28, 2, '2014-06-19', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (36, 0, 28, 2, '2014-06-20', '09:30:00', '16:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (37, 0, 28, 2, '2014-06-24', '09:00:00', '17:15:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (38, 0, 28, 2, '2014-06-25', '08:45:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (39, 0, 28, 2, '2014-06-26', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (40, 0, 28, 2, '2014-06-27', '09:45:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (41, 0, 28, 2, '2014-06-28', '09:00:00', '17:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (42, 0, 28, 2, '2014-06-30', '12:00:00', '15:00:00');

/* WORKTIMES project 1002.2014.1 EMPLOYEE 3 */
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (43, 0, 32, 3, '2014-06-02', '12:00:00', '18:00:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (44, 0, 32, 3, '2014-06-03', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (45, 0, 32, 3, '2014-06-04', '09:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (46, 0, 32, 3, '2014-06-05', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (47, 0, 32, 3, '2014-06-06', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (48, 0, 32, 3, '2014-06-09', '12:00:00', '18:00:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (49, 0, 32, 3, '2014-06-10', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (50, 0, 32, 3, '2014-06-11', '07:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (51, 0, 32, 3, '2014-06-12', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (52, 0, 32, 3, '2014-06-13', '09:00:00', '16:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (53, 0, 32, 3, '2014-06-16', '13:00:00', '18:00:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (54, 0, 32, 3, '2014-06-17', '08:00:00', '17:45:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (55, 0, 32, 3, '2014-06-18', '09:45:00', '18:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (56, 0, 32, 3, '2014-06-19', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (57, 0, 32, 3, '2014-06-20', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (58, 0, 32, 3, '2014-06-24', '11:00:00', '18:15:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (59, 0, 32, 3, '2014-06-25', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (60, 0, 32, 3, '2014-06-26', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (61, 0, 32, 3, '2014-06-27', '11:00:00', '19:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (62, 0, 32, 3, '2014-06-28', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (63, 0, 32, 3, '2014-06-30', '12:00:00', '17:00:00');

/* WORKTIMES project 1002.2014.1 EMPLOYEE 4 */
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (64, 0, 32, 4, '2014-06-02', '11:00:00', '18:00:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (65, 0, 32, 4, '2014-06-03', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (66, 0, 32, 4, '2014-06-04', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (67, 0, 32, 4, '2014-06-05', '10:15:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (68, 0, 32, 4, '2014-06-06', '07:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (69, 0, 32, 4, '2014-06-09', '12:00:00', '18:00:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (70, 0, 32, 4, '2014-06-10', '08:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (71, 0, 32, 4, '2014-06-11', '07:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (72, 0, 32, 4, '2014-06-12', '09:00:00', '17:45:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (73, 0, 32, 4, '2014-06-13', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (74, 0, 32, 4, '2014-06-16', '13:00:00', '18:00:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (75, 0, 32, 4, '2014-06-17', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (76, 0, 32, 4, '2014-06-18', '09:00:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (77, 0, 32, 4, '2014-06-19', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (78, 0, 32, 4, '2014-06-20', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (79, 0, 32, 4, '2014-06-24', '11:00:00', '18:15:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (80, 0, 32, 4, '2014-06-25', '09:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (81, 0, 32, 4, '2014-06-26', '08:30:00', '16:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (82, 0, 32, 4, '2014-06-27', '10:00:00', '17:30:00');
INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (83, 0, 32, 4, '2014-06-28', '09:00:00', '15:00:00');

INSERT INTO worktime (id, version, project, employee, date, startTime, endTime) VALUES (84, 0, 32, 4, '2014-06-30', '12:00:00', '17:00:00');

/* VACATION REQUESTS */
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (0, 0, 1, '2013-12-18', '2014-01-03', 10, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (1, 0, 1, '2014-07-10', '2014-07-18', 7, 'APPROVED', '2014-06-01 11:00:00', 2, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (2, 0, 1, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 5, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (3, 0, 2, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (4, 0, 2, '2014-07-14', '2014-07-17', 4, 'APPROVED', '2014-06-01 11:00:00', 3, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (5, 0, 2, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 4, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (6, 0, 3, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (7, 0, 3, '2014-07-10', '2014-07-18', 8, 'APPROVED', '2014-06-01 11:00:00', 1, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (8, 0, 3, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 6, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (9, 0, 4, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (10, 0, 4, '2014-07-14', '2014-07-18', 5, 'APPROVED', '2014-06-01 11:00:00', 2, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (11, 0, 4, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 5, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (12, 0, 5, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (13, 0, 5, '2014-07-14', '2014-07-15', 2, 'APPROVED', '2014-06-01 11:00:00', 1, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (14, 0, 5, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 1, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (15, 0, 6, '2013-12-19', '2014-01-03', 9, 'APPROVED', '2013-12-01 11:00:00', '2013-12-02 10:30:13');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (16, 0, 6, '2014-07-14', '2014-07-14', 1, 'APPROVED', '2014-06-01 11:00:00', 4, '2014-06-03 10:00:03');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (17, 0, 6, '2014-04-07', '2014-04-11', 5, 'REJECTED', '2014-03-15 11:00:00', 3, '2014-03-19 13:05:06');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (18, 0, 9, '2014-04-24', '2014-04-29', 5, 'APPROVED', '2014-01-03 11:00:00', '2014-01-10 04:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (19, 0, 9, '2014-05-05', '2014-05-05', 1, 'APPROVED', '2014-04-01 10:00:00', '2014-04-08 04:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (20, 0, 9, '2014-06-17', '2014-06-24', 6, 'REJECTED', '2014-05-03 16:00:00', 1, '2014-05-03 16:10:01');


INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (0, 0, 34, '2014-07-01', '2014-07-01', 31.34, 19, '2014-04-16 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (1, 0, 34, '2014-07-01', '2014-07-04', 350, 19, '2014-07-16 10:00:30', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (2, 0, 35, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (3, 0, 35, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (4, 0, 36, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (5, 0, 36, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (6, 0, 37, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (7, 0, 37, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL', false);

INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (8, 0, 38, '2014-07-01', '2014-07-01', 15.16, 19, '2014-04-16 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (9, 0, 38, '2014-07-01', '2014-07-04', 250, 19, '2014-07-16 10:00:30', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (10, 0, 39, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (11, 0, 39, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (12, 0, 40, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (13, 0, 40, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (14, 0, 41, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (15, 0, 41, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL', false);

INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (16, 0, 42, '2014-07-01', '2014-07-01', 131.34, 19, '2014-04-16 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (17, 0, 42, '2014-07-01', '2014-07-04', 1350, 19, '2014-07-16 10:00:30', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (18, 0, 43, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (19, 0, 43, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (20, 0, 44, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (21, 0, 44, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (22, 0, 45, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (23, 0, 45, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL', false);

INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (24, 0, 46, '2014-07-01', '2014-07-01', 85.34, 19, '2014-04-16 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (25, 0, 46, '2014-07-01', '2014-07-04', 250, 19, '2014-07-16 10:00:30', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (26, 0, 47, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (27, 0, 47, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (28, 0, 48, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (29, 0, 48, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (30, 0, 49, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (31, 0, 49, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL', false);
 
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (32, 0, 50, '2014-07-01', '2014-07-01', 16.34, 19, '2014-04-16 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (33, 0, 50, '2014-07-01', '2014-07-04', 370, 19, '2014-07-16 10:00:30', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (34, 0, 51, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (35, 0, 51, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (36, 0, 52, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (37, 0, 52, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (38, 0, 53, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (39, 0, 53, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL', false);
 
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (40, 0, 54, '2014-07-01', '2014-07-01', 30.54, 19, '2014-04-16 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (41, 0, 54, '2014-07-01', '2014-07-04', 330, 19, '2014-07-16 10:00:30', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (42, 0, 55, '2014-06-02', '2014-06-02', 32.40, 19, '2014-07-01 12:40:51', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (43, 0, 55, '2014-06-02', '2014-06-05', 350, 19, '2014-07-01 12:40:51', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (44, 0, 56, '2014-04-01', '2014-04-10', 30.33, 19, '2014-05-01 08:31:12', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (45, 0, 56, '2014-04-01', '2014-04-10', 340, 19, '2014-05-01 08:33:12', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (46, 0, 57, '2014-05-01', '2014-05-10', 330.13, 19, '2014-06-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (47, 0, 57, '2014-05-01', '2014-05-10', 1000, 19, '2014-06-01 10:00:30', 'HOTEL', false);
 
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (48, 0, 58, '2013-11-01', '2013-11-30', 210.31, 19, '2013-12-03 07:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (49, 0, 58, '2013-11-01', '2013-11-30', 2000, 19, '2013-12-03 07:01:44', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (50, 0, 59, '2013-12-01', '2013-12-24', 190.45, 19, '2013-12-26 07:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (51, 0, 59, '2013-12-01', '2013-12-24', 2100, 19, '2013-12-26 07:01:44', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (52, 0, 60, '2014-01-04', '2014-01-31', 250.99, 19, '2014-02-01 12:40:51', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (53, 0, 60, '2014-01-04', '2014-01-31', 1900, 19, '2014-02-01 12:40:51', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (54, 0, 61, '2014-02-01', '2014-02-28', 150.56, 19, '2014-04-01 08:31:12', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (55, 0, 61, '2014-02-01', '2014-02-28', 1500, 19, '2014-04-01 08:33:12', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (56, 0, 62, '2014-05-01', '2014-05-31', 450, 19, '2014-06-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (57, 0, 62, '2014-05-01', '2014-05-31', 2030, 19, '2014-06-01 10:00:30', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (58, 0, 63, '2014-06-01', '2014-06-30', 390, 19, '2014-07-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (59, 0, 63, '2014-06-01', '2014-06-30', 3000, 19, '2014-07-01 10:00:30', 'HOTEL', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (60, 0, 64, '2014-07-01', '2014-07-31', 200, 19, '2014-06-01 10:00:30', 'TAXI', false);
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type, paid) VALUES (61, 0, 64, '2014-07-01', '2014-07-31', 1500, 19, '2014-06-01 10:00:30', 'HOTEL', false);

/* BILLABLE TIMES */
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (0, 0, 1, 28, '2014-06-02', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (1, 0, 1, 28, '2014-06-03', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (2, 0, 1, 28, '2014-06-04', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (3, 0, 1, 28, '2014-06-05', 450);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (4, 0, 1, 28, '2014-06-06', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (5, 0, 1, 28, '2014-06-09', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (6, 0, 1, 28, '2014-06-10', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (7, 0, 1, 28, '2014-06-11', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (8, 0, 1, 28, '2014-06-12', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (9, 0, 1, 28, '2014-06-13', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (10, 0, 1, 28, '2014-06-16', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (11, 0, 1, 28, '2014-06-17', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (12, 0, 1, 28, '2014-06-18', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (13, 0, 1, 28, '2014-06-19', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (14, 0, 1, 28, '2014-06-20', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (15, 0, 1, 28, '2014-06-24', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (16, 0, 1, 28, '2014-06-25', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (17, 0, 1, 28, '2014-06-26', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (18, 0, 1, 28, '2014-06-27', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (19, 0, 1, 28, '2014-06-28', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (20, 0, 1, 28, '2014-06-30', 480);
 
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (21, 0, 2, 28, '2014-06-02', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (22, 0, 2, 28, '2014-06-03', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (23, 0, 2, 28, '2014-06-04', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (24, 0, 2, 28, '2014-06-05', 450);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (25, 0, 2, 28, '2014-06-06', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (26, 0, 2, 28, '2014-06-09', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (27, 0, 2, 28, '2014-06-10', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (28, 0, 2, 28, '2014-06-11', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (29, 0, 2, 28, '2014-06-12', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (30, 0, 2, 28, '2014-06-13', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (31, 0, 2, 28, '2014-06-16', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (32, 0, 2, 28, '2014-06-17', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (33, 0, 2, 28, '2014-06-18', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (34, 0, 2, 28, '2014-06-19', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (35, 0, 2, 28, '2014-06-20', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (36, 0, 2, 28, '2014-06-24', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (37, 0, 2, 28, '2014-06-25', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (38, 0, 2, 28, '2014-06-26', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (39, 0, 2, 28, '2014-06-27', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (40, 0, 2, 28, '2014-06-28', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (41, 0, 2, 28, '2014-06-30', 480);
 
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (42, 0, 3, 32, '2014-06-02', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (43, 0, 3, 32, '2014-06-03', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (44, 0, 3, 32, '2014-06-04', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (45, 0, 3, 32, '2014-06-05', 450);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (46, 0, 3, 32, '2014-06-06', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (47, 0, 3, 32, '2014-06-09', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (48, 0, 3, 32, '2014-06-10', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (49, 0, 3, 32, '2014-06-11', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (50, 0, 3, 32, '2014-06-12', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (51, 0, 3, 32, '2014-06-13', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (52, 0, 3, 32, '2014-06-16', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (53, 0, 3, 32, '2014-06-17', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (54, 0, 3, 32, '2014-06-18', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (55, 0, 3, 32, '2014-06-19', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (56, 0, 3, 32, '2014-06-20', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (57, 0, 3, 32, '2014-06-24', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (58, 0, 3, 32, '2014-06-25', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (59, 0, 3, 32, '2014-06-26', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (60, 0, 3, 32, '2014-06-27', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (61, 0, 3, 32, '2014-06-28', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (62, 0, 3, 32, '2014-06-30', 480);
 
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (63, 0, 4, 32, '2014-06-02', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (64, 0, 4, 32, '2014-06-03', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (65, 0, 4, 32, '2014-06-04', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (66, 0, 4, 32, '2014-06-05', 450);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (67, 0, 4, 32, '2014-06-06', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (68, 0, 4, 32, '2014-06-09', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (69, 0, 4, 32, '2014-06-10', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (70, 0, 4, 32, '2014-06-11', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (71, 0, 4, 32, '2014-06-12', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (72, 0, 4, 32, '2014-06-13', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (73, 0, 4, 32, '2014-06-16', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (74, 0, 4, 32, '2014-06-17', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (75, 0, 4, 32, '2014-06-18', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (76, 0, 4, 32, '2014-06-19', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (77, 0, 4, 32, '2014-06-20', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (78, 0, 4, 32, '2014-06-24', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (79, 0, 4, 32, '2014-06-25', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (80, 0, 4, 32, '2014-06-26', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (81, 0, 4, 32, '2014-06-27', 480);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (82, 0, 4, 32, '2014-06-28', 360);
INSERT INTO billableTime (id, version, employee, project, date, minutes) VALUES (83, 0, 4, 32, '2014-06-30', 480);

INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, 0, '1003.2014.1-2014.01-1', 22, '2014-01-04', 1500.00, 'PAID', '2014-02-01');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (1, 0, '1003.2014.1-2014.01-2', 22, '2014-01-04', 2300.00, 'PAID', '2014-02-01');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (2, 0, '1003.2014.1-2014.01-3', 22, '2014-01-04', 6000.00, 'PAID', '2014-02-01');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (3, 0, '1003.2014.1-2014.02-1', 22, '2014-02-15', 3000.00, 'PAID', '2014-03-20');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (4, 0, '1003.2014.1-2014.02-2', 22, '2014-02-15', 4020.00, 'PAID', '2014-03-20');

INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (5, 0, '1000.2014.1-2014.01-1', 19, '2014-01-15', 12570.00, 'PAID', '2014-03-01');

INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (6, 0, '1000.2014.1-2014.06-1', 20, '2014-06-03', 7400.00, 'PAID', '2014-07-01');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (7, 0, '1000.2014.1-2014.06-2', 20, '2014-06-03', 7600.00, 'PAID', '2014-07-01');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (8, 0, '1002.2014.1-2014.06-1', 21, '2014-06-01', 13000.00, 'PAID', '2014-06-15');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (9, 0, '1002.2014.1-2014.06-2', 21, '2014-06-01', 25100.00, 'PAID', '2014-07-15');
