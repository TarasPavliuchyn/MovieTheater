INSERT INTO event (event_name, base_price, rating)
VALUES ('The Hateful Eight',100,'HIGH'),
       ('Finding Dory',150,'HIGH') ,
       ('Doom',80,'LOW');

INSERT INTO schedule (event_id, auditorium, event_date)
VALUES (1,'Red','2016-03-18'),
       (2,'Red','2016-03-18');

INSERT INTO user (user_id, email, password, full_name, birthday, user_role)
VALUES (1,'Taras_Pavliuchyn@epam.com','qwerty','Taras Pavliuchyn', '1990-03-18', 'CUSTOMER'),
       (2,'test@epam.com','qwerty','test', '1990-03-18', 'CUSTOMER');

INSERT INTO ticket (ticket_id, purchased, booked, discounted, event_id, event_date, seat)
VALUES (1,0,1,0,1,'2016-03-18',25),
       (2,0,1,0,2,'2016-03-18',12),
       (3,0,0,0,2,'2016-03-18',10),
       (4,0,0,0,2,'2016-03-18',11),
       (5,0,0,0,2,'2016-03-18',12),
       (6,0,0,0,2,'2016-03-18',13),
       (7,0,0,0,2,'2016-03-18',14),
       (8,0,0,0,2,'2016-03-18',15),
       (9,0,1,0,3,'2016-03-18',12);