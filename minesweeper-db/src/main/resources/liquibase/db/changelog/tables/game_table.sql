create table game (
  game_id bigint unsigned not null auto_increment comment 'Unique identifier',
  status_id tinyint default 1 comment 'Game statuses: 1 - In_progress, 2 - Paused, 3 - Game_over, 4 - Finished',
  num_rows tinyint not null comment 'Number of rows',
  num_columns tinyint not null comment 'Number of columns',
  num_mines tinyint not null comment 'Number of mines',
  board text not null comment 'Board of the game',
  original_board text not null comment 'Original Board of the game',
  date_created timestamp(3) not null comment 'Indicates the date when the game is created in the system',
  last_updated timestamp(3) not null comment 'Indicates the date when the game is updated in the system',
  time_duration_sec int unsigned not null default 0 comment 'Duration time in seconds of the game',
  user_id bigint unsigned default null comment 'Associated user id',
  primary key (game_id)
);