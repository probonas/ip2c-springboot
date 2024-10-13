begin;

create table countries
(

    id                serial primary key,
    name              varchar(128) not null,
    two_letter_code   char(2)      not null,
    three_letter_code char(3)      not null,
    created_at        timestamptz  not null
);

create table ip_addresses
(
    id         serial primary key,
    country_id int         not null,
    ip         varchar(15) not null,
    created_at timestamptz not null,
    updated_at timestamptz
);

alter table ip_addresses
    add constraint fk_country foreign key (country_id) references countries (id);

-- seed the database with values

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (1, 'Greece', 'GR', 'GRC', '2023-10-12 06:00:00.500000');

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (2, 'Germany', 'DE', 'DEU', '2023-10-12 06:00:00.500000');

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (3, 'Cyprus', 'CY', 'CYP', '2023-10-12 06:00:00.500000');

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (4, 'United States', 'US', 'USA', '2023-10-12 06:00:00.500000');

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (6, 'Spain', 'ES', 'ESP', '2023-10-12 06:00:00.500000');

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (7, 'France', 'FR', 'FRA', '2023-10-12 06:00:00.500000');

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (8, 'Italy', 'IT', 'IA ', '2023-10-12 06:00:00.500000');

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (9, 'Japan', 'JP', 'JPN', '2023-10-12 06:00:00.500000');

insert into countries (id, name, two_letter_code, three_letter_code, created_at)
values (10, 'China', 'CN', 'CHN', '2023-10-12 06:00:00.500000');

select setval('countries_id_seq', (SELECT MAX(id) FROM countries));

select setval('countries_id_seq', (SELECT MAX(id) FROM countries));

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (6, 1, '192.168.1.1', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (7, 2, '172.16.254.1', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (8, 3, '10.0.0.2', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (9, 4, '203.0.113.5', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (10, 6, '198.51.100.10', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (11, 7, '192.0.2.15', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (12, 8, '203.0.113.20', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (13, 4, '8.8.8.8', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (14, 6, '1.1.1.1', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (15, 7, '8.8.4.4', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (16, 8, '4.2.2.2', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (17, 10, '208.67.222.222', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');

insert into ip_addresses (id, country_id, ip, created_at, updated_at)
values (18, 4, '104.16.0.0', '2023-10-12 07:00:00.000000', '2023-10-12 07:00:00.000000');


select setval('ip_addresses_id_seq', (SELECT MAX(id) FROM ip_addresses));

-- create the indexes needed

create index country_two_letter_idx on countries (two_letter_code);

create index ip_idx on ip_addresses (ip);

commit;