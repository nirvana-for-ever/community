create table user
(
  id           int auto_increment
    primary key,
  name         varchar(255) null,
  account_id   varchar(255) null,
  token        char(50)     null,
  gmt_create   bigint       null,
  gmt_modified bigint       null
);
