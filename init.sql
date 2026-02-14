--
-- PostgreSQL database dump
--

\restrict N0yzEII7cWKH5WbRjlbcsxkRggF7iomLagjUQDMoNBHgREGTXaPrniVViTPsxHX

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
                             id integer NOT NULL,
                             name character varying(15)
);


ALTER TABLE public.role OWNER TO postgres;

--
-- Name: role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.role_id_seq OWNER TO postgres;

--
-- Name: role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;


--
-- Name: situation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.situation (
                                  id integer NOT NULL,
                                  user_id character(36),
                                  title character varying(255),
                                  description character varying(1023),
                                  date timestamp without time zone,
                                  approved boolean
);


ALTER TABLE public.situation OWNER TO postgres;

--
-- Name: situation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.situation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.situation_id_seq OWNER TO postgres;

--
-- Name: situation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.situation_id_seq OWNED BY public.situation.id;


--
-- Name: track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track (
                              id integer NOT NULL,
                              title character varying(255),
                              author character varying(255),
                              filepath character varying(1023),
                              approved boolean
);


ALTER TABLE public.track OWNER TO postgres;

--
-- Name: track_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.track_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.track_id_seq OWNER TO postgres;

--
-- Name: track_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.track_id_seq OWNED BY public.track.id;


--
-- Name: tracks_situations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tracks_situations (
                                          id_situation integer,
                                          id_track integer,
                                          id_user_suggest character(36),
                                          approved boolean
);


ALTER TABLE public.tracks_situations OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              id character(36) NOT NULL,
                              login character varying(31),
                              password character varying(255),
                              email character varying(255),
                              role integer,
                              username character varying(255),
                              firstname character varying(255),
                              lastname character varying(255),
                              bio character varying(1023),
                              profilephoto character varying(1023)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);


--
-- Name: situation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.situation ALTER COLUMN id SET DEFAULT nextval('public.situation_id_seq'::regclass);


--
-- Name: track id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track ALTER COLUMN id SET DEFAULT nextval('public.track_id_seq'::regclass);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: situation situation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.situation
    ADD CONSTRAINT situation_pkey PRIMARY KEY (id);


--
-- Name: track track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: situation situation_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.situation
    ADD CONSTRAINT situation_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: track track_author_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_author_fkey FOREIGN KEY (author) REFERENCES public.users(id);


--
-- Name: tracks_situations tracks_situations_id_situation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracks_situations
    ADD CONSTRAINT tracks_situations_id_situation_fkey FOREIGN KEY (id_situation) REFERENCES public.situation(id);


--
-- Name: tracks_situations tracks_situations_id_track_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracks_situations
    ADD CONSTRAINT tracks_situations_id_track_fkey FOREIGN KEY (id_track) REFERENCES public.track(id);


--
-- Name: tracks_situations tracks_situations_id_user_suggest_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracks_situations
    ADD CONSTRAINT tracks_situations_id_user_suggest_fkey FOREIGN KEY (id_user_suggest) REFERENCES public.users(id);


--
-- Name: users users_role_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_fkey FOREIGN KEY (role) REFERENCES public.role(id);


INSERT INTO public.role (id, name) VALUES (1, 'USER'), (2, 'ADMIN');

CREATE EXTENSION IF NOT EXISTS "pgcrypto" SCHEMA public;

INSERT INTO public.users (
    id, login, password, email, role, username, firstname, lastname, bio, profilephoto
) VALUES (
             public.gen_random_uuid()::text,
             'admin',
             UPPER(md5('admin')),
             'admin@example.com',
             2,
             'Administrator',
             'Admin',
             'Admin',
             'Administrator account',
             NULL
         );
--
-- PostgreSQL database dump complete
--

\unrestrict N0yzEII7cWKH5WbRjlbcsxkRggF7iomLagjUQDMoNBHgREGTXaPrniVViTPsxHX

