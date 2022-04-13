create table projects
(
    id            varchar(255) not null
        primary key,
    creation_date date,
    description   varchar(255),
    edit_date     date,
    name          varchar(255)
);

alter table projects
    owner to hits2;

create table users
(
    id            varchar(255) not null
        primary key,
    creation_date date,
    edit_date     date,
    email         varchar(255),
    full_name     varchar(255),
    password_hash varchar(255),
    role          varchar(255)
);

alter table users
    owner to hits2;

create table comments
(
    id            varchar(255) not null
        primary key,
    creation_date date,
    edit_date     date,
    text          varchar(255),
    user_id       varchar(255)
        constraint user_id_constraint
            references users
);

alter table comments
    owner to hits2;

create table tasks
(
    id            varchar(255) not null
        primary key,
    creation_date date,
    description   varchar(255),
    edit_date     date,
    name          varchar(255),
    priority      varchar(255),
    time_mark     varchar(255),
    project_id    varchar(255)
        constraint project_id_constraint
            references projects,
    creator_id    varchar(255)
        constraint creator_id_constraint
            references users,
    editor_id     varchar(255)
        constraint editor_id_constraint
            references users
);

alter table tasks
    owner to hits2;

create table comment_task
(
    comment_id varchar(255) not null
        constraint comment_id_constraint
            references comments,
    task_id    varchar(255) not null
        constraint task_id_constraint
            references tasks
);

alter table comment_task
    owner to hits2;

