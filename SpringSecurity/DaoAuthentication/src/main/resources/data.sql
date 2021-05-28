insert into users(username, password, enabled)
values('asha', '12345', true);
insert into users(username, password, enabled)
values('mani', '12345', true);

insert into authorities(username, authority)
values('asha', 'ROLE_USER');
insert into authorities(username, authority)
values('mani', 'ROLE_ADMIN');

/*--use my_users if custom table for authentication needed.*/
insert into my_users(username, password, enabled)
values('my_asha', '12345', true);
insert into my_users(username, password, enabled)
values('my_mani', '12345', true);

insert into my_authorities(username, authority)
values('my_asha', 'ROLE_USER');
insert into my_authorities(username, authority)
values('my_mani', 'ROLE_ADMIN');