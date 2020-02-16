create table loan_applications
(
    id int auto_increment,
    personal_id char(11) not null,
    first_name text not null,
    last_name text not null,
    birth_date date not null,
    employer text,
    salary decimal(12,2),
    monthly_liability decimal(12,2),
    requested_amount decimal(12,2),
    requested_term int not null,
    requested_term_type int not null,
    constraint loan_applications_pk
        primary key (id)
);

create table users
(
    id int auto_increment,
    user_name varchar(128) not null,
    password varchar(128) not null,
    enabled boolean default true not null,
    constraint users_pk
        primary key (id)
);

create unique index users_user_name_uindex
    on users (user_name);

create table authorities
(
    id int auto_increment,
    authority_name varchar(128) not null,
    active boolean default true not null,
    constraint authorities_pk
        primary key (id)
);

create unique index authorities_authority_name_uindex
    on authorities (authority_name);

create table user_authorities
(
    user_id int not null,
    authority_id int not null
);

create unique index user_authorities_user_id_authority_id_uindex
    on user_authorities (user_id, authority_id);






