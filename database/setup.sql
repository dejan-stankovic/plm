create database plm;
create user 'plm_user'@'localhost' identified by 'plm_password';
grant all on plm.* to 'plm_user'@'localhost';
