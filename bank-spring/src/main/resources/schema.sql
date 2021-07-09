create table User (
    id int auto_increment primary key,
    username varchar(20) unique not null,
    password text not null,
    email varchar(50) unique,
    phone char(8) unique,
    address varchar(50) not null
);

create table Account (
    id int auto_increment primary key,
    type varchar(20) not null,
    balance decimal not null,
    owner int not null,
    constraint ownerFK foreign key (owner) references User(id) on update cascade on delete cascade
);

create table Transaction (
    id int auto_increment primary key,
    sender int not null,
    receiver int not null,
    date datetime not null,
    amount decimal not null,
    constraint senderFK foreign key (sender) references User(id) on update cascade on delete cascade ,
    constraint receiverFK foreign key (receiver) references User(id) on update cascade on delete cascade
);
