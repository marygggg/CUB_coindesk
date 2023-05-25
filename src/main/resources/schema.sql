DROP TABLE IF EXISTS currency_name;

CREATE TABLE currency_name (
  code VARCHAR(100) PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);