INSERT INTO `member` (`email`, `mname`, `secret`, `username`)
VALUES
  ('user1@google.com', 'User One', '$2a$12$CP.lvkxMK0AE/wlpRXsJb.PadHH2XUfUJdc.JvKFz.DL5LxOEEbw.', 'user1'),
  ('user2@google.com', 'User Two', '$2a$12$CP.lvkxMK0AE/wlpRXsJb.PadHH2XUfUJdc.JvKFz.DL5LxOEEbw.', 'user2'),
  ('user3@google.com', 'User Three', '$2a$12$CP.lvkxMK0AE/wlpRXsJb.PadHH2XUfUJdc.JvKFz.DL5LxOEEbw.', 'user3'),
  ('user4@google.com', 'User Four', '$2a$12$CP.lvkxMK0AE/wlpRXsJb.PadHH2XUfUJdc.JvKFz.DL5LxOEEbw.', 'user4'),
  ('user5@google.com', 'User Five', '$2a$12$CP.lvkxMK0AE/wlpRXsJb.PadHH2XUfUJdc.JvKFz.DL5LxOEEbw.', 'user5'),
  ('kiara@google.com', 'User six', '$2a$12$CP.lvkxMK0AE/wlpRXsJb.PadHH2XUfUJdc.JvKFz.DL5LxOEEbw.', 'kiara');

INSERT INTO `project` (`created_time`, `creator_user`, `description`, `end_date`, `image`, `start_date`, `status`, `title`)
VALUES
  (UNIX_TIMESTAMP('2023-08-01'), 1, 'Sample project 1', '2023-08-31', NULL, '2023-08-01', 'In Progress', 'Project A'),
  (UNIX_TIMESTAMP('2023-08-02'), 2, 'Sample project 2', '2023-08-15', NULL, '2023-08-01', 'Planning', 'Project B'),
  (UNIX_TIMESTAMP('2023-08-03'), 1, 'Sample project 3', '2023-08-30', NULL, '2023-08-10', 'Completed', 'Project C'),
  (UNIX_TIMESTAMP('2023-08-04'), 3, 'Sample project 4', '2023-09-15', NULL, '2023-09-01', 'In Progress', 'Project D'),
  (UNIX_TIMESTAMP('2023-08-05'), 2, 'Sample project 5', '2023-09-30', NULL, '2023-09-10', 'Planning', 'Project E'),
  (UNIX_TIMESTAMP('2023-08-06'), 4, 'Sample project 6', '2023-10-15', NULL, '2023-10-01', 'Completed', 'Project F'),
  (UNIX_TIMESTAMP('2023-08-07'), 3, 'Sample project 7', '2023-10-31', NULL, '2023-10-10', 'In Progress', 'Project G'),
  (UNIX_TIMESTAMP('2023-08-08'), 1, 'Sample project 8', '2023-11-15', NULL, '2023-11-01', 'Planning', 'Project H'),
  (UNIX_TIMESTAMP('2023-08-09'), 4, 'Sample project 9', '2023-11-30', NULL, '2023-11-10', 'Completed', 'Project I'),
  (UNIX_TIMESTAMP('2023-08-10'), 2, 'Sample project 10', '2023-12-15', NULL, '2023-12-01', 'In Progress', 'Project J');
  
  
  
