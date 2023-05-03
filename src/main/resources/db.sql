create table "Citizen"
(
    DNI     CHARACTER VARYING not null,
    NAME    CHARACTER VARYING,
    SURNAME CHARACTER VARYING,
    PHONE   CHARACTER VARYING,
    EMAIL   CHARACTER VARYING,
    constraint "Citizen_pk"
        primary key (DNI)
);

create table "Officer"
(
    ID       BIGINT auto_increment,
    NAME     CHARACTER VARYING,
    SURNAME  CHARACTER VARYING,
    CATEGORY CHARACTER VARYING,
    EMAIL    CHARACTER VARYING,
    constraint "Officer_pk"
        primary key (ID)
);

create table "PublicService"
(
    ID          BIGINT auto_increment,
    DEPARTMENT  CHARACTER VARYING,
    INSTITUTION CHARACTER VARYING,
    constraint "PublicService_pk"
        primary key (ID)
);

create table "Claim"
(
    NUMBER                 BIGINT not null,
    DESCRIPTION            CHARACTER VARYING,
    "dateTimeClaimedEvent" DATE,
    "dateTimeFiling"       CHARACTER VARYING,
    STATUS                 CHARACTER VARYING,
    RESOLUTION             CHARACTER VARYING,
    OFFICER                BIGINT,
    CITIZEN                BIGINT,
    "publicService"        BIGINT,
    constraint "Claim_pk"
        primary key (NUMBER),
    constraint "CLAIM_PublicService__FK"
        foreign key (NUMBER) references "PublicService",
    constraint "Claim_Officer_fk"
        foreign key (OFFICER) references "Officer",
    constraint "Claim__Citizen_fk"
        foreign key (CITIZEN) references "Citizen"
);

create table "Access"
(
    ID         BIGINT auto_increment,
    "dateTime" DATE,
    "toWhat"   BIGINT not null,
    WHO        BIGINT,
    constraint "Access_pk"
        primary key (ID),
    constraint "ACCESS_Officer__FK"
        foreign key (ID) references "Officer",
    constraint "ACCESS__Claim_FK"
        foreign key (ID) references "Claim"
);

create table "Action"
(
    ID          BIGINT auto_increment,
    DESCRIPTION CHARACTER VARYING,
    "dateTime"  DATE,
    CLAIM       BIGINT,
    constraint "Action_pk"
        primary key (ID),
    constraint "ACTION_Claim__FK"
        foreign key (CLAIM) references "Claim"
);

