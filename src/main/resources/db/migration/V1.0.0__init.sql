create table hibernate_sequence (
    next_val bigint
)engine=InnoDB;

insert into hibernate_sequence values (1);

create table Payload (
    id bigint not null,
    bucketName varchar(255),
    contentType varchar(255),
    createdAt datetime(6),
    email varchar(255),
    fileName varchar(255),
    notificationType integer,
    phone varchar(255),
    status integer,
    updatedAt datetime(6),
    userName varchar(255),
    primary key (id)
)engine=InnoDB;