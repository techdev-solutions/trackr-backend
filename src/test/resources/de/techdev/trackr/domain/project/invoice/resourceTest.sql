-- data for the project resource test.
INSERT INTO address (id, version, street, houseNumber, zipCode, city, country) VALUES (0, 0, 'Sun Alley', '31', '15489', 'Munich', 'Deutschland');

INSERT INTO company (id, version, companyId, name, address_id, timeForPayment) VALUES (0, 0, 1000, 'webshop Ltd.', 0, 30);

INSERT INTO invoice (id, version, identifier, debitor, creationDate, invoiceTotal, invoiceState, dueDate) VALUES (0, 0, '1003.2014.1-2014.01-1', 0, '2014-01-04', 1500.00, 'PAID', '2014-02-01');