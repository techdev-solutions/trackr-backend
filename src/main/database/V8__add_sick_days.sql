create table SickDays (
    id int8 not null,
    endDate date,
    startDate date,
    version int4,
    employee_id int8,
    primary key (id)
);

alter table SickDays
    add constraint FKF63D6D555198E9B
    foreign key (employee_id)
    references Employee;