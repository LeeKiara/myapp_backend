-- 1. 외래키 옵션해제
SET foreign_key_checks = 0;
SET foreign_key_checks = 1;


truncate memeber;
truncate task;
truncate project;
truncate project_team_member;

drop table member;
drop table task;
drop table project;
drop table project_team_member;

select * from member;
select * from project;
select * from project_team_member;
select * from task;


