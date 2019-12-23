create table comment
(
	id int auto_increment,
	q_id int not null,
	type int not null,
	commentator int not null,
	gmt_create bigint not null,
	content varchar(2000) null,
	constraint comment_pk
		primary key (id)
);