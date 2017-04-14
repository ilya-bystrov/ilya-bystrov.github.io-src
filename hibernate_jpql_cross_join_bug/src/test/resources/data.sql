truncate table person;
insert into person (id, firstname, lastname) values (1, 'Wayne', 'Austin');
insert into person (id, firstname, lastname) values (2, 'Evelyn', 'Duncan');
insert into person (id, firstname, lastname) values (3, 'Cheryl', 'Romero');

truncate table street_address;
insert into street_address (person_id, address) values (1, '57440 Esch Crossing');
insert into street_address (person_id, address) values (2, '4665 Ridgeview Parkway');
insert into street_address (person_id, address) values (3, '842 Fremont Plaza');