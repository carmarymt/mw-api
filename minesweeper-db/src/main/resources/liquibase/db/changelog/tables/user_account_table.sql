create table user_account (
  user_id bigint unsigned not null auto_increment comment 'Unique identifier',
  user_name varchar(120) not null comment 'User Name of the user',
  name varchar(120) not null comment 'Name of the user',
  surname varchar(120) not null comment 'Surname of the user',
  email varchar(120) not null comment 'Email of the user',
  date_created timestamp(3) not null comment 'Indicates the date when the user is created in the system',
  primary key (user_id)
);