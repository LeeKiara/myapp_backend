select * from user;
select * from department;
select * from team;

select user.*,
department.* from user, department
where user.depart_code = department.depart_code;

select * from user where depart_code = "101";
select * from department where depart_code = "101";


-- $2a$12$rJXe178BhS85g42F5F9LKO3gCempit4Nog5YE0tNWldqCzxaZjBq6

CREATE TABLE `user` (
  `user_no` bigint NOT NULL AUTO_INCREMENT,
  `depart_code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 
truncate user;
--
INSERT INTO `user` (`depart_code`, `email`, `name`, `secret`, `user_id`)
VALUES
  (101, 'kiara@example.com', 'kiara Lee', '$2a$12$rJXe178BhS85g42F5F9LKO3gCempit4Nog5YE0tNWldqCzxaZjBq6', 'kiara'),
  (102, 'jane@example.com', 'Jane Smith', '$2a$12$rJXe178BhS85g42F5F9LKO3gCempit4Nog5YE0tNWldqCzxaZjBq6', 'janesmith'),
  (103, 'alice@example.com', 'Alice Johnson', '$2a$12$rJXe178BhS85g42F5F9LKO3gCempit4Nog5YE0tNWldqCzxaZjBq6', 'alicejohnson'),
  (101, 'bob@example.com', 'Bob Anderson', '$2a$12$rJXe178BhS85g42F5F9LKO3gCempit4Nog5YE0tNWldqCzxaZjBq6', 'bobanderson'),
  (101, 'eva@example.com', 'Eva Williams', '$2a$12$rJXe178BhS85g42F5F9LKO3gCempit4Nog5YE0tNWldqCzxaZjBq6', 'evawilliams');
  
INSERT INTO `department` (`depart_code`, `depart_name`)
VALUES
  ('101', 'IT Department'),
  ('102', 'HR Department'),
  ('103', 'Finance Department'),
  ('104', 'Marketing Department'),
  ('105', 'Admin Department'),
  ('106', 'Sales Department'),
  ('107', 'Research Department'),
  ('108', 'Customer Service Department'),
  ('109', 'Production Department'),
  ('110', 'Design Department'),
  ('111', 'Quality Control Department'),
  ('112', 'Logistics Department'),
  ('113', 'Public Relations Department'),
  ('114', 'Legal Department'),
  ('115', 'Engineering Department'),
  ('116', 'Support Department'),
  ('117', 'Training Department'),
  ('118', 'Purchasing Department'),
  ('119', 'Project Management Department'),
  ('120', 'Testing Department');
  

  
  

