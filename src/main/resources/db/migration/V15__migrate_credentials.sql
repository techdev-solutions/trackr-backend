ALTER TABLE Employee
  ADD COLUMN email VARCHAR(255);

ALTER TABLE Employee
    ADD UNIQUE (email);

-- transfer the email to the employee
UPDATE Employee e SET email = (SELECT email FROM Credential WHERE id = e.id);

ALTER TABLE Employee
    ALTER COLUMN email SET NOT NULL;

CREATE TABLE Settings (
    id INT8 NOT NULL,
    type VARCHAR(255),
    value VARCHAR(255) NOT NULL,
    employee_id INT8 NOT NULL,
    PRIMARY KEY (id)
);

alter table Settings add constraint FK_18fk82qvt48n13545pq4dt2fr foreign key (employee_id) references Employee;


-- transfer locale setting from credential to settins
INSERT INTO Settings (id, type, value, employee_id) (
  SELECT nextval('hibernate_sequence'), 'LOCALE', locale, id FROM Credential
);

DROP TABLE credential_authority;
DROP TABLE credential;
DROP TABLE authority;
