create schema if not exists hospital;
set schema 'hospital';


CREATE TABLE IF NOT EXISTS user_info (
    id bigserial PRIMARY KEY,
    name varchar(35) NOT NULL,
    surname varchar(35) NOT NULL,
    fatherhood varchar(35),
    birthday date NOT NULL,
    email varchar(320) UNIQUE NOT NULL,
    password varchar(20) NOT NULL
);


CREATE TABLE IF NOT EXISTS doctors (
    user_id bigint PRIMARY KEY,
    department varchar(50) NOT NULL,
    specialization varchar(50) NOT NULL,
    cabinet smallint,
    constraint fk_user_info foreign key(user_id) references user_info(id)
);


CREATE TABLE IF NOT EXISTS addresses (
    id bigserial PRIMARY KEY,
    city varchar(100) NOT NULL,
    street varchar(150) NOT NULL,
    house smallint NOT NULL,
    flat smallint
);


CREATE TABLE IF NOT EXISTS patients (
    user_id bigint PRIMARY KEY,
    id_address bigint,
    constraint fk_user_info foreign key(user_id) references user_info(id),
    constraint fk_address foreign key(id_address) references addresses(id)
);


CREATE TABLE IF NOT EXISTS patient_cards (
    patient_id bigint PRIMARY KEY,
    number uuid UNIQUE NOT NULL,
    reg_date date NOT NULL,
    constraint fk_patient foreign key(patient_id) references patients(user_id)
);

CREATE TABLE IF NOT EXISTS reviews (
    id bigserial PRIMARY KEY,
    id_doctor bigint,
    id_patient bigint,
    description varchar(500),
    constraint fk_patient foreign key(user_id) references patients(user_id),
    constraint fk_doctor foreign key(user_id) references doctors(user_id)
);

CREATE TABLE IF NOT EXISTS appointments (
    id bigserial PRIMARY KEY,
    id_doctor bigint,
    id_card bigint,
    date_time_start timestamp without time zone NOT NULL,
    constraint fk_doctor foreign key(id_doctor) references doctors(user_id),
    constraint fk_patient_card foreign key(id_card) references patient_cards(patient_id)
);
