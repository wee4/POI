-- create database 
CREATE DATABASE db_poi
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;


CREATE SEQUENCE vehicle_id_seq;
-- Table: public.vehicle
-- DROP TABLE public.vehicle;
CREATE TABLE public.vehicle
(
    id serial NOT NULL,
    creation_date timestamp NOT NULL,
    update_date timestamp NOT NULL,
    name character varying NOT NULL,
    board character varying,
    color character varying,
    CONSTRAINT vehicle_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.vehicle
    OWNER to postgres;


CREATE SEQUENCE position_id_seq;
-- Table: public.position
-- DROP TABLE public.position;
CREATE TABLE public.positions
(
    id serial NOT NULL,
    sent_date timestamp NOT NULL,
    velocity double precision NOT NULL,
    longitude double precision NOT NULL,
    latitude double precision NOT NULL,
    vehicle_id integer NOT NULL,
    ignition boolean NOT NULL DEFAULT false,
    CONSTRAINT position_pkey PRIMARY KEY (id),
    CONSTRAINT fk_vehicle_id FOREIGN KEY (vehicle_id)
        REFERENCES public.vehicle (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.positions
    OWNER to postgres;

CREATE SEQUENCE point_interest_id_seq;

-- Table: public.point_interest
-- DROP TABLE public.point_interest;
CREATE TABLE public.point_interest
(
    id serial NOT NULL,
    name character varying NOT NULL,
    radius double precision NOT NULL,
    longitude double precision NOT NULL,
    latitude double precision NOT NULL,
    CONSTRAINT point_interest_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.point_interest
    OWNER to postgres;



CREATE SEQUENCE meeting_id_seq;

-- Table: public.meeting
-- DROP TABLE public.meeting;
CREATE TABLE public.meeting
(
    id serial NOT NULL,
    point_interest_id integer NOT NULL,
    vehicle_id integer NOT NULL,
    check_in timestamp NOT NULL,
    check_out  timestamp NOT NULL,
    CONSTRAINT meeting_pkey PRIMARY KEY (id),
        CONSTRAINT fk_point_interest_id FOREIGN KEY (point_interest_id)
        REFERENCES public.point_interest (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_vehicle_id FOREIGN KEY (vehicle_id)
        REFERENCES public.vehicle (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.meeting
    OWNER to postgres;