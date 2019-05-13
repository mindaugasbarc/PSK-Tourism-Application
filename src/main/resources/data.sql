-- TEST DATA WHEN SCHEMA IS CREATED --

-- CREATE office --
INSERT INTO office (name, address) VALUES
    ('Chicago office', '343 W. Erie St. Suite 600, Chicago, IL 60654, United States'),
    ('Toronto office', '36 Toronto Street Suite 260, Toronto, Ontario M5C 2C5, Canada'),
    ('London office', '8 Devonshire Square, London EC2M 4PL, United Kingdom'),
    ('Kaunas office', '11d. Juozapaviciaus pr., Kaunas, LT-45252, Lithuania'),
    ('Vilnius office', '135 Zalgirio g., Vilnius, LT-08217, Lithuania');

-- CREATE users --
INSERT INTO users (fullname, email, role) VALUES
    ('Admin', 'admin@devbridge.com', 'ADVISOR'),
    ('Markas Gedminas', 'markas.gedinimas@devbridge.com', 'DEFAULT'),
    ('Robert Požarickij', 'robertp@devbridge.com', 'DEFAULT'),
    ('Simona Mockute', 'simona.mockute@devbridge.com', 'DEFAULT'),
    ('Lina Pakalkaite', 'lina.pakalkaite@devbridge.com', 'ADVISOR'),
    ('Justinas Viršilas', 'justinasv@devbridge.com', 'DEFAULT'),
    ('Michail Ivanilov', 'michail.ivanilov@devbridge.com', 'ADVISOR'),
    ('Viktor Dulko', 'viktor.d@devbridge.com', 'DEFAULT');

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- decrypting SELECT PGP_SYM_DECRYPT(password::bytea, 'geckoKey') from user_login; --
INSERT INTO user_login (user_id, username, password) VALUES
    (1, 'admin', PGP_SYM_ENCRYPT('admin', 'geckoKey')),
    (2, 'markas', PGP_SYM_ENCRYPT('markas', 'geckoKey')),
    (3, 'robert', PGP_SYM_ENCRYPT('robert', 'geckoKey')),
    (4, 'simona', PGP_SYM_ENCRYPT('simona', 'geckoKey')),
    (5, 'lina', PGP_SYM_ENCRYPT('lina', 'geckoKey')),
    (6, 'justinas', PGP_SYM_ENCRYPT('justinas', 'geckoKey')),
    (7, 'michail', PGP_SYM_ENCRYPT('michail', 'geckoKey')),
    (8, 'viktor', PGP_SYM_ENCRYPT('viktor', 'geckoKey'));







