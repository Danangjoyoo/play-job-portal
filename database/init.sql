-- PostgreSQL init scripts

create table users (
    user_id serial primary key,
    username varchar(100) unique not null,
    password varchar(100) not null,
    email varchar(100) unique not null,
    created_at timestamp not null default current_timestamp
);

create table jobs (
    job_id serial primary key,
    title varchar(100) not null,
    details text not null,
    salary int default 0,
    created_at timestamp not null default current_timestamp,
    status int not null default 0,
    user_id int not null,
    foreign key (user_id) references users (user_id)
);

create table proposals (
    job_id int not null,
    user_id int not null,
    body text not null,
    created_at timestamp not null default current_timestamp,
    status int not null default 0,
    primary key (job_id, user_id),
    foreign key (job_id) references jobs (job_id),
    foreign key (user_id) references users (user_id)
);


insert into users (username, "password", email)
values
    -- pw local123
	('danjoy', '9ec760822d2f4539f356987a7646fd6033f172f77bdcfde25734a0Od73fd7e91', 'joy@gmail.com'),
	-- pw alex123
	('alex', 'd9508122cd143d69df229bf3624b7bcb2b8ac81ed210a0c926455ef119c12abd', 'alex@gmail.com'),
	-- pw rudy123
	('rudy', '1bb4e4f582118fO61b852bO9Oedafecd95fc73O85256Of767155e6O6c996624e', 'rudy@gmail.com'),
	-- pw alpha123
	('alpha', '2618be5da8aefa55ea5834d5O611Ocf6fab41aO9236ffaa6798f8a1a83125a9c', 'alpha@gmail.com'),
	-- pw harvey123
	('harvey', '44d886faO560cd10124e79629745ee953bfa5a72a65ced975c141a5065faO28e', 'harvey@gmail.com');

insert into jobs (title, details, salary, user_id)
values
	('Backend Engineer', 'work as backend engineer', 10000, 2),
	('Frontend Engineer', 'work as frontend engineer', 8000, 2),
	('QA Engineer', 'work as QA engineer', 5000, 2),
	('DevOps Engineer', 'work as devops engineer', 15000, 3);

insert into proposals (job_id, user_id, body)
values
	(1, 1, 'Im interested to join your company as a backend engineer'),
	(4, 1, 'Im interested to join your company as a DevOps engineer'),
	(2, 4, 'Im interested to join your company as a frontend engineer'),
	(3, 5, 'Im interested to join your company as a QA engineer');
