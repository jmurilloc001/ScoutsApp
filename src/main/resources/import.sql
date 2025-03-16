INSERT INTO products (name,price,stock,lastpurchase) VALUES('Pañoleta',3.0,10,'2008-12-31 13:00:00');
INSERT INTO products (name,price,stock,lastpurchase) VALUES('Mochila grande',40.0,2,'2010-06-04 13:00:00');
INSERT INTO products (name,price,stock,lastpurchase) VALUES('Mochila pequeña',25.0,5,'2024-05-10 13:00:00');
INSERT INTO products (name,price,stock,lastpurchase) VALUES('Espumadera',1.5,4,'2020-01-01 13:00:00');

INSERT INTO meetings (date_meeting) VALUES('2025-02-14 17:30:00');
INSERT INTO meetings (date_meeting) VALUES('2025-02-07 17:30:00');
INSERT INTO meetings (date_meeting) VALUES('2025-02-01 17:30:00');

INSERT INTO affiliates (name,lastname,birthday,inscripcion_date,seccion) VALUES('Jorge','Murillo Carrera','2005-09-12 00:00:00','2015-09-20 17:30:00','SCOUTER');
INSERT INTO affiliates (name,lastname,birthday,inscripcion_date,seccion) VALUES('Jano','Díaz Diez','2004-01-05 00:00:00','2014-09-20 17:30:00','SCOUTER');
INSERT INTO affiliates (name,lastname,birthday,inscripcion_date,seccion) VALUES('Alberto','Cruz','2005-09-12 00:00:00','2017-09-20 17:30:00','ROVER');

INSERT INTO roles (name) VALUES('ROLE_ADMIN');
INSERT INTO roles (name) VALUES('ROLE_USER');

INSERT INTO users (username,password,enabled) VALUES('admin','12345',true);
