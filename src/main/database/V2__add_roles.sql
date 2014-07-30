insert into employee (id, version, firstName, lastName, title, hourlyCostRate, salary, federalState) values (0, 0, 'admin', 'admin', '', 0, 0, 'BERLIN');
insert into credential (id, email, enabled, locale) values (0, 'admin@techdev.de', true, 'en');
insert into authority (id, authority, authorityOrder) values (0, 'ROLE_ADMIN', 0);
insert into authority (id, authority, authorityOrder) values (1, 'ROLE_SUPERVISOR', 1);
insert into authority (id, authority, authorityOrder) values (2, 'ROLE_EMPLOYEE', 2);
insert into credential_authority (credential_id, authorities_id) values (0, 0);

