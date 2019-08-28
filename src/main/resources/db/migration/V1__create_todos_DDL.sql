CREATE TABLE  if not exists accounts
(
    id bigserial not null
        constraint accounts_pk
            primary key,
    username varchar not null,
    password varchar,
    active boolean
);

ALTER TABLE  accounts owner to postgres;

CREATE TABLE  if not exists todos
(
    id bigserial not null
        constraint plans_pk
            primary key,
    description varchar not null,
    isdone boolean default false
);

ALTER TABLE  todos owner to postgres;

CREATE UNIQUE index if not exists plans_id_uindex
    on todos (id);

CREATE TABLE  if not exists todos_in_account
(
    id_row bigserial not null,
    id_account bigint not null
        constraint plans_in_account_accounts_id_fk
            references accounts,
    id_plan bigint not null
        constraint plans_in_account_plans_id_fk
            references todos
);

ALTER TABLE  todos_in_account owner to postgres;

CREATE UNIQUE index if not exists plans_in_account_id_row_uindex
    on todos_in_account (id_row);

CREATE TABLE  if not exists roles
(
    id serial not null
        constraint roles_pk
            primary key,
    role varchar
);

ALTER TABLE  roles owner to postgres;

CREATE UNIQUE index if not exists roles_id_uindex
    on roles (id);

CREATE TABLE  if not exists user_role
(
    user_id bigint
        constraint user_role_accounts_id_fk
            references accounts,
    role_id integer
        constraint user_role_roles_id_fk
            references roles
);

ALTER TABLE  user_role owner to postgres;
