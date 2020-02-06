create table notification
(
	id int auto_increment,
	sender int not null,
	receiver int not null,
	type int null comment '对于评论或者问题的消息',
	qid int not null comment '父级（评论或者问题）id',
	status int default 0 not null comment '状态，已读或未读',
	gmt_create bigint not null,
	constraint notification_pk
		primary key (id)
);