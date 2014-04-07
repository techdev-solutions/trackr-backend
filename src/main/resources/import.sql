INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) VALUES (0, 0, 'admin', 'admin', '', 0, 0, 'BERLIN');
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
INSERT INTO authority (id, authority, authorityOrder) VALUES (0, 'ROLE_ADMIN', 0);
INSERT INTO authority (id, authority, authorityOrder) VALUES (1, 'ROLE_SUPERVISOR', 1);
INSERT INTO authority (id, authority, authorityOrder) VALUES (2, 'ROLE_EMPLOYEE', 2);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (0, 0);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (1, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (2, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (3, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (4, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (5, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (6, 1);

INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (0, 0, 'Bismarckstrasse', '47', '76133', 'Karlsruhe', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (1, 0, 'Zur Giesserei', '19a', '76123', 'Karlsruhe', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (2, 0, 'Friedrichstrasse', '123', '10521', 'Berlin', 'Deutschland');

INSERT INTO company (id, version, companyId, name, address_id) VALUES (0, 0, 1000, 'techdev Solutions UG', 0);
INSERT INTO company (id, version, companyId, name, address_id) VALUES (1, 0, 1001, 'cofinpro AG', 1);
INSERT INTO company (id, version, companyId, name, address_id) VALUES (2, 0, 5000, 'Hays', 2);

INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company) VALUES (0, 0, 'Alexander', 'Hanschke', 'alexander.hanschke@techdev.de', 'Herr', '0178/11234566', 0);
INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, company) VALUES (1, 0, 'Adrian', 'Krion', 'adrian.krion@techdev.de', 'Herr', '0178/234586923', 0);

INSERT INTO project (id, version, identifier, name, company_id, volume, fixedPrice, debitor_id) VALUES (0, 0, '1001.1', 'Freiberuflerverwaltung', 1, 142, 500000.01, 2);
INSERT INTO project (id, version, identifier, name, company_id, volume, fixedPrice) VALUES (1, 0, '5000.1', 'Zaun streichen', 2, 1, 3.14);

INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (0, 0, 0, 1, '2014-03-03', '09:00:00', '17:15:00', 'Kommentar 1');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (6, 0, 0, 2, '2014-03-03', '09:00:00', '17:00:00', 'Kommentar 123');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (7, 0, 0, 4, '2014-03-03', '09:00:00', '12:00:00', 'Kommentar 46');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (8, 0, 0, 4, '2014-03-04', '12:00:00', '15:00:00', 'Kommentar 789');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (1, 0, 0, 1, '2014-03-04', '09:00:00', '17:00:00', 'Kommentar 2');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (2, 0, 1, 1, '2014-03-05', '09:00:00', '12:00:00', 'Kommentar 3');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (3, 0, 0, 1, '2014-03-05', '13:00:00', '17:00:00', 'Kommentar 4');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (4, 0, 0, 1, '2014-03-06', '09:00:00', '17:00:00', 'Kommentar 5');
INSERT INTO workTime (id, version, project, employee, date, startTime, endTime, comment) VALUES (5, 0, 0, 1, '2014-03-07', '09:00:00', '17:00:00', 'Kommentar 6');

INSERT INTO holiday (id, day, name, federalState) VALUES (0, '2014-12-25', '1. Weihnachtsfeiertag', 'BERLIN');
INSERT INTO holiday (id, day, name, federalState) VALUES (1, '2014-12-26', '2. Weihnachtsfeiertag', 'BERLIN');

INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (0, 0, 2, '2014-03-01', '2014-03-08', 5, 'PENDING', '2014-01-01 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime) VALUES (3, 0, 2, '2014-12-10', '2014-12-24', 5, 'PENDING', '2014-01-03 11:00:00');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (1, 0, 2, '2014-03-09', '2014-03-16', 5, 'APPROVED', '2014-01-01 10:00:00', 1, '2014-03-25');
INSERT INTO vacationRequest (id, version, employee_id, startDate, endDate, numberOfDays, status, submissionTime, approver_id, approvalDate) VALUES (2, 0, 2, '2014-03-17', '2014-03-24', 5, 'REJECTED', '2014-01-01 16:00:00', 1, '2014-03-25');

INSERT INTO travelExpenseReport (id, version, employee_id, status) VALUES (0, 0, 1, 'PENDING');
INSERT INTO travelExpense (id, version, report_id, fromDate, toDate, cost, vat, submissionDate) VALUES (0, 0, 0, '2014-04-01', '2014-04-10', 130.49, 19, '2014-04-12 10:00:30');