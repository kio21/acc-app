create table public.user
(
    id            bigserial primary key,
    name          varchar(500),
    date_of_birth date,
    password      varchar(500)
);

create table public.account
(
    id              bigserial primary key,
    user_id         bigint references "user" (id),
    initial_balance decimal,
    balance         decimal
);

create table public.email_data
(
    id      bigserial primary key,
    user_id bigint references "user" (id),
    email   varchar(200) unique
);

create table public.phone_data
(
    id      bigserial primary key,
    user_id bigint references "user" (id),
    phone   varchar(13) unique
);
