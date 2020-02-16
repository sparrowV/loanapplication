-- twinno_manager
insert into users(user_name,password,enabled) values('manager','$2y$10$UgcIJF/l6QLXGZ77vK4f6.416u1ls9asZ5u.Rc8x8oMmiMkv.4DY2',true);
insert into user_authorities(user_id,authority_id) values (1,1);
insert into authorities(authority_name,active) values
('ROLE_MANAGER',true),
('ROLE_OPERATOR',true),
('ROLE_CLIENT',true);