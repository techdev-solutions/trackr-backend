-- data for the vacation request resource test.
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, vacationEntitlement)
  VALUES (0, 0, 'employee@techdev.de', 'John', 'Johnson', 'Software Engineer', 80, 10000, 'BERLIN', '2014-02-01', 30);

INSERT INTO vacationrequest (id, enddate, numberofdays, startdate, status, submissiontime, version, employee_id)
  VALUES (0, '2015-02-06', 5, '2015-02-02', 'PENDING', '2015-01-20 19:21:56', 0, 0);

-- approved and rejected requests for various tests
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, vacationEntitlement)
  VALUES (1, 0, 'employee2@techdev.de', 'John', 'Johnson', 'Software Engineer', 80, 10000, 'BERLIN', '2014-02-01', 30);

INSERT INTO vacationrequest (id, enddate, numberofdays, startdate, status, submissiontime, version, employee_id, approvaldate, approver_id)
  VALUES (1, '2015-02-06', 5, '2015-02-02', 'APPROVED', '2015-01-20 19:21:56', 0, 0, '2015-01-21 09:13:47', 1);

INSERT INTO vacationrequest (id, enddate, numberofdays, startdate, status, submissiontime, version, employee_id, approvaldate, approver_id)
  VALUES (2, '2015-02-06', 5, '2015-02-02', 'REJECTED', '2015-01-20 19:21:56', 0, 0, '2015-01-21 09:13:47', 1);
