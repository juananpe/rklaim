create table PUBLIC.CITIZEN
(
    DNI     CHARACTER VARYING(255) not null
        primary key,
    EMAIL   CHARACTER VARYING(255),
    NAME    CHARACTER VARYING(255),
    PHONE   CHARACTER VARYING(255),
    SURNAME CHARACTER VARYING(255)
);

create table PUBLIC.OFFICER
(
    ID       BIGINT not null
        primary key,
    CATEGORY SMALLINT,
    EMAIL    CHARACTER VARYING(255),
    NAME     CHARACTER VARYING(255),
    SURNAME  CHARACTER VARYING(255)
);

create table PUBLIC.PUBLICSERVICE
(
    ID          BIGINT not null
        primary key,
    DEPARTMENT  CHARACTER VARYING(255),
    INSTITUTION CHARACTER VARYING(255)
);

create table PUBLIC.CLAIM
(
    NUMBER               BIGINT not null
        primary key,
    DATETIMECLAIMEDEVENT TIMESTAMP,
    DATETIMEFILING       TIMESTAMP,
    DESCRIPTION          CHARACTER VARYING(255),
    RESOLUTION           SMALLINT,
    STATUS               SMALLINT,
    CITIZEN_DNI          CHARACTER VARYING(255),
    OFFICER_ID           BIGINT,
    PUBLICSERVICE_ID     BIGINT,
    constraint FK1KFNDWM7493GW7BG5HLXLLW99
        foreign key (PUBLICSERVICE_ID) references PUBLIC.PUBLICSERVICE,
    constraint FKCR932LJUM069G5QRL1L7BOJBK
        foreign key (OFFICER_ID) references PUBLIC.OFFICER,
    constraint FKFSGLF7PM7B9OMS2GBSQSM93H2
        foreign key (CITIZEN_DNI) references PUBLIC.CITIZEN
);

create table PUBLIC.ACCESS
(
    ID            BIGINT not null
        primary key,
    DATETIME      TIMESTAMP,
    TOWHAT_NUMBER BIGINT,
    WHO_ID        BIGINT,
    constraint FK1GKKCGP9WFTMFC99C39YA4D1G
        foreign key (WHO_ID) references PUBLIC.OFFICER,
    constraint FKSP5B4H8G8B8PHOCAK8NJQEQOR
        foreign key (TOWHAT_NUMBER) references PUBLIC.CLAIM
);

create table PUBLIC.ACTION
(
    ID           BIGINT not null
        primary key,
    DATETIME     TIMESTAMP,
    DESCRIPTION  CHARACTER VARYING(255),
    CLAIM_NUMBER BIGINT,
    constraint FK7NKDGPR0T2MQG7RHSUX7K95LJ
        foreign key (CLAIM_NUMBER) references PUBLIC.CLAIM
);

create table PUBLIC.CITIZEN_CLAIM
(
    CITIZEN_DNI   CHARACTER VARYING(255) not null,
    CLAIMS_NUMBER BIGINT                 not null
        constraint UK_FE98RI361FA58ROT7QL6KXQL6
            unique,
    constraint FKH02GAX18YH2WHMD2D43L6SOUH
        foreign key (CLAIMS_NUMBER) references PUBLIC.CLAIM,
    constraint FKP13Q2G0C7CIYON61IX708XY0A
        foreign key (CITIZEN_DNI) references PUBLIC.CITIZEN
);

create table PUBLIC.CLAIM_ACCESS
(
    CLAIM_NUMBER BIGINT not null,
    ACCESSES_ID  BIGINT not null
        constraint UK_RK3Y0I6HD9LJRYSKY1TLW2WOM
            unique,
    constraint FK1IYO1MMF4TIP8UXGMYKSL3ELV
        foreign key (ACCESSES_ID) references PUBLIC.ACCESS,
    constraint FKHDYCBAY8FRK8BLVYN0PB69A14
        foreign key (CLAIM_NUMBER) references PUBLIC.CLAIM
);

create table PUBLIC.CLAIM_ACTION
(
    CLAIM_NUMBER BIGINT not null,
    ACTIONS_ID   BIGINT not null
        constraint UK_ISLEUG9D06WLBNSWXUF5IN1NI
            unique,
    constraint FK20POBLSDBL9UN6XE9UMLTBJRO
        foreign key (ACTIONS_ID) references PUBLIC.ACTION,
    constraint FKDAH8S7POY7U71F2ITU87BHEYD
        foreign key (CLAIM_NUMBER) references PUBLIC.CLAIM
);

create table PUBLIC.OFFICER_ACCESS
(
    OFFICER_ID    BIGINT not null,
    ACCESSLIST_ID BIGINT not null
        constraint UK_QH01653LU7FROVGJI1QJAQNEF
            unique,
    constraint FKHAQVB1M3SUQBSSPG2BRLLFK5P
        foreign key (ACCESSLIST_ID) references PUBLIC.ACCESS,
    constraint FKYKYW8NMYJIGR8OROT0JTMAPA
        foreign key (OFFICER_ID) references PUBLIC.OFFICER
);

create table PUBLIC.OFFICER_CLAIM
(
    OFFICER_ID        BIGINT not null,
    ASSIGNEDTO_NUMBER BIGINT not null
        constraint UK_BA9GJRP6GEP5P7LDE53EAIRIE
            unique,
    constraint FK1UV0GOC4JVHFOOFCPWB7PQE9E
        foreign key (ASSIGNEDTO_NUMBER) references PUBLIC.CLAIM,
    constraint FKABDE8OINYNDA08BKWUOE8MAW5
        foreign key (OFFICER_ID) references PUBLIC.OFFICER
);

create table PUBLIC.PUBLICSERVICE_CLAIM
(
    PUBLICSERVICE_ID BIGINT not null,
    CLAIMS_NUMBER    BIGINT not null
        constraint UK_7WHYMY36TBTVQ13LO9A13HNKC
            unique,
    constraint FK7PWTIIU1B7MEWX7XC9LL4QVG6
        foreign key (CLAIMS_NUMBER) references PUBLIC.CLAIM,
    constraint FKPWK2M7Y0LCNCU3BKT8C7FN5JX
        foreign key (PUBLICSERVICE_ID) references PUBLIC.PUBLICSERVICE
);

