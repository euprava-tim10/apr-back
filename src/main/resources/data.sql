INSERT INTO user
VALUES ('Administrator', 1, 'FACULTY_DEGREE', 'Jovana', 'Milisic',
        '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u', 'ADMINISTRATION',
        'ROLE_ADMIN', '1102997105029', 1);

INSERT INTO user
VALUES ('Administrator', 2, 'FACULTY_DEGREE', 'Mara', 'Maric',
        '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u', 'ADMINISTRATION',
        'ROLE_ADMIN', '1234567890123', 2);

INSERT INTO user
VALUES ('User', 3, 'SECONDARY_SCHOOL_DEGREE', 'Petar', 'Pertovic',
        '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u', 'OTHER',
        'ROLE_USER', '123412341234', null);
INSERT INTO user
VALUES ('User', 4, 'SECONDARY_SCHOOL_DEGREE', 'Nemanja', 'Murgaski',
        '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u', 'OTHER',
        'ROLE_USER', '123412341233', 3);

INSERT INTO company
values (1, 'Poslasticarnica Mika', '1', '1', '2022-06-11', '1', 'ACTIVE');
INSERT INTO company
values (2, 'DIP', '2', '2', '2022-06-11', '2', 'ACTIVE');
INSERT INTO company
values (3, 'Frizer mara', '3', '3', '2022-06-11', '3', 'DELETED');
INSERT INTO company
values (4, 'Bubi', '4', '4', '2022-06-11', '4', 'ACTIVE');



INSERT INTO job_advertisement
values (1, '2023-07-28', 'SECONDARY_SCHOOL_DEGREE', 'OTHER', '2023-06-01', 1);
INSERT INTO job_advertisement
values (2, '2023-07-30', 'FACULTY_DEGREE', 'ECONOMICS_FINANCE_AND_INSURANCE', '2023-06-01', 2);
INSERT INTO job_advertisement
values (3, '2023-07-18', 'SECONDARY_SCHOOL_DEGREE', 'ADMINISTRATION', '2023-06-02', 3);
INSERT INTO job_advertisement
values (4, '2023-07-22', 'FACULTY_DEGREE', 'MEDICINE', '2023-06-02', 4);
INSERT INTO job_advertisement
values (5, '2023-07-21', 'FACULTY_DEGREE', 'ELECTRICAL_ENGINEERING_AND_TELECOMMUNICATIONS', '2023-05-29', 3);

