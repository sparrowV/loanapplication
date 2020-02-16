
create table loan_applications
(
    id int auto_increment,
    personal_id varchar(11) not null,
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

