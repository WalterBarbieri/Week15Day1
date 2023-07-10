create table school_students (
	id serial primary key,
	name varchar(20),
	lastname varchar(20),
	gender varchar(1),
	birthdate date,
	avg numeric(10,2),
	min_vote integer,
	max_vote integer	
)