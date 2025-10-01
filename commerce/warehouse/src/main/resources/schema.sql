drop table if exists warehouse_product;

CREATE TABLE warehouse_product (
    product_id UUID PRIMARY KEY,
    quantity BIGINT DEFAULT 0,
    fragile BOOLEAN,
    width DOUBLE PRECISION,
    height DOUBLE PRECISION,
    depth DOUBLE PRECISION,
    weight DOUBLE PRECISION
);