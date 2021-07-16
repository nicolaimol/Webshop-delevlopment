create table if not exists User (
    id int auto_increment primary key,
    username varchar(20) unique not null,
    password text not null,
    email varchar(50) unique,
    phone char(8) unique,
    address varchar(50) not null
);

create table if not exists Account (
    id int auto_increment primary key,
    type varchar(20) not null,
    name varchar(20) not null,
    balance decimal not null,
    owner int not null,
    constraint ownerFK foreign key (owner) references User(id) on update cascade on delete cascade
);

create table if not exists Transaction (
    id int auto_increment primary key,
    sender int not null,
    receiver int not null,
    date datetime not null,
    amount decimal not null,
    constraint senderFK foreign key (sender) references Account(id) on update cascade on delete cascade ,
    constraint receiverFK foreign key (receiver) references Account(id) on update cascade on delete cascade
);

create or replace view TransactionView
as
    select distinct Transaction.id as trans, sender, receiver,
           (select username from User, Account, Transaction where Transaction.sender = Account.id and Account.owner = User.id and Transaction.id = trans)
           as senderU,
           (select username from User, Account, Transaction where Transaction.receiver = Account.id and Account.owner = User.id and Transaction.id = trans)
           as receiverU,
           date, Transaction.amount
    from User, Transaction, Account;

