--liquibase formatted sql
--changeset ermakovich:2

ALTER TABLE IF EXISTS hospital.user_info
    ADD COLUMN role character varying(20);
