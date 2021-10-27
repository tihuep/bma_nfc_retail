DROP USER 'bma'@'localhost';

CREATE USER 'bma'@'localhost' IDENTIFIED BY '(Bma2021)';
GRANT SELECT ON *.* TO 'bma'@'localhost';
GRANT ALL PRIVILEGES ON nfc_retail.* TO 'bma'@'localhost';
FLUSH PRIVILEGES;
