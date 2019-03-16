create table IF NOT EXISTS employee (
    id integer not null,
    name varchar(255) not null,
	depId integer not null,
	gender varchar(1) not null,
	phone varchar(20) not null,
	address varchar(255) not null,
	age tinyint not null,
	created_at timestamp not null,
	lastModify_at timestamp not null,
    primary key(id)
);

create table IF NOT EXISTS department (
    id integer not null,
    name varchar(255) not null,
    primary key(id)
);