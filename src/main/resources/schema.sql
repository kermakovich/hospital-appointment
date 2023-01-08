SELECT 'CREATE DATABASE hospital_appointment_db'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'hospital_appointment_db');
SEt schema 'public';

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
    user_id bigint PRIMARY KEY references user_info(id),
    department varchar(50) NOT NULL,
    specialization varchar(50) NOT NULL
);


CREATE TABLE IF NOT EXISTS addresses (
    id bigserial PRIMARY KEY,
    city varchar(100) NOT NULL,
    street varchar(150) NOT NULL,
    house smallint NOT NULL,
    flat smallint
);


CREATE TABLE IF NOT EXISTS patients (
    user_id bigint PRIMARY KEY references user_info(id),
    id_address bigint references addresses(id)
);


CREATE TABLE IF NOT EXISTS patient_cards (
    patient_id bigint PRIMARY KEY references patients(user_id),
    number uuid UNIQUE NOT NULL,
    reg_date date NOT NULL
);

CREATE TABLE IF NOT EXISTS reviews (
    id bigserial PRIMARY KEY,
    id_doctor bigint references doctors(user_id),
    id_patient bigint references patients(user_id),
    description varchar(500)
);

CREATE TABLE IF NOT EXISTS card_entries (
    id bigserial PRIMARY KEY,
    id_card bigint references patient_cards(patient_id),
    diagnose varchar(100),
    treatment varchar
);

CREATE TABLE IF NOT EXISTS appointments (
    id bigserial PRIMARY KEY,
    id_doctor bigint references doctors(user_id) NOT NULL,
    id_card bigint references patient_cards(patient_id) NOT NULL,
    date_time_start timestamp without time zone NOT NULL
);
