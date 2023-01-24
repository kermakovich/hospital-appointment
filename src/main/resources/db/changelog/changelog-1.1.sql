--liquibase formatted sql
--changeset ermakovich:2.1

ALTER TABLE IF EXISTS user_info
    ALTER COLUMN password TYPE character varying(200)