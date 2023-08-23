select * from member;
select * from project;
select * from project_team_member;
select * from task;


select t1.title , t2.*
from project t1
left join project_team_member t2 on t1.creator_user = t2.mid;

select *
from project_team_member t1
left join project t2 on t1.mid = t2.creator_user;

select * from member where username = 'user5';
update member set email = 'user5@gmail.com' where username = 'user5';


