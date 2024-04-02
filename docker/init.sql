USE chess;

CREATE TABLE board_history
(
    id          int auto_increment primary key,
    source_rank int not null,
    source_file int not null,
    target_rank int not null,
    target_file int not null
);
