create table ACCOUNT (
  ID BIGINT auto_increment PRIMARY KEY,
  USERNAME varchar(100) not null unique,
  PASSWORD CHAR(60) not null,
);

create table URL(
    ID BIGINT auto_increment PRIMARY KEY,
    URL varchar not null unique,
    SHORT_URL varchar not null unique,
    REDIRECT_NUMBER INT not null,
    VISITS BIGINT not null,
    ACCOUNT_ID BIGINT REFERENCES ACCOUNT
);

INSERT into URL values (DEFAULT ,'test','shortUrl',301,0,null)