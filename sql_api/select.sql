select * from member;
select * from project;
select * from project_team_member;
select * from task ;
-- where mid=1;

SELECT date_format(Now(), '%Y-%m-%d');

SELECT *,
DATE_FORMAT(FROM_UNIXTIME(created_time / 1000), '%Y-%m-%d %H:%i:%s') AS formatted_date
FROM  project where creator_user = 1
order by created_time desc;

select *, date_format(created_time, '%Y-%m-%d') from project where creator_user = 1;

select * from member where username = "it1";
update member set mname = "UX현빈" where username = "hbin1028";


select * from project_team_member where pid=8;

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
SELECT   t2.title AS title,  t2.description AS description,  t2.start_date As startDate,  t2.end_date AS endDate,  t2.creator_user AS creatorUser,  
t2.status,  t1.pid,  t1.mid  
FROM project_team_member t1  LEFT JOIN project t2 ON t1.pid = t2.pid   WHERE t1.mid = 6;


truncate project;

SELECT   t2.title AS title,  t2.description AS description,  t2.start_date As startDate,  
t2.end_date AS endDate,  t2.creator_user AS creatorUser,  t2.status,  t2.image,  t1.pid,  t1.mid  
FROM project_team_member t1  LEFT JOIN project t2 ON t1.pid = t2.pid   WHERE t1.mid = 
(select mid from member where username = 'kiara');

select * from project where pid = 10;
select * from project_team_member where pid = 10;
delete from project_team_member where pid = 10;

SELECT   t2.title AS title,  t2.description AS description,  t2.start_date As startDate,  
t2.end_date AS endDate,  t2.creator_user AS creatorUser,  t2.status,  t2.image,  t1.pid,  t1.mid  
FROM project_team_member t1  LEFT JOIN project t2 ON t1.pid = t2.pid   
WHERE t1.mid = 2  ORDER BY t2.created_time desc;


select p1_0.pid,p1_0.created_time,p1_0.creator_user,p1_0.description,p1_0.end_date,p1_0.image,p1_0.start_date,p1_0.status,p1_0.title from project p1_0 where p1_0.creator_user=2
 order by p1_0.created_time desc;
 
SELECT  t1.tid AS tid,  t1.title AS title,  t1.description AS description,  t1.start_date AS startDate,  t1.end_date AS endDate,  t1.mid AS mid,  t1.status AS status,  
t2.username AS username,  t2.mname AS mname  FROM Task t1  
LEFT JOIN Member t2 ON t1.mid = t2.mid  WHERE t1.project_pid = 2;

select * from task;
select * from task where project_pid = 2;


SELECT   t2.title AS title,  t2.description AS description,  t2.start_date As startDate,  t2.end_date AS endDate,  t2.creator_user AS creatorUser,  t2.status,  t2.image,  t1.pid,  t1.mid  FROM project_team_member t1  LEFT JOIN project t2 ON t1.pid = t2.pid   WHERE t1.mid = ?  ORDER BY t2.created_time desc

select p1_0.pid,p1_0.created_time,p1_0.creator_user,p1_0.description,p1_0.end_date,p1_0.image,p1_0.start_date,p1_0.status,p1_0.title from project p1_0 where p1_0.creator_user=? order by p1_0.created_time desc limit 0,6