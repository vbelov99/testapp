 CREATE TABLE IF NOT EXISTS users_table (
   Id INTEGER IDENTITY PRIMARY KEY, 
   login varchar(20) NOT NULL, 
   password varchar(20) NOT NULL,
   UNIQUE (login)
 );