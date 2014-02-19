INSERT INTO employee (id, version, firstName, lastName) VALUES (0, 0, 'admin', 'admin');
INSERT INTO employee (id, version, firstName, lastName) VALUES (1, 0, 'Moritz', 'Schulze');
INSERT INTO credential (id, email, enabled) VALUES (0, 'admin', true);
INSERT INTO credential (id, email, enabled) VALUES (1, 'moritz.schulze2@techdev.de', true);
INSERT INTO authority (id, authority, screenName, authorityOrder) VALUES (0, 'ROLE_ADMIN', 'Administrator', 0);
INSERT INTO authority (id, authority, screenName, authorityOrder) VALUES (1, 'ROLE_SUPERVISOR', 'Supervisor', 1);
INSERT INTO authority (id, authority, screenName, authorityOrder) VALUES (2, 'ROLE_EMPLOYEE', 'Angestellter', 2);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (0, 0);
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (1, 2);

-- INSERT INTO company (id, version, companyId, name, street, houseNumber, zipCode, city, country) VALUES (0, 0, '00001.1', 'techdev Solutions UG', 'Bismarckstraße', '47', '76133', 'Karlsruhe', 'Deutschland');
