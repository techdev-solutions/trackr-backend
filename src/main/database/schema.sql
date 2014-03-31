
    alter table BillableTime 
        drop constraint FK3EBA06E37BE2CBE

    alter table BillableTime 
        drop constraint FK3EBA06E65C090FD

    alter table Company 
        drop constraint FK9BDFD45D9475612A

    alter table ContactPerson 
        drop constraint FK4E7B4375FA3D5CEA

    alter table Credential_Authority 
        drop constraint FK3DA6FD5B34EFD736

    alter table Credential_Authority 
        drop constraint FK3DA6FD5B27F2CCA0

    alter table Project 
        drop constraint FK50C8E2F98F0FA88A

    alter table Project 
        drop constraint FK50C8E2F9F02B33F8

    alter table VacationRequest 
        drop constraint FK266F9CD248918324

    alter table VacationRequest 
        drop constraint FK266F9CD255198E9B

    alter table WorkTime 
        drop constraint FK5EE019E37BE2CBE

    alter table WorkTime 
        drop constraint FK5EE019E65C090FD

    drop table if exists Address cascade

    drop table if exists Authority cascade

    drop table if exists BillableTime cascade

    drop table if exists Company cascade

    drop table if exists ContactPerson cascade

    drop table if exists Credential cascade

    drop table if exists Credential_Authority cascade

    drop table if exists Employee cascade

    drop table if exists Holiday cascade

    drop table if exists Project cascade

    drop table if exists VacationRequest cascade

    drop table if exists WorkTime cascade

    drop sequence hibernate_sequence

    create table Address (
        id int8 not null,
        city varchar(255),
        country varchar(255),
        houseNumber varchar(255),
        street varchar(255),
        version int4,
        zipCode varchar(255),
        primary key (id)
    )

    create table Authority (
        id int8 not null,
        authority varchar(255) unique,
        authorityOrder int4,
        primary key (id)
    )

    create table BillableTime (
        id int8 not null,
        date date,
        minutes int4,
        version int4,
        employee int8,
        project int8,
        primary key (id),
        unique (employee, project, date)
    )

    create table Company (
        id int8 not null,
        companyId int8 unique,
        name varchar(255),
        version int4,
        address_id int8,
        primary key (id)
    )

    create table ContactPerson (
        id int8 not null,
        email varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        phone varchar(255),
        salutation varchar(255),
        version int4,
        company int8,
        primary key (id)
    )

    create table Credential (
        id int8 not null,
        email varchar(255) unique,
        enabled boolean,
        locale varchar(255),
        primary key (id)
    )

    create table Credential_Authority (
        Credential_id int8 not null,
        authorities_id int8 not null
    )

    create table Employee (
        id int8 not null,
        federalState varchar(255),
        firstName varchar(255),
        hourlyCostRate numeric(19, 2),
        joinDate date,
        lastName varchar(255),
        leaveDate date,
        phoneNumber varchar(255),
        salary numeric(19, 2),
        title varchar(255),
        vacationEntitlement float4,
        version int4,
        primary key (id)
    )

    create table Holiday (
        id int8 not null,
        day date,
        federalState varchar(255),
        name varchar(255),
        primary key (id)
    )

    create table Project (
        id int8 not null,
        dailyRate numeric(19, 2),
        fixedPrice numeric(19, 2),
        hourlyRate numeric(19, 2),
        identifier varchar(255) unique,
        name varchar(255),
        version int4,
        volume int4,
        company_id int8,
        debitor_id int8,
        primary key (id)
    )

    create table VacationRequest (
        id int8 not null,
        approvalDate date,
        endDate date,
        numberOfDays int4,
        startDate date,
        status varchar(255),
        submissionTime timestamp,
        version int4,
        approver_id int8,
        employee_id int8,
        primary key (id)
    )

    create table WorkTime (
        id int8 not null,
        comment varchar(255),
        date date,
        endTime time,
        startTime time,
        version int4,
        employee int8,
        project int8,
        primary key (id)
    )

    alter table BillableTime 
        add constraint FK3EBA06E37BE2CBE 
        foreign key (project) 
        references Project

    alter table BillableTime 
        add constraint FK3EBA06E65C090FD 
        foreign key (employee) 
        references Employee

    alter table Company 
        add constraint FK9BDFD45D9475612A 
        foreign key (address_id) 
        references Address

    alter table ContactPerson 
        add constraint FK4E7B4375FA3D5CEA 
        foreign key (company) 
        references Company

    alter table Credential_Authority 
        add constraint FK3DA6FD5B34EFD736 
        foreign key (authorities_id) 
        references Authority

    alter table Credential_Authority 
        add constraint FK3DA6FD5B27F2CCA0 
        foreign key (Credential_id) 
        references Credential

    alter table Project 
        add constraint FK50C8E2F98F0FA88A 
        foreign key (company_id) 
        references Company

    alter table Project 
        add constraint FK50C8E2F9F02B33F8 
        foreign key (debitor_id) 
        references Company

    alter table VacationRequest 
        add constraint FK266F9CD248918324 
        foreign key (approver_id) 
        references Employee

    alter table VacationRequest 
        add constraint FK266F9CD255198E9B 
        foreign key (employee_id) 
        references Employee

    alter table WorkTime 
        add constraint FK5EE019E37BE2CBE 
        foreign key (project) 
        references Project

    alter table WorkTime 
        add constraint FK5EE019E65C090FD 
        foreign key (employee) 
        references Employee

    create sequence hibernate_sequence
