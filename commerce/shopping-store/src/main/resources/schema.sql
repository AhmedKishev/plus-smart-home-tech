drop table if exists products;

CREATE TABLE products (
    product_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_name VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    image_src VARCHAR(500),
    quantity_state VARCHAR(50) NOT NULL,
    product_state VARCHAR(50) NOT NULL,
    product_category VARCHAR(50),
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0)
);