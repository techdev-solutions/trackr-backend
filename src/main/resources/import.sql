INSERT INTO employee (id, firstName, lastName) VALUES (0, 'admin', 'admin');
INSERT INTO credentials (id, email, enabled) VALUES (0, 'admin', 1);
INSERT INTO authority (id, authority) VALUES (0, 'ROLE_ADMIN');
INSERT INTO authority (id, authority) VALUES (1, 'ROLE_STAFF');
INSERT INTO authority (id, authority) VALUES (2, 'ROLE_EMPLOYEE');
INSERT INTO credentials_authority (credentials_id, authorities_id) VALUES (0, 0);