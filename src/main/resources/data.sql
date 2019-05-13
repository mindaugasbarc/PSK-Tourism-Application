-- TEST DATA WHEN SCHEMA IS CREATED --

-- CREATE office --
INSERT INTO office (id, name, address) VALUES
    (1, 'Chicago office', '343 W. Erie St. Suite 600, Chicago, IL 60654, United States'),
    (2, 'Toronto office', '36 Toronto Street Suite 260, Toronto, Ontario M5C 2C5, Canada'),
    (3, 'London office', '8 Devonshire Square, London EC2M 4PL, United Kingdom'),
    (4, 'Kaunas office', '11d. Juozapaviciaus pr., Kaunas, LT-45252, Lithuania'),
    (5, 'Vilnius office', '135 Zalgirio g., Vilnius, LT-08217, Lithuania');

-- CREATE users --
INSERT INTO users (id, fullname, email, role) VALUES
    (1, 'Admin', 'admin@devbridge.com', 'ADVISOR'),
    (2, 'Markas Gedminas', 'markas.gedinimas@devbridge.com', 'DEFAULT'),
    (3, 'Robert Požarickij', 'robertp@devbridge.com', 'DEFAULT'),
    (4, 'Simona Mockute', 'simona.mockute@devbridge.com', 'DEFAULT'),
    (5, 'Lina Pakalkaite', 'lina.pakalkaite@devbridge.com', 'ADVISOR'),
    (6, 'Justinas Viršilas', 'justinasv@devbridge.com', 'DEFAULT'),
    (7, 'Michail Ivanilov', 'michail.ivanilov@devbridge.com', 'ADVISOR'),
    (8, 'Viktor Dulko', 'viktor.d@devbridge.com', 'DEFAULT');





