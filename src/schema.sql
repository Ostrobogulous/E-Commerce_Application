DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS electronicProduct;
DROP TABLE IF EXISTS musicalProduct;
DROP TABLE IF EXISTS bookProduct;
DROP TABLE IF EXISTS purchase;


CREATE TABLE user(
    id integer PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL COLLATE NOCASE,
    password TEXT NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    role TEXT NOT NULL DEFAULT 'customer'
);

CREATE TABLE product(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    price REAL NOT NULL,
    stock INT,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE electronicProduct (
    id INTEGER PRIMARY KEY AUTOINCREMENT ,
    product_id INT NOT NULL,
    brand VARCHAR(255),
    warranty INT,
    color VARCHAR(10),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE musicalProduct (
    id INTEGER PRIMARY KEY AUTOINCREMENT ,
    product_id INT NOT NULL,
    instrument_type VARCHAR(255),
    brand VARCHAR(255),
    color VARCHAR(10),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE bookProduct (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    product_id INT NOT NULL,
    author VARCHAR(255),
    genre VARCHAR(255),
    year INT,
    page_count INT,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE purchase(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total_amount REAL NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

