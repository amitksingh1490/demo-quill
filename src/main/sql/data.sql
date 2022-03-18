USE demoquill

CREATE TABLE user (id INT, first_name VARCHAR(255), last_name VARCHAR(255), age INT);
CREATE TABLE address (owner_fk INT, street VARCHAR(255), zip INT, state VARCHAR(255));

INSERT INTO user VALUES (1, 'Jack', 'Ripper', 55);
INSERT INTO user VALUES (2, 'Vlad', 'Dracul', 321);
INSERT INTO user VALUES (3, 'Joe', 'Bloggs', 20);

INSERT INTO address VALUES (1, 'Summer St', 11111, 'NY');
INSERT INTO address VALUES (1, 'Winter St', 11111, 'NY');
INSERT INTO address VALUES (2, 'Bran Castle', 11111, 'Transylvania');
INSERT INTO address VALUES (2, 'Ambras Castle', 11111, 'Innsbruck');
INSERT INTO address VALUES (3, 'Bloggo Sphere', 11111, 'NJ');
