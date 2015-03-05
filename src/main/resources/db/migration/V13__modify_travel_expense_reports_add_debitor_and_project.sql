alter table TravelExpenseReport
  add column debitor_id int8 not null default 0;

update TravelExpenseReport set debitor_id = (
    select id from Company where companyId = '1000'
);

alter table TravelExpenseReport
    alter column debitor_id drop default;

alter table TravelExpenseReport
  add column project_id int8;

alter table TravelExpenseReport
  add constraint FK854DBA92D4C285A6
  foreign key (project_id)
  references Project;

alter table TravelExpenseReport
  add constraint FK854DBA92F02B33F8
  foreign key (debitor_id)
  references Company;