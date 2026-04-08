create table if not exists product (
    id integer primary key autoincrement,
    name varchar(255) not null,
    description text not null,
    sku varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create index if not exists product_name_description_sku on product(name,description,sku);

create table if not exists product_variant (
    id integer primary key autoincrement,
    name varchar(255) not null,
    price decimal(10, 2) not null,
    stock integer not null,
    color varchar(225) not null,
    size varchar(225) not null,
    product_id integer not null,
    foreign key(product_id) references product(id)
);

create index if not exists product_variant_name_price_stock on product_variant(name,price,stock);