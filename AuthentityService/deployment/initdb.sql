CREATE SCHEMA `spacex-agency-system` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
create table craftnames
(
    id   tinyint auto_increment,
    name varchar(50) not null,
    constraint id
        unique (id)
);

alter table craftnames
    add primary key (id);

create table roles
(
    id   tinyint auto_increment,
    role varchar(50) not null,
    constraint id
        unique (id)
);

create table accounts
(
    id            bigint auto_increment,
    first_name    varchar(50)          not null,
    last_name     varchar(50)          not null,
    email         varchar(254)         not null,
    phone         varchar(14)          null,
    role_id       tinyint              null,
    password_hash varchar(255)         not null,
    enabled       tinyint(1) default 1 not null,
    constraint email
        unique (email),
    constraint id
        unique (id),
    constraint phone
        unique (phone),
    constraint position_fk
        foreign key (role_id) references roles (id)
            on update cascade on delete set null,
    constraint email_chk
        check (`email` like '%@%'),
    constraint phone_chk
        check (`phone` like '+%')
);

alter table accounts
    add primary key (id);

create table servicetypes
(
    id      tinyint auto_increment,
    service varchar(50) not null,
    constraint id
        unique (id)
);

alter table servicetypes
    add primary key (id);

create table spacecrafts
(
    id           bigint auto_increment,
    name_id      tinyint null,
    capacity     float   not null,
    max_weight   int     not null,
    launch_price int     not null,
    constraint id
        unique (id),
    constraint craftname_fk
        foreign key (name_id) references craftnames (id)
            on update cascade on delete set null,
    constraint craftname_chk
        check (`name_id` is not null)
);

alter table spacecrafts
    add primary key (id);

create table statuses
(
    id     tinyint auto_increment,
    status varchar(50) not null,
    constraint id
        unique (id),
    constraint status
        unique (status)
);

alter table statuses
    add primary key (id);

create table misions
(
    id              bigint auto_increment,
    name            varchar(50)                not null,
    description     varchar(1000)              null,
    cust_id         bigint                     not null,
    craft_id        bigint                     not null,
    status_id       tinyint  default 1         null,
    curator_id      bigint   default 3         null,
    payload_weight  int      default 200       not null,
    date            datetime default curdate() not null,
    duration        float    default 14        null,
    mision_price    bigint                     not null,
    service_type_id tinyint                    null,
    service_price   int                        not null,
    constraint id
        unique (id),
    constraint name
        unique (name),
    constraint craft_fk
        foreign key (craft_id) references spacecrafts (id)
            on update cascade on delete cascade,
    constraint curator_fk
        foreign key (curator_id) references accounts (id)
            on update cascade on delete cascade,
    constraint cust_fk
        foreign key (cust_id) references accounts (id)
            on update cascade on delete cascade,
    constraint service_fk
        foreign key (service_type_id) references servicetypes (id)
            on update cascade on delete cascade,
    constraint status_fk
        foreign key (status_id) references statuses (id)
            on update cascade on delete cascade
);

alter table misions
    add primary key (id);

create definer = root@localhost trigger count_mision_price
    before insert
    on misions
    for each row
begin
    declare service_price int;
    declare mision_price, launch_price float;
    set service_price = 0;
    set mision_price = 0;
    set launch_price = 0;

    set new.service_price = case
                                when new.service_type_id = 1 then (902300)
                                when new.service_type_id = 2 then (2301000)
                                when new.service_type_id = 3 then (1854300)
                                when new.service_type_id = 4 then (1226000)
                                when new.service_type_id = 5 then (626000)
                                when new.service_type_id = 6 then (2836000)
                                when new.service_type_id = 7 then (3565600)
                                when new.service_type_id = 8 then (3643000)
                                when new.service_type_id = 9 then (5462000)
                                when new.service_type_id = 10 then (10540400)
                                when new.service_type_id = 11 then (12304440)
                                when new.service_type_id = 12 then (23455000)
                                when new.service_type_id = 13 then (50144000)
        end;

    select launch_price from spacecrafts where id = new.craft_id into launch_price;
    set new.mision_price = new.service_price * new.duration + launch_price;

end;