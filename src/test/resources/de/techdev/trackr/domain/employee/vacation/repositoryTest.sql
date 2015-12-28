-- data for the vacation request repository test.
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, vacationEntitlement, deleted)
  VALUES (0, 0, 'employee@techdev.de', 'John', 'Johnson', 'Software Engineer', 80, 10000, 'BERLIN', '2014-02-01', 30, false);

INSERT INTO vacationrequest (id, enddate, numberofdays, startdate, status, submissiontime, version, employee_id)
  VALUES (0, '2014-12-08', 5, '2014-10-01', 'APPROVED', '2014-10-15 19:21:56', 0, 0);

INSERT INTO vacationrequest (id, enddate, numberofdays, startdate, status, submissiontime, version, employee_id)
  VALUES (1, '2014-12-08', 5, '2014-10-01', 'REJECTED', '2014-10-15 19:21:56', 0, 0);

INSERT INTO vacationrequest (id, enddate, numberofdays, startdate, status, submissiontime, version, employee_id)
  VALUES (3, '2014-12-08', 5, '2014-10-01', 'PENDING', '2014-10-15 19:21:56', 0, 0);