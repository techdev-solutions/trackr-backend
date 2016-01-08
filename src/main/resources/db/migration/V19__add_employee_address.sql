ALTER TABLE employee ADD COLUMN address_id int8;
ALTER TABLE employee ADD CONSTRAINT employee_contact_address FOREIGN KEY (address_id) REFERENCES address;
