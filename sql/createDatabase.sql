DROP SCHEMA IF EXISTS ProjectYT;
CREATE SCHEMA ProjectYT DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE ProjectYT;

CREATE TABLE users(
userName VARCHAR(15) NOT NULL,
password VARCHAR(15) NOT NULL,
firstName VARCHAR(15),
lastName VARCHAR(15),
email VARCHAR(30) NOT NULL,
channelDescription VARCHAR(50),
userType ENUM ('USER','ADMIN') NOT NULL DEFAULT 'USER',
registrationDate DATE NOT NULL,
blocked BOOLEAN NOT NULL DEFAULT FALSE,
deleted BOOLEAN NOT NULL DEFAULT FALSE,
profileUrl VARCHAR(300) NOT NULL,
PRIMARY KEY (userName)
);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl) VALUES('marko','123','Marko','Markovic','marko@gmail.com',null,'USER','2018-1-1','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png');
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl) VALUES('darko','123','Darko','Darkovic','darko@gmail.com',null,'USER','2018-3-3','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png');
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl) VALUES('stanko','123','Stanko','Stankic','stanko@gmail.com',null,'USER','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png');
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl) VALUES('pera','123','Pera','Peric','pera@gmail.com',null,'ADMIN','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png');
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl) VALUES('mirko','123','Mirko','Mirkovic','mirko@gmail.com',null,'USER','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png');
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl) VALUES('zoran','123','Zoran','Jovanovic','zoran@gmail.com',null,'USER','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png');
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl) VALUES('goran','123','Goran','Jovanovic','goran@gmail.com',null,'USER','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png');
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl) VALUES('123','123','Ime123','Prezime123','123@gmail.com',null,'admin','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png');

CREATE TABLE subscribe(
mainUser VARCHAR(15),
subscriber VARCHAR(15),
FOREIGN KEY (mainUser) REFERENCES users(userName) ON DELETE RESTRICT,
FOREIGN KEY (subscriber) REFERENCES users(userName) ON DELETE RESTRICT
);
INSERT INTO subscribe(mainUser,subscriber)VALUES('marko','123');
INSERT INTO subscribe(mainUser,subscriber)VALUES('marko','zoran');
INSERT INTO subscribe(mainUser,subscriber)VALUES('marko','goran');
INSERT INTO subscribe(mainUser,subscriber)VALUES('marko','mirko');
-------------------------------------------------------------------
INSERT INTO subscribe(mainUser,subscriber)VALUES('darko','goran');
INSERT INTO subscribe(mainUser,subscriber)VALUES('darko','zoran');
INSERT INTO subscribe(mainUser,subscriber)VALUES('darko','123');
-------------------------------------------------------------------
INSERT INTO subscribe(mainUser,subscriber)VALUES('stanko','goran');
INSERT INTO subscribe(mainUser,subscriber)VALUES('stanko','zoran');
INSERT INTO subscribe(mainUser,subscriber)VALUES('stanko','123');
-------------------------------------------------------------------
INSERT INTO subscribe(mainUser,subscriber)VALUES('pera','goran');
INSERT INTO subscribe(mainUser,subscriber)VALUES('pera','zoran');
INSERT INTO subscribe(mainUser,subscriber)VALUES('pera','123');
-------------------------------------------------------------------
INSERT INTO subscribe(mainUser,subscriber)VALUES('goran','123');
-------------------------------------------------------------------
INSERT INTO subscribe(mainUser,subscriber)VALUES('zoran','123');

CREATE TABLE video(
id BIGINT AUTO_INCREMENT,
videoUrl VARCHAR(100) NOT NULL,
pictureUrl VARCHAR(100) NOT NULL,
videoName VARCHAR(50) NOT NULL,
description VARCHAR(50),
visibility ENUM ('PRIVATE','PUBLIC','UNLISTED') NOT NULL,
blocked BOOLEAN NOT NULL DEFAULT FALSE,
commentsEnabled BOOLEAN NOT NULL DEFAULT TRUE,
ratingEnabled BOOLEAN DEFAULT TRUE,
numberOfLikes BIGINT NOT NULL ,
numberOfDislikes BIGINT NOT NULL,
views BIGINT NOT NULL,
datePosted  DATE NOT NULL,
owner VARCHAR(10) NOT NULL,
deleted BOOLEAN NOT NULL DEFAULT FALSE,
PRIMARY KEY (id),
FOREIGN KEY (owner) REFERENCES users(userName) ON DELETE RESTRICT
);

INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Temp Name',
'Best movie123','PUBLIC',false,true,true,124,11,22314,'2018-4-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Temp Name',
'Best movie123','PUBLIC',false,true,true,124,11,22314,'2018-3-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Temp Name',
'Best movie123','PUBLIC',false,true,true,124,11,22314,'2018-2-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Temp Name',
'Best movie123','PUBLIC',false,true,true,124,11,22314,'2018-1-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Temp Name',
'Best movie123','PUBLIC',false,true,true,124,11,22314,'2017-4-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Temp Name',
'Best movie123','PUBLIC',false,true,true,124,11,22314,'2017-3-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Temp Name',
'Best movie123','PUBLIC',false,true,true,124,11,22314,'2017-2-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Temp Name',
'Best movie123','PUBLIC',false,true,true,124,11,22314,'2017-1-4','marko',false);


CREATE TABLE comment(
id BIGINT AUTO_INCREMENT,
text VARCHAR(100) NOT NULL,
owner VARCHAR(10) NOT NULL,
videoId BIGINT NOT NULL,
datePosted DATE NOT NULL,
likeNumber BIGINT NOT NULL,
dislikeNumber BIGINT NOT NULL,
deleted BOOLEAN NOT NULL DEFAULT FALSE,
PRIMARY KEY (id),
FOREIGN KEY (owner) REFERENCES users(userName) ON DELETE RESTRICT,
FOREIGN KEY (videoId) REFERENCES video(id) ON DELETE RESTRICT
);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Best movie','marko',1,'2017-2-2',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('woow','pera',1,'2017-5-5',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Cool','stanko',1,'2017-7-7',5,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Lame','marko',1,'2017-9-9',2,1,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('So lamee','goran',1,'2018-1-1',4,2,false);




CREATE TABLE likeDislike(
id BIGINT AUTO_INCREMENT,
liked BOOLEAN NOT NULL,
likeDate DATE NOT NULL,
owner VARCHAR(10),
PRIMARY KEY (id),
FOREIGN KEY (owner) REFERENCES users(userName) ON DELETE RESTRICT
);
INSERT INTO likeDislike(liked,likeDate,owner)
VALUES(true,'2018-2-5','marko');
INSERT INTO likeDislike(liked,likeDate,owner)
VALUES(true,'2018-2-3','darko');
INSERT INTO likeDislike(liked,likeDate,owner)
VALUES(true,'2018-2-1','pera');
INSERT INTO likeDislike(liked,likeDate,owner)
VALUES(true,'2017-9-5','stanko');
INSERT INTO likeDislike(liked,likeDate,owner)
VALUES(true,'2017-4-5','marko');
INSERT INTO likeDislike(liked,likeDate,owner)
VALUES(true,'2013-2-5','pera');
---------------------------------------------
CREATE TABLE likeDislikeVideo(
likeId BIGINT,
videoId BIGINT,
FOREIGN KEY (likeId) REFERENCES likeDislike (id) ON DELETE RESTRICT,
FOREIGN KEY (videoId) REFERENCES video(id) ON DELETE RESTRICT
);
INSERT INTO likeDislikeVideo(likeId,videoId)
VALUES(1,1);
INSERT INTO likeDislikeVideo(likeId,videoId)
VALUES(2,1);
INSERT INTO likeDislikeVideo(likeId,videoId)
VALUES(3,2);
INSERT INTO likeDislikeVideo(likeId,videoId)
VALUES(4,2);

CREATE TABLE likeDislikeComment(
likeId BIGINT,
commentId BIGINT,
FOREIGN KEY (likeId) REFERENCES likeDislike (id) ON DELETE RESTRICT,
FOREIGN KEY (commentId) REFERENCES comment (id) ON DELETE RESTRICT
);
INSERT INTO likeDislikeComment(likeId,commentId)
VALUES(5,1);
INSERT INTO likeDislikeComment(likeId,commentId)
VALUES(6,1);