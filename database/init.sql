DROP DATABASE IF EXISTS nfc_retail;
CREATE DATABASE nfc_retail;
USE nfc_retail;

CREATE TABLE user
(
    id         varchar(100)        not null,
    first_name varchar(100)        not null,
    last_name  varchar(100)        not null,
    email      varchar(100) unique not null,
    password   varchar(200)        not null,
    PRIMARY KEY (id)
);

CREATE TABLE purchase
(
    id      varchar(100) not null,
    user_id varchar(100) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE article
(
    id          varchar(100) not null,
    description varchar(1000),
    price       float        not null,
    image_url   varchar(200),
    PRIMARY KEY (id)
);

CREATE TABLE article_purchase
(
    amount      integer      not null,
    purchase_id varchar(100) not null,
    article_id  varchar(100) not null,
    FOREIGN KEY (purchase_id) REFERENCES purchase (id) ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
);

CREATE TABLE payment_method
(
    id   varchar(100) not null,
    name varchar(100) not null,
    PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id                varchar(100) not null,
    total             float        not null,
    currency          varchar(100) not null,
    confirmed         boolean      not null default false,
    confirmation_date datetime,
    purchase_id       varchar(100) not null,
    payment_method_id varchar(100) not null,
    PRIMARY KEY (id),
    FOREIGN KEY (purchase_id) REFERENCES purchase (id) ON DELETE CASCADE,
    FOREIGN KEY (payment_method_id) REFERENCES payment_method (id) ON DELETE CASCADE
);

