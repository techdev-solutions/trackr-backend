-- data for the company repository test.
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (0, 0, 'Sun Alley', '31', '15489', 'Munich', 'Deutschland');

INSERT INTO company (id, version, companyId, name, address_id, timeForPayment) VALUES (0, 0, 1000, 'webshop Ltd.', 0, 30);

INSERT INTO contactPerson (id, version, firstName, lastName, email, salutation, phone, roles, company)
  VALUES (0, 0, 'Robert', 'Lake', 'r.lake@webshop.de', 'Mr', '0178/11234566', 'Sales Manager', 0);

--- with project
INSERT INTO company (id, version, companyId, name, address_id, timeForPayment) VALUES (1, 0, 1001, 'webshop Ltd.', 0, 30);

INSERT INTO project (id, dailyrate, fixedprice, hourlyrate, identifier, name, version, volume, company_id)
  VALUES (0, 30, 30, 30, '1001.1', 'Project', 0, 1000, 1);