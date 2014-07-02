INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (0, 0, 'admin', 'admin', 'technical administrator', 0, 0, 'BERLIN');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, vacationEntitlement) VALUES (1, 0, 'Moritz', 'Schulze', 'Hausmeister', 0.25, 40, 'BERLIN', '2014-02-01', 30.5);
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (2, 0, 'Viktor', 'Widiker', 'Software Consultant', 123, 456, 'BERLIN');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (3, 0, 'Alexander', 'Hanschke', 'Praktikant', 321, 654.32, 'BADEN_WUERTTEMBERG');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (4, 0, 'Adrian', 'Krion', 'Sekretaer', 6854, 123455, 'HESSEN');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate) VALUES (5, 0, 'Angelika', 'Gutjahr', 'Entertainerin', 900.1, 500000, 'BADEN_WUERTTEMBERG', '1990-10-03');
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (6, 0, 'Nikolaj', 'Weise', 'Empfang', 100.5, 123.4, 'BERLIN');
INSERT INTO credential (id, email, enabled, locale) VALUES (0, 'admin@techdev.de', true, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (1, 'moritz.schulze@techdev.de', true, 'de');
INSERT INTO credential (id, email, enabled, locale) VALUES (2, 'viktor.widiker@techdev.de', true, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (3, 'alexander.hanschke@techdev.de', true, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (4, 'adrian.krion@techdev.de', true, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (5, 'angelika.gutjahr@techdev.de', true, 'en');
INSERT INTO credential (id, email, enabled, locale) VALUES (6, 'nikolaj.weise@techdev.de', true, 'en');
INSERT INTO authority (id, authority, authorityOrder) VALUES (7, 'ROLE_ADMIN', 0);
INSERT INTO authority (id, authority, authorityOrder) VALUES (8, 'ROLE_SUPERVISOR', 1);
INSERT INTO authority (id, authority, authorityOrder) VALUES (9, 'ROLE_EMPLOYEE', 2);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (0, 7);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (1, 8);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (2, 8);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (3, 8);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (4, 8);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (5, 8);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (6, 8);

INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (10, 0, 'Bismarckstrasse', '47', '76133', 'Karlsruhe', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (11, 0, 'Zur Giesserei', '19a', '76123', 'Karlsruhe', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (12, 0, 'Friedrichstrasse', '123', '10521', 'Berlin', 'Deutschland');

INSERT INTO company (id, version, companyId, name, address_id) VALUES (13, 0, 1000, 'techdev Solutions UG', 10);
INSERT INTO company (id, version, companyId, name, address_id) VALUES (14, 0, 1001, 'cofinpro AG', 11);
INSERT INTO company (id, version, companyId, name, address_id) VALUES (15, 0, 5000, 'Hays', 12);

INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company, roles) VALUES (16, 0, 'Alexander', 'Hanschke', 'alexander.hanschke@techdev.de', 'Herr', '0178/11234566', 13, 'Boss 1');
INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company) VALUES (17, 0, 'Adrian', 'Krion', 'adrian.krion@techdev.de', 'Herr', '0178/234586923', 13);

INSERT INTO project (id, version, identifier, name, company_id, volume, fixedPrice, debitor_id) VALUES (18, 0, '1001.1', 'Freiberuflerverwaltung', 13, 142, 500000.01, 14);
INSERT INTO project (id, version, identifier, name, company_id, volume, hourlyRate) VALUES (19, 0, '5000.1', 'Zaun streichen', 14, 1, 62.12);

INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (20, 0, 18, 1, '2014-07-03', '09:00:00', '17:15:00', 'Kommentar 1');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (21, 0, 18, 2, '2014-07-03', '09:00:00', '17:00:00', 'Kommentar 123');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (22, 0, 18, 4, '2014-07-03', '09:00:00', '12:00:00', 'Kommentar 46');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (23, 0, 18, 4, '2014-07-04', '12:00:00', '15:00:00', 'Kommentar 789');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (24, 0, 18, 1, '2014-07-04', '09:00:00', '17:00:00', 'Kommentar 2');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (25, 0, 19, 1, '2014-07-05', '09:00:00', '12:00:00', 'Kommentar 3');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (26, 0, 19, 1, '2014-07-05', '13:00:00', '17:00:00', 'Kommentar 4');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (27, 0, 19, 1, '2014-07-06', '09:00:00', '17:00:00', 'Kommentar 5');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (28, 0, 18, 1, '2014-07-07', '09:00:00', '17:00:00', 'Kommentar 6');

INSERT INTO holiday (id, day, name, federalState) VALUES (29, '2014-12-25', '1. Weihnachtsfeiertag', 'BERLIN');
INSERT INTO holiday (id, day, name, federalState) VALUES (30, '2014-12-26', '2. Weihnachtsfeiertag', 'BERLIN');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (31, 0, 2, '2014-03-01', '2014-03-08', 5, 'PENDING', '2014-01-01 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (32, 0, 2, '2014-12-10', '2014-12-24', 5, 'PENDING', '2014-01-03 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approvalDate) VALUES (33, 0, 0, '2014-03-09', '2014-03-16', 5, 'APPROVED', '2014-01-01 10:00:00', '2014-03-25 12:30:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (34, 0, 0, '2014-03-17', '2014-03-24', 5, 'REJECTED', '2014-01-01 16:00:00', 1, '2014-03-25 17:00:01');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (35, 0, 0, 'PENDING');
INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (36, 0, 0, 'APPROVED');
INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (37, 0, 0, 'REJECTED');
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (38, 0, 35, '2014-04-01', '2014-04-10', 130.49, 19, '2014-04-12 10:00:30', 'TAXI');
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (39, 0, 36, '2014-04-01', '2014-04-10', 100000, 19, '2014-04-12 10:00:30', 'HOTEL');
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (40, 0, 37, '2014-04-01', '2014-04-10', 100000000, 25, '2014-04-12 10:00:30', 'HOTEL');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (41, 0, 1, 'PENDING');
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (42, 0, 41, '2014-04-01', '2014-04-10', 130.49, 19, '2014-04-12 10:00:30', 'TAXI');
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (43, 0, 41, '2014-04-05', '2014-04-07', 200.13, 18, '2014-04-12 10:00:30', 'HOTEL');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (44, 0, 1, 'SUBMITTED');
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (45, 0, 44, '2014-04-01', '2014-04-10', 130.49, 19, '2014-04-12 10:00:30', 'TAXI');
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate, type) VALUES (46, 0, 44, '2014-04-05', '2014-04-07', 200.13, 18, '2014-04-12 10:00:30', 'HOTEL');

INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (47, 0, 'C201406-ZIM1', 13, '2014-07-03', 1234.00, 'OUTSTANDING', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (48, 0, 'C201406-ZIM2', 13, '2014-07-01', 1007.34, 'OUTSTANDING', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (49, 0, 'C201406-DBCE1', 14, '2014-07-24', 2000.38, 'OUTSTANDING', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (50, 0, 'C201405-ZIM3', 14, '2014-07-04', 500.61, 'OVERDUE', '2014-06-10');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (51, 0, 'C201406-DBCE2', 15, '2014-07-03', 100.20, 'OVERDUE', '2014-06-10');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (52, 0, 'C201405-COF3', 15, '2014-07-01', 381.78, 'OVERDUE', '2014-06-10');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (53, 0, 'C201405-COF4', 15, '2014-07-01', 789.65, 'OVERDUE', '2014-06-10');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (54, 0, 'C201405-COF5', 15, '2014-07-01', 777.77, 'OUTSTANDING', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (55, 0, 'C201405-COF6', 15, '2014-07-01', 666.66, 'PAID', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (56, 0, 'C201405-COF2', 14, '2014-07-01', 98.01, 'PAID', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (57, 0, 'C201405-COF7', 15, '2014-07-05', 0.01, 'OUTSTANDING', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (58, 0, 'C201405-COF1', 13, '2014-07-01', 768.13, 'OUTSTANDING', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (59, 0, 'C201406-DBCE3', 13, '2014-07-01', 1540.00, 'PAID', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (60, 0, 'C201405-AUD1', 13, '2014-07-10', 4321.32, 'PAID', '2014-06-29');
INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (61, 0, 'C201405-AUD2', 13, '21314-07-01', 300.00, 'PAID', '2014-06-29');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (62, 0, 1, '2014-07-01', '2014-07-21', 15, 'APPROVED', '2014-01-01 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (63, 0, 2, '2014-07-05', '2014-07-08', 4, 'APPROVED', '2014-01-01 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (64, 0, 3, '2014-07-07', '2014-07-12', 5, 'APPROVED', '2014-01-01 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (65, 0, 4, '2014-07-13', '2014-07-20', 5, 'APPROVED', '2014-01-01 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (66, 0, 5, '2014-07-22', '2014-07-30', 6, 'APPROVED', '2014-01-01 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (67, 0, 6, '2014-07-01', '2014-07-31', 27, 'APPROVED', '2014-01-01 11:00:00');

INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (68, 0, 1, 18, '2014-07-01', 480);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (69, 0, 1, 18, '2014-07-02', 480);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (70, 0, 1, 18, '2014-07-03', 420);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (71, 0, 1, 18, '2014-07-04', 360);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (72, 0, 1, 19, '2014-07-01', 480);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (73, 0, 1, 19, '2014-07-08', 540);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (74, 0, 1, 19, '2014-07-09', 480);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (75, 0, 1, 19, '2014-07-10', 420);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (76, 0, 1, 19, '2014-07-12', 480);

INSERT INTO project (id, version, identifier, name, company_id, volume, hourlyRate) VALUES (77, 0, '5001.1', 'Cloud IaaS synergy promotion', 14, 100, 80.00);
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (78, 0, 77, 1, '2014-07-12', '09:00:00', '17:00:00', 'Kommentar 5');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (79, 0, 77, 1, '2014-07-13', '08:30:00', '18:00:00', 'Kommentar 5');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (80, 0, 77, 1, '2014-07-14', '09:00:00', '18:30:00', 'Kommentar 5');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (81, 0, 77, 1, '2014-07-15', '09:00:00', '17:00:00', 'Kommentar 5');

INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (81, 0, 1, 77, '2014-07-12', 420);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (82, 0, 1, 77, '2014-07-13', 460);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (83, 0, 1, 77, '2014-07-14', 430);
INSERT INTO billableTime (id, version , employee, project, date, minutes) VALUES (84, 0, 1, 77, '2014-07-15', 480);

DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START WITH 100;