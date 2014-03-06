INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary) VALUES (0, 0, 'admin', 'admin', '', 0, 0);
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary) VALUES (1, 0, 'Moritz', 'Schulze', 'Hausmeister', 0.25, 40);
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary) VALUES (2, 0, 'Viktor', 'Widiker', 'Software Consultant', 123, 456);
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary) VALUES (3, 0, 'Alexander', 'Hanschke', 'Praktikant', 321, 654.32);
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary) VALUES (4, 0, 'Adrian', 'Krion', 'Sekretär', 6854, 123455);
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary) VALUES (5, 0, 'Angelika', 'Gutjahr', 'Entertainerin', 900.1, 500000);
INSERT INTO employee (id, version, firstName, lastName, title, hourlyCostRate, salary) VALUES (6, 0, 'Nikolaj', 'Weise', 'Empfang', 100.5, 123.4);
INSERT INTO credential (id, email, enabled) VALUES (0, 'admin@techdev.de', true);
INSERT INTO credential (id, email, enabled) VALUES (1, 'moritz.schulze@techdev.de', true);
INSERT INTO credential (id, email, enabled) VALUES (2, 'viktor.widiker@techdev.de', true);
INSERT INTO credential (id, email, enabled) VALUES (3, 'alexander.hanschke@techdev.de', true);
INSERT INTO credential (id, email, enabled) VALUES (4, 'adrian.krion@techdev.de', true);
INSERT INTO credential (id, email, enabled) VALUES (5, 'angelika.gutjahr@techdev.de', true);
INSERT INTO credential (id, email, enabled) VALUES (6, 'nikolaj.weise@techdev.de', true);
INSERT INTO authority (id, authority, screenName, authorityOrder) VALUES (0, 'ROLE_ADMIN', 'Administrator', 0);
INSERT INTO authority (id, authority, screenName, authorityOrder) VALUES (1, 'ROLE_SUPERVISOR', 'Supervisor', 1);
INSERT INTO authority (id, authority, screenName, authorityOrder) VALUES (2, 'ROLE_EMPLOYEE', 'Angestellter', 2);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (0, 0);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (1, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (2, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (3, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (4, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (5, 1);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (6, 1);

INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (0, 0, 'Bismarckstraße', '47', '76133', 'Karlsruhe', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (1, 0, 'Zur Gießerei', '19a', '76123', 'Karlsruhe', 'Deutschland');
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (2, 0, 'Friedrichstraße', '123', '10521', 'Berlin', 'Deutschland');

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
