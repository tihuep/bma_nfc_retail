SET FOREIGN_KEY_CHECKS = 0;

DELETE
FROM article;
DELETE
FROM payment_method;

INSERT INTO article (id, description, price)
VALUES ('5dd2f3c2-8958-4934-83d6-d3c7918470d3', 'Drink-Milch (1 Liter)', 1.75);
INSERT INTO article (id, description, price)
VALUES ('598ba466-9fd9-4104-9d6e-c474acf2b837', 'Vollrahm (250ml)', 1.75);
INSERT INTO article (id, description, price)
VALUES ('59c289a8-1cb1-44a1-8d7a-4cb66a717c6b', 'Eier roh (6 Stück)', 4.50);
INSERT INTO article (id, description, price)
VALUES ('99adbe62-dd41-4913-a160-1741b107b887', 'Penne (1kg)', 3.0);
INSERT INTO article (id, description, price)
VALUES ('2953784f-f7e6-4f57-89d6-21ca151e2059', 'Reis (1kg)', 5.50);
INSERT INTO article (id, description, price)
VALUES ('67fbdf49-8548-43c1-83fb-37198488d653', 'Weissmehl (1kg)', 1.20);
INSERT INTO article (id, description, price)
VALUES ('6d199311-f992-4923-bcdc-23a430f4511a', 'Zucker (1kg)', 1.0);
INSERT INTO article (id, description, price)
VALUES ('10af8775-992f-48c5-a270-2ff25815bb8a', 'Äpfel (1kg)', 3.30);
INSERT INTO article (id, description, price)
VALUES ('ee53c56f-abe2-4af5-b666-52895e3a01bf', 'Bananen (1kg)', 3.30);
INSERT INTO article (id, description, price)
VALUES ('6586d884-5945-4a2a-a56b-2fd9aa1536b4', 'Paprika Chips (250g)', 5.50);

INSERT INTO payment_method (id, name)
VALUES ('a77a2136-7f4f-4e9a-8217-6422f2f8c1d0', 'PayPal');
INSERT INTO payment_method (id, name)
VALUES ('5d4b02c5-8ede-44f5-9d18-a6d26b7ea7cc', 'Twint');
INSERT INTO payment_method (id, name)
VALUES ('fdaa1062-2d9d-44ae-bd83-dcfa18a20924', 'Kreditkarte');

SET FOREIGN_KEY_CHECKS = 1;
