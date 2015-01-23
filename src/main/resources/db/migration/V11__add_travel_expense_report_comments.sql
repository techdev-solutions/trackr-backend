create table TravelExpenseReportComment (
  id int8 not null,
  submissionDate timestamp,
  text varchar(255),
  employee_id int8,
  travelExpenseReport_id int8,
  primary key (id)
);

alter table TravelExpenseReportComment
  add constraint FKCEF7236D55198E9B
  foreign key (employee_id)
  references Employee;

alter table TravelExpenseReportComment
  add constraint FKCEF7236D6F1C4208
  foreign key (travelExpenseReport_id)
  references TravelExpenseReport;