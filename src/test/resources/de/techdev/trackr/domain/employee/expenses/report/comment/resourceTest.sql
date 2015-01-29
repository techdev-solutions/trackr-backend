-- data for the travel expense report comment resource test
INSERT INTO employee (id, federalstate, firstname, hourlycostrate, joindate, lastname, phonenumber, salary, title, vacationentitlement, version, email)
 VALUES (0, 'BERLIN', 'Firstname', 100, '2014-06-06', 'Lastname', 'Phonenumber', 40000, 'Title', 30, 0, 'employee@techdev.de');

INSERT INTO address (id, city, country, housenumber, street, version, zipcode)
  VALUES (0, 'City', 'Country', '13', 'Street', 0, 'zipcode');

INSERT INTO company (id, companyid, name, version, address_id, timeforpayment)
  VALUES (0, 1000, 'Company', 0, 0, 30);

INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id)
  VALUES (0, 0, 0, 'PENDING', '2015-01-25 09:30:21', 0);

INSERT INTO travelexpensereportcomment (id, submissiondate, text, employee_id, travelexpensereport_id)
  VALUES (0, '2015-01-25 13:01:31', 'Comment 1', 0, 0);