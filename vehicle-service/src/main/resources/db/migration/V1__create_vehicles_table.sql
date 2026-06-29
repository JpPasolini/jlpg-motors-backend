CREATE TABLE vehicles (
    id UUID PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year_model INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    plate VARCHAR(10) NOT NULL UNIQUE
);