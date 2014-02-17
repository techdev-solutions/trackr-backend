INSERT INTO employee (id, version, firstName, lastName) VALUES (0, 0, 'admin', 'admin');
INSERT INTO credential (id, email, enabled) VALUES (0, 'admin', true);
INSERT INTO authority (id, authority) VALUES (0, 'ROLE_ADMIN');
INSERT INTO authority (id, authority) VALUES (1, 'ROLE_STAFF');
INSERT INTO authority (id, authority) VALUES (2, 'ROLE_EMPLOYEE');
INSERT INTO credential_authority (credential_id, authorities_id) VALUES (0, 0);