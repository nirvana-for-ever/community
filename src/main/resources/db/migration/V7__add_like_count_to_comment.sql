alter table comment modify type int not null comment '评论的类型，是问题的评论还是评论的评论';

alter table comment
	add like_count int default 0 null;
