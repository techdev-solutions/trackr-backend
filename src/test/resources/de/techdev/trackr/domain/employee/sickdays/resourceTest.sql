-- data for the sick days resource test.
INSERT INTO employee (id, version, email, firstName, lastName, title, hourlyCostRate, salary, federalState, joinDate, vacationEntitlement, deleted)
  VALUES (0, 0, 'employee@techdev.de', 'John', 'Johnson', 'Software Engineer', 80, 10000, 'BERLIN', '2014-02-01', 30, false);
INSERT INTO sickdays (id, enddate, startdate, version, employee_id) VALUES (0, '2014-01-05', '2014-01-01', 0, 0);