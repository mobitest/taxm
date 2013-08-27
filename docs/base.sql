prompt PL/SQL Developer import file
prompt Created on 2013年8月12日 by Administrator
set feedback off
set define off
prompt Creating APP_USER...
create table APP_USER
(
  ID                  NUMBER(19) not null,
  ACCOUNT_EXPIRED     NUMBER(1) not null,
  ACCOUNT_LOCKED      NUMBER(1) not null,
  ADDRESS             VARCHAR2(150 CHAR),
  CITY                VARCHAR2(50 CHAR),
  COUNTRY             VARCHAR2(100 CHAR),
  POSTAL_CODE         VARCHAR2(15 CHAR),
  PROVINCE            VARCHAR2(100 CHAR),
  CREDENTIALS_EXPIRED NUMBER(1) not null,
  EMAIL               VARCHAR2(255 CHAR) not null,
  ACCOUNT_ENABLED     NUMBER(1),
  FIRST_NAME          VARCHAR2(50 CHAR) not null,
  LAST_NAME           VARCHAR2(50 CHAR) not null,
  PASSWORD            VARCHAR2(255 CHAR) not null,
  PASSWORD_HINT       VARCHAR2(255 CHAR),
  PHONE_NUMBER        VARCHAR2(255 CHAR),
  USERNAME            VARCHAR2(50 CHAR) not null,
  VERSION             NUMBER(10),
  WEBSITE             VARCHAR2(255 CHAR)
)
;
alter table APP_USER
  add primary key (ID);
alter table APP_USER
  add unique (EMAIL);
alter table APP_USER
  add unique (USERNAME);

prompt Creating ROLE...
create table ROLE
(
  ID          NUMBER(19) not null,
  DESCRIPTION VARCHAR2(64 CHAR),
  NAME        VARCHAR2(20 CHAR)
)
;
alter table ROLE
  add primary key (ID);

prompt Creating USER_ROLE...
create table USER_ROLE
(
  USER_ID NUMBER(19) not null,
  ROLE_ID NUMBER(19) not null
)
;
alter table USER_ROLE
  add primary key (USER_ID, ROLE_ID);
alter table USER_ROLE
  add constraint FK143BF46A4FD90D75 foreign key (ROLE_ID)
  references ROLE (ID);
alter table USER_ROLE
  add constraint FK143BF46AF503D155 foreign key (USER_ID)
  references APP_USER (ID);

prompt Deleting USER_ROLE...
delete from USER_ROLE;
commit;
prompt Deleting ROLE...
delete from ROLE;
commit;
prompt Deleting APP_USER...
delete from APP_USER;
commit;
prompt Loading APP_USER...
insert into APP_USER (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, ADDRESS, CITY, COUNTRY, POSTAL_CODE, PROVINCE, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, PHONE_NUMBER, USERNAME, VERSION, WEBSITE)
values (26, 0, 0, null, null, null, null, null, 0, 'as.com', 0, 'aaa', 'bbb', '2d4759cc129d6c8160ba0139cd36462e65804538', 'll', '23443', 'user3', 0, '23423424');
insert into APP_USER (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, ADDRESS, CITY, COUNTRY, POSTAL_CODE, PROVINCE, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, PHONE_NUMBER, USERNAME, VERSION, WEBSITE)
values (-1, 0, 0, null, 'Denver', 'US', '80210', 'CO', 0, 'matt_raible@yahoo.com', 1, 'Tomcat', 'User', 'b6b1f4781776979c0775c71ebdd8bdc084aac5fe', 'A male kitty.', null, 'user', 1, 'http://tomcat.apache.org');
insert into APP_USER (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, ADDRESS, CITY, COUNTRY, POSTAL_CODE, PROVINCE, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, PHONE_NUMBER, USERNAME, VERSION, WEBSITE)
values (-2, 0, 0, null, 'Denver', 'US', '80210', 'CO', 0, 'matt@raibledesigns.com', 1, 'Matt', 'Raible', 'a40546cc4fd6a12572828bb803380888ad1bfdab', 'Not a female kitty.', null, 'admin', 1, 'http://raibledesigns.com');
insert into APP_USER (ID, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, ADDRESS, CITY, COUNTRY, POSTAL_CODE, PROVINCE, CREDENTIALS_EXPIRED, EMAIL, ACCOUNT_ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, PASSWORD_HINT, PHONE_NUMBER, USERNAME, VERSION, WEBSITE)
values (1, 0, 0, null, null, null, null, null, 0, 'qq@11.com', 1, '张三', '四', 'dff53c888b2105782989c62f333feb40c7f09d10', 'numbers', null, 'someone', 0, 'a');
commit;
prompt 4 records loaded
prompt Loading ROLE...
insert into ROLE (ID, DESCRIPTION, NAME)
values (-1, 'Administrator role (can edit Users)', 'ROLE_ADMIN');
insert into ROLE (ID, DESCRIPTION, NAME)
values (-2, 'Default role for all Users', 'ROLE_USER');
commit;
prompt 2 records loaded
prompt Loading USER_ROLE...
insert into USER_ROLE (USER_ID, ROLE_ID)
values (-2, -1);
insert into USER_ROLE (USER_ID, ROLE_ID)
values (-1, -2);
insert into USER_ROLE (USER_ID, ROLE_ID)
values (1, -2);
insert into USER_ROLE (USER_ID, ROLE_ID)
values (26, -2);
commit;
prompt 1 records loaded


create table app_version
(id int not null,
num int not null,
title varchar2(100),
release_notes varchar2(800),
remark varchar2(400),
release_date date,
url varchar2(200),
must_update int,
primary key(id)
);

comment on column app_vesrion.must_update
  is '1-must update /0-may update';

insert into app_version(id,num,title,release_notes, remark, release_date, url, must_update)
values(1, 3, 'beta版','包括认证、自动更新、一户式查询、本地缓存等完整功能',null,sysdate,'taxm4a.apk',1);

commit; 
prompt 1 records loaded
set feedback on
set define on
prompt Done.
