INSERT INTO event (event_name, base_price, rating)
VALUES ('The Hateful Eight',100,'HIGH');

INSERT INTO schedule (event_id, auditorium, event_date)
VALUES (1,'Red','2016-03-18');

INSERT INTO user (user_id, email, password, full_name, birthday, user_role)
VALUES (1,'Taras_Pavliuchyn@epam.com','qwerty','Taras Pavliuchyn', '1990-03-18', 'CUSTOMER');

INSERT INTO ticket (ticket_id, purchased, booked, discounted, event_id, event_date, ticket_price, user_id)
VALUES (1,0,1,0,1,'2016-03-18', 100, 1);