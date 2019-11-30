create table question
(
	id int auto_increment,
	title varchar(255) null,
	description text null,
	gmt_create bigint null,
	gmt_modified bigint null,
	creator int null,
	read_count int default 0 null,
	comment_count int default 0 null,
	like_count int default 0 null,
	tag varchar(255) null,
	constraint question_pk
		primary key (id)
);

