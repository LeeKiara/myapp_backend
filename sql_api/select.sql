select * from member;
select * from project;
select * from project_team_member;
select * from task;

select * from project_team_member where pid in (
select pid from project where creator_user in (
   select mid from member where username = "user1"
   ));
   
select * from project_team_member where mid = 6;

-- update member set secret = '$2a$12$CP.lvkxMK0AE/wlpRXsJb.PadHH2XUfUJdc.JvKFz.DL5LxOEEbw.' where mid = 16;

select t1.title , t2.*
from project t1
left join project_team_member t2 on t1.creator_user = t2.mid;

select *
from project_team_member t1
left join project t2 on t1.mid = t2.creator_user;


select * from member where mid=1;
-- update member set username='pm-user3' where mid=3;

select * from project where pid=15;

select t1.tid,
        t1.title ,
        t1.description,
        t1.start_date,
        t1.end_date,
        t1.mid,
        t1.project_pid,
        t1.status,
        t1.created_time,
        t2.username
from task t1
left join member t2 on t1.mid = t2.mid
and project_pid = 8;

SELECT t.* FROM Task t 
         LEFT JOIN Member m ON t.mid = m.mid
         WHERE m.mid = 18;
         
         
select t1_0.tid,t1_0.created_time,t1_0.description,t1_0.end_date,t1_0.mid,t1_0.pid,t1_0.start_date,t1_0.status,t1_0.title,m1_0.mid,m1_0.email,m1_0.mname,m1_0.secret,m1_0.username 
from task t1_0 left join member m1_0 on t1_0.mid=m1_0.mid where t1_0.pid=15;       


select t1_0.tid,t1_0.created_time,t1_0.description,t1_0.end_date,t1_0.mid,t1_0.pid,t1_0.start_date,t1_0.status,t1_0.title,m1_0.mid,m1_0.email,m1_0.mname,m1_0.secret,m1_0.username 
from task t1_0 left join member m1_0 on t1_0.mid=m1_0.mid where t1_0.pid=15;  


SELECT * FROM Member m 
            LEFT JOIN project_team_member ptm ON m.mid = ptm.mid 
            WHERE ptm.pid = 15;
            
            
select * from task ;   
select count(*) AS countTask from task where project_pid = 8;   

 SELECT
        t1.title AS title,
        t1.description AS description,
        t1.start_date AS startdate,
        DATE_FORMAT(t1.start_date, '%Y-%m-%d') AS startDateFormat,
        t2.username AS username  
    FROM
        Task t1  
    LEFT JOIN
        Member t2 
            ON t1.mid = t2.mid  
    WHERE
        t1.project_pid = 8;

SELECT 
t1.title,
t1.description
FROM Project t1
LEFT JOIN Project_team_member t2 on t1.pid = t2.pid
AND t2.mid = 6;

SELECT   t2.title AS title,  t2.description AS description,  t2.start_date,  t2.end_date,  t2.creator_user,  t2.status,  t1.pid  
FROM project_team_member t1  LEFT JOIN project t2 ON t1.pid = t2.pid   WHERE t1.mid = 6;