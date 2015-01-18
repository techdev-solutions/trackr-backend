create table Invoice (
    id int8 not null,
    creationDate date,
    dueDate date,
    identifier varchar(255),
    invoiceState varchar(255),
    invoiceTotal numeric(19, 2),
    version int4,
    debitor int8,
    primary key (id),
    unique (identifier)
);

alter table Invoice
    add constraint FKD80EDB0D1D75383C
    foreign key (debitor)
    references Company;
