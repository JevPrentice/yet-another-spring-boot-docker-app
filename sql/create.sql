create extension if not exists "uuid-ossp";

create user "postgres_username" superuser password 'postgres_password';

create sequence human_being_id_sequence;

create table human_being (
    id bigint not null primary key default nextval('human_being_id_sequence'),
    created timestamp without time zone not null default now(),
    name text not null,
    birthday date not null
);