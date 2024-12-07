SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE users;
SET REFERENTIAL_INTEGRITY TRUE;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;
--select next value for users_seq
--insert into users (id, full_name, password) values (1000001, "ankit sood", "ankit.sood");

insert into users (created_at, email, full_name, password, updated_at, id) values ("Sat Dec 07 15:15:25 IST 2023", "bar@bar.com", "Bar Bar", "Foo Bar", "Sat Dec 07 15:15:25 IST 2023", 1000001);