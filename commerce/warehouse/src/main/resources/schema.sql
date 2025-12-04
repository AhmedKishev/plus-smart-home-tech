drop table if exists warehouse_product;

CREATE TABLE warehouse_product (
    product_id UUID PRIMARY KEY,
    quantity BIGINT DEFAULT 0,
    fragile BOOLEAN,
    width DOUBLE PRECISION,
    height DOUBLE PRECISION,
    depth DOUBLE PRECISION,
    weight DOUBLE PRECISION,
    deliveryId DOUBLE PRECISION
);


create table if not exists bookings
(
    shopping_cart_id uuid primary key,
    delivery_weight  double precision not null,
    delivery_volume  double precision not null,
    fragile          boolean          not null,
    order_id         uuid
);

create table if not exists booking_products
(
    shopping_cart_id uuid references bookings (shopping_cart_id) on delete cascade primary key,
    product_id       uuid not null,
    quantity         integer
    )