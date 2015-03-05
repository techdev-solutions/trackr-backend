-- data for the travel expense report comment resource test
INSERT INTO employee (id, federalstate, firstname, hourlycostrate, joindate, lastname, phonenumber, salary, title, vacationentitlement, version, email)
 VALUES (0, 'BERLIN', 'Firstname', 100, '2014-06-06', 'Lastname', 'Phonenumber', 40000, 'Title', 30, 0, 'employee@techdev.de');

INSERT INTO address (id, city, country, housenumber, street, version, zipcode)
  VALUES (0, 'City', 'Country', '13', 'Street', 0, 'zipcode');

INSERT INTO company (id, companyid, name, version, address_id, timeforpayment)
  VALUES (0, 1000, 'Company', 0, 0, 30);

INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id)
  VALUES (0, 0, 0, 'PENDING', '2015-01-25 09:30:21', 0);

INSERT INTO travelexpense (id, cost, fromdate, submissiondate, todate, type, vat, version, report_id, comment)
  VALUES (0, 100, '2015-01-01', '2015-02-01 13:41:13', '2015-02-13', 'TAXI', 13, 0, 0, 'Comment');

-- accepted report for various tests
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id)
  VALUES (1, 0, 0, 'APPROVED', '2015-01-25 09:30:21', 0);

INSERT INTO travelexpense (id, cost, fromdate, submissiondate, todate, type, vat, version, report_id, comment)
  VALUES (1, 100, '2015-01-01', '2015-02-01 13:41:13', '2015-02-13', 'TAXI', 13, 0, 1, 'Comment');

-- submitted report for various tests
INSERT INTO travelExpenseReport (id, version, employee_id, status, submissionDate, debitor_id)
  VALUES (2, 0, 0, 'SUBMITTED', '2015-01-25 09:30:21', 0);

INSERT INTO travelexpense (id, cost, fromdate, submissiondate, todate, type, vat, version, report_id, comment)
  VALUES (2, 100, '2015-01-01', '2015-02-01 13:41:13', '2015-02-13', 'TAXI', 13, 0, 2, 'Comment');