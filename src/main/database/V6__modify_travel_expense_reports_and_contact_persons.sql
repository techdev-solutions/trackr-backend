alter table TravelExpenseReport
  add column submissionDate timestamp;

alter table ContactPerson
  add column roles varchar(255);
