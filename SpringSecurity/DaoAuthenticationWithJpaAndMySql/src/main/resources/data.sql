use db_1;
insert into jpa_users(username, password, enabled)
values('asha', '12345', true);
insert into jpa_users(username, password, enabled)
values('mani', '12345', true);

insert into jpa_authorities(username, authority)
values('asha', 'ROLE_USER');
insert into jpa_authorities(username, authority)
values('mani', 'ROLE_ADMIN');