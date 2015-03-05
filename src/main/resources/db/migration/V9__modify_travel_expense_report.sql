alter table TravelExpenseReport
  add column approvalDate timestamp;

alter table TravelExpenseReport
  add column approver_id int8;

alter table TravelExpenseReport
  add constraint FK854DBA9248918324
  foreign key (approver_id)
  references Employee;