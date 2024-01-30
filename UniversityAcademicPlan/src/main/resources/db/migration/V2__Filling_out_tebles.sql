INSERT INTO users (nick_name, password, user_type) VALUES ('user1', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_TEACHER');
INSERT INTO users (nick_name, password, user_type) VALUES ('user2', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_TEACHER');
INSERT INTO users (nick_name, password, user_type) VALUES ('user3', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_TEACHER');
INSERT INTO users (nick_name, password, user_type) VALUES ('user4', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user5', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user6', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user7', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user8', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user9', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user10', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('admin', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_ADMIN');
INSERT INTO users (nick_name, password, user_type) VALUES ('stuff', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUFF');
INSERT INTO users (nick_name, password, user_type) VALUES ('user13', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user14', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user15', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user16', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_STUDENT');
INSERT INTO users (nick_name, password, user_type) VALUES ('user17', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_TEACHER');
INSERT INTO users (nick_name, password, user_type) VALUES ('user18', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_TEACHER');
INSERT INTO users (nick_name, password, user_type) VALUES ('user19', '$2a$10$P.Gwfw2tdh5amkQg/MaVpOsUFwkPxJwFi1GbF07F6PvqE0ToIKwJy', 'ROLE_TEACHER');
----------------------------------------------------------------------------------
INSERT INTO groups (name) VALUES ('gr1');
INSERT INTO groups (name) VALUES ('gr2');
INSERT INTO groups (name) VALUES ('gr3');
INSERT INTO groups (name) VALUES ('gr4');
--------------------------------------------------------
INSERT INTO location (name) VALUES ('auditory1');
INSERT INTO location (name) VALUES ('auditory2');
INSERT INTO location (name) VALUES ('auditory3');
INSERT INTO location (name) VALUES ('auditory4');
--------------------------------------------------------
INSERT INTO course (name, location_id) VALUES ('c1', 1);
INSERT INTO course (name, location_id) VALUES ('c2', 2);
INSERT INTO course (name, location_id) VALUES ('c3', 3);
INSERT INTO course (name, location_id) VALUES ('c4', 4);
INSERT INTO course (name, location_id) VALUES ('c5', 1);
INSERT INTO course (name, location_id) VALUES ('c6', 2);
INSERT INTO course (name, location_id) VALUES ('c7', 3);
-----------------------------------------------------------------
INSERT INTO lecture (name, course_id) VALUES ('c1-l1', 1);
INSERT INTO lecture (name, course_id) VALUES ('c1-l2', 1);
INSERT INTO lecture (name, course_id) VALUES ('c1-l3', 1);
INSERT INTO lecture (name, course_id) VALUES ('c1-l4', 1);
INSERT INTO lecture (name, course_id) VALUES ('c1-l5', 1);
INSERT INTO lecture (name, course_id) VALUES ('c1-l6', 1);
INSERT INTO lecture (name, course_id) VALUES ('c1-l7', 1);

INSERT INTO lecture (name, course_id) VALUES ('c2-l1', 2);
INSERT INTO lecture (name, course_id) VALUES ('c2-l2', 2);
INSERT INTO lecture (name, course_id) VALUES ('c2-l3', 2);
INSERT INTO lecture (name, course_id) VALUES ('c2-l4', 2);
INSERT INTO lecture (name, course_id) VALUES ('c2-l5', 2);
INSERT INTO lecture (name, course_id) VALUES ('c2-l6', 2);
INSERT INTO lecture (name, course_id) VALUES ('c2-l7', 2);

INSERT INTO lecture (name, course_id) VALUES ('c3-l1', 3);
INSERT INTO lecture (name, course_id) VALUES ('c3-l2', 3);
INSERT INTO lecture (name, course_id) VALUES ('c3-l3', 3);
INSERT INTO lecture (name, course_id) VALUES ('c3-l4', 3);
INSERT INTO lecture (name, course_id) VALUES ('c3-l5', 3);
INSERT INTO lecture (name, course_id) VALUES ('c3-l6', 3);
INSERT INTO lecture (name, course_id) VALUES ('c3-l7', 3);

INSERT INTO lecture (name, course_id) VALUES ('c4-l1', 4);
INSERT INTO lecture (name, course_id) VALUES ('c4-l2', 4);
INSERT INTO lecture (name, course_id) VALUES ('c4-l3', 4);
INSERT INTO lecture (name, course_id) VALUES ('c4-l4', 4);
INSERT INTO lecture (name, course_id) VALUES ('c4-l5', 4);
INSERT INTO lecture (name, course_id) VALUES ('c4-l6', 4);
INSERT INTO lecture (name, course_id) VALUES ('c4-l7', 4);

INSERT INTO lecture (name, course_id) VALUES ('c5-l1', 5);
INSERT INTO lecture (name, course_id) VALUES ('c5-l2', 5);
INSERT INTO lecture (name, course_id) VALUES ('c5-l3', 5);
INSERT INTO lecture (name, course_id) VALUES ('c5-l4', 5);
INSERT INTO lecture (name, course_id) VALUES ('c5-l5', 5);
INSERT INTO lecture (name, course_id) VALUES ('c5-l6', 5);
INSERT INTO lecture (name, course_id) VALUES ('c5-l7', 5);

INSERT INTO lecture (name, course_id) VALUES ('c6-l1', 6);
INSERT INTO lecture (name, course_id) VALUES ('c6-l2', 6);
INSERT INTO lecture (name, course_id) VALUES ('c6-l3', 6);
INSERT INTO lecture (name, course_id) VALUES ('c6-l4', 6);
INSERT INTO lecture (name, course_id) VALUES ('c6-l5', 6);
INSERT INTO lecture (name, course_id) VALUES ('c6-l6', 6);
INSERT INTO lecture (name, course_id) VALUES ('c6-l7', 6);

INSERT INTO lecture (name, course_id) VALUES ('c7-l1', 7);
INSERT INTO lecture (name, course_id) VALUES ('c7-l2', 7);
INSERT INTO lecture (name, course_id) VALUES ('c7-l3', 7);
INSERT INTO lecture (name, course_id) VALUES ('c7-l4', 7);
INSERT INTO lecture (name, course_id) VALUES ('c7-l5', 7);
INSERT INTO lecture (name, course_id) VALUES ('c7-l6', 7);
INSERT INTO lecture (name, course_id) VALUES ('c7-l7', 7);
--------------------------------------------------------------------
INSERT INTO admin (first_name, last_name, id) VALUES ('Ury', 'Bodnar', 11);
INSERT INTO stuff (first_name, last_name, id) VALUES ('Suff-FN1', 'Stuff-LN-1', 12);
------------------------------------------------------------------------
INSERT INTO student (first_name, last_name, id, groups_id) VALUES ('fn1', 'ln1', 4, 1);
INSERT INTO student (first_name, last_name, id, groups_id) VALUES ('fn2', 'ln2', 5, 2);
INSERT INTO student (first_name, last_name, id, groups_id) VALUES ('fn3', 'ln3', 6, 3);
INSERT INTO student (first_name, last_name, id, groups_id) VALUES ('fn4', 'ln4', 7, 4);
INSERT INTO student (first_name, last_name, id, groups_id) VALUES ('fn5', 'ln5', 8, 1);
INSERT INTO student (first_name, last_name, id, groups_id) VALUES ('fn6', 'ln6', 9, 2);
INSERT INTO student (first_name, last_name, id, groups_id) VALUES ('fn7', 'ln7', 10, 3);
INSERT INTO student (first_name, last_name, id) VALUES ('----', '----', 13);
INSERT INTO student (first_name, last_name, id) VALUES ('----', '----', 14);
INSERT INTO student (first_name, last_name, id) VALUES ('----', '----', 15);
INSERT INTO student (first_name, last_name, id) VALUES ('----', '----', 16);
---------------------------------------------------------------------
INSERT INTO teacher (first_name, last_name, id) VALUES ('T-fn1', 'T-ln1', 1);
INSERT INTO teacher (first_name, last_name, id) VALUES ('T-fn2', 'T-ln2', 2);
INSERT INTO teacher (first_name, last_name, id) VALUES ('T-fn3', 'T-ln3', 3);
INSERT INTO teacher (first_name, last_name, id) VALUES ('----', '----', 17);
INSERT INTO teacher (first_name, last_name, id) VALUES ('----', '----', 18);
INSERT INTO teacher (first_name, last_name, id) VALUES ('----', '----', 19);
-----------------------------------------------------------------------------
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('MONDAY', '8:00', '9:40', 1);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('MONDAY', '10:00', '11:40', 2);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('MONDAY', '12:00', '13:40', 3);

INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('TUESDAY', '8:00', '9:40', 4);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('TUESDAY', '10:00', '11:40', 5);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('TUESDAY', '12:00', '13:40', 6);

INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('WEDNESDAY', '8:00', '9:40', 7);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('WEDNESDAY', '10:00', '11:40', 1);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('WEDNESDAY', '12:00', '13:40', 2);

INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('THURSDAY', '8:00', '9:40', 3);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('THURSDAY', '10:00', '11:40', 4);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('THURSDAY', '12:00', '13:40', 5);

INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('FRIDAY', '8:00', '9:40', 6);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('FRIDAY', '10:00', '11:40', 7);
INSERT INTO schedule (day_of_week, start_time, end_time, course_id) VALUES ('FRIDAY', '12:00', '13:40', 1);
--------------------------------------------------------------------------------------------
INSERT INTO groups_course (groups_id, course_id) VALUES (1, 1);
INSERT INTO groups_course (groups_id, course_id) VALUES (1, 2);
INSERT INTO groups_course (groups_id, course_id) VALUES (1, 3);
INSERT INTO groups_course (groups_id, course_id) VALUES (2, 4);
INSERT INTO groups_course (groups_id, course_id) VALUES (2, 5);
INSERT INTO groups_course (groups_id, course_id) VALUES (2, 6);
INSERT INTO groups_course (groups_id, course_id) VALUES (3, 7);
INSERT INTO groups_course (groups_id, course_id) VALUES (3, 1);
INSERT INTO groups_course (groups_id, course_id) VALUES (3, 2);
INSERT INTO groups_course (groups_id, course_id) VALUES (3, 3);
INSERT INTO groups_course (groups_id, course_id) VALUES (4, 4);
INSERT INTO groups_course (groups_id, course_id) VALUES (4, 5);
INSERT INTO groups_course (groups_id, course_id) VALUES (4, 6);
-----------------------------------------------------------------------------------------------
INSERT INTO course_teacher (course_id, teacher_id) VALUES (1, 3);
INSERT INTO course_teacher (course_id, teacher_id) VALUES (4, 3);
INSERT INTO course_teacher (course_id, teacher_id) VALUES (2, 2);
INSERT INTO course_teacher (course_id, teacher_id) VALUES (5, 2);
INSERT INTO course_teacher (course_id, teacher_id) VALUES (3, 2);
INSERT INTO course_teacher (course_id, teacher_id) VALUES (5, 1);
INSERT INTO course_teacher (course_id, teacher_id) VALUES (6, 1);
INSERT INTO course_teacher (course_id, teacher_id) VALUES (7, 1);
------------------------------------------------------------------------------------------------
