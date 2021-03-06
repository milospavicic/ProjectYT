DROP SCHEMA IF EXISTS projectYT;
CREATE SCHEMA projectYT DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE projectYT;

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
profileUrl VARCHAR(300),
lol BOOLEAN NOT NULL DEFAULT FALSE,
PRIMARY KEY (userName)
);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('marko','123','Marko','Markovic','marko@gmail.com',null,'USER','2018-1-1','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('darko','123','Darko','Darkovic','darko@gmail.com',null,'USER','2018-3-3','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('stanko','123','Stanko','Stankic','stanko@gmail.com',null,'USER','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('pera','123','Pera','Peric','pera@gmail.com',null,'ADMIN','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('mirko','123','Mirko','Mirkovic','mirko@gmail.com',null,'USER','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('zoran','123','Zoran','Jovanovic','zoran@gmail.com',null,'USER','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('goran','123','Goran','Jovanovic','goran@gmail.com',null,'USER','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO users(userName,password,firstName,lastName,email,channelDescription,userType,registrationDate,profileUrl,lol) VALUES('123','123','Ime123','Prezime123','123@gmail.com',null,'admin','2018-4-4','http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png',false);
INSERT INTO `users` (`userName`,`password`,`firstName`,`lastName`,`email`,`channelDescription`,`userType`,`registrationDate`,`blocked`,`deleted`,`profileUrl`,`lol`) VALUES ('sake','sake123','Sandra','Stojanovic','email',NULL,'USER','2018-06-09',0,0,'https://imagesvc.timeincuk.net/v3/keystone/image?url=http://ksassets.timeincuk.net/wp/uploads/sites/46/2016/05/Best-Beauty-Cannes-Film-Festival-2016-Adriana-Lima--e1501163450723.jpg&q=82',0);

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
pictureUrl VARCHAR(200) NOT NULL,
videoName VARCHAR(80) NOT NULL,
description VARCHAR(500),
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
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0CbN8sfihY','https://s.aolcdn.com/hss/storage/midas/8c786b6e2ab90b7d527621886ee9ff4d/205751517/sw-tlj-ed.jpg','Star Wars: The Last Jedi Trailer',
'Watch the new trailer for Star Wars: The Last Jedi and see it in theaters December 15.','PUBLIC',false,true,true,1244,11,1942,'2018-3-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/qxjPjPzQ1iU','https://i.ytimg.com/vi/h5XQq1ulspc/maxresdefault.jpg','War for the Planet of the Apes',
'Directed By Matt Reeves
Cast: Andy Serkis, Woody Harrelson, Steve Zahn, Amiah Miller, Karin Konoval, Judy Greer and Terry Notary','PUBLIC',false,true,true,124,11,22314,'2018-4-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/6ZfuNTqbHE8','https://pixel.nymag.com/imgs/daily/vulture/2018/03/20/avenger/20-avengers-lede.w710.h473.jpg','Marvel Studios\' Avengers: Infinity War Official Trailer',
'"There was an idea�" Avengers: Infinity War. In theaters April 27.','PUBLIC',false,true,true,4142,121,5451,'2018-2-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/MWRUNTLisPo','https://i.ytimg.com/vi/31lEwIF6AcI/maxresdefault.jpg','DOCTOR STRANGE Official Trailer (Marvel - 2016 )',
'After his career is destroyed, a brilliant but arrogant and conceited surgeon gets a new lease on life when a sorcerer takes him under his wing and trains him to defend the world against evil. ','PRIVATE',false,true,true,412,82,1251,'2018-1-4','marko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/5OVY7MmSSYs','http://demofest.org/wp-content/uploads/2017/03/Bad-Copy-4-2.jpg','bad copy - esi mi dobar',
'"I kad mi pridje neki smarac ja mu kazem odma, esi mi Boban, e, esi mi Boban.."','PUBLIC',false,true,true,424,21,600,'2017-4-4','pera',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/vhbMbiYb5bg','https://lths.news/wp-content/uploads/2018/02/Greta-Van-Fleet.jpg','Greta Van Fleet - Black Smoke Rising',
'Greta Van Fleet is an American rock band from Frankenmuth, Michigan, formed in 2012.','UNLISTED',false,true,true,421,123,1244,'2017-3-4','pera',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/3BXDsVD6O10','https://e.snmc.io/lk/l/l/0c5f19d420aa434d0b86dbd872e08e24/6821082.jpg','Eminem - River (Audio) ft. Ed Sheeran',
'Eminem\'s track "River" ft. Ed Sheeran is available on the album \'Revival,\' out now: http://shady.sr/Revival','PUBLIC',false,true,true,5523,522,8345,'2017-2-4','stanko',false);
INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes
,numberOfDislikes,views,datePosted,owner,deleted) VALUES('https://www.youtube.com/embed/Q0oIoR9mLwc','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWbta65PJ9nZFAC1O3sEA_qgt6xjJTvUXSSp2FZ4DH8pU7Btpo','Red Hot Chili Peppers - Dark Necessities',
'Watch the music video for �Dark Necessities� now! ','PUBLIC',false,true,true,1234,453,2351,'2017-1-4','stanko',false);


CREATE TABLE comment(
id BIGINT AUTO_INCREMENT,
text VARCHAR(500) NOT NULL,
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

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Best movie','marko',2,'2017-2-2',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('woow','pera',2,'2017-5-5',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Cool','stanko',2,'2017-7-7',5,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Lame','marko',2,'2017-9-9',2,1,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('So lamee','goran',2,'2018-1-1',4,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Best movie','marko',3,'2017-2-2',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('woow','pera',3,'2017-5-5',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Cool','stanko',3,'2017-7-7',5,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Lame','marko',3,'2017-9-9',2,1,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('So lamee','goran',3,'2018-1-1',4,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Best movie','marko',4,'2017-2-2',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('woow','pera',4,'2017-5-5',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Cool','stanko',4,'2017-7-7',5,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Lame','marko',4,'2017-9-9',2,1,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('So lamee','goran',4,'2018-1-1',4,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Best movie','marko',5,'2017-2-2',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('woow','pera',5,'2017-5-5',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Cool','stanko',5,'2017-7-7',5,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Lame','marko',5,'2017-9-9',2,1,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('So lamee','goran',5,'2018-1-1',4,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Best movie','marko',6,'2017-2-2',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('woow','pera',6,'2017-5-5',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Cool','stanko',6,'2017-7-7',5,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Lame','marko',6,'2017-9-9',2,1,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('So lamee','goran',6,'2018-1-1',4,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Best movie','marko',7,'2017-2-2',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('woow','pera',7,'2017-5-5',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Cool','stanko',7,'2017-7-7',5,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Lame','marko',7,'2017-9-9',2,1,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('So lamee','goran',7,'2018-1-1',4,2,false);

INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Best movie','marko',8,'2017-2-2',7,4,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('woow','pera',8,'2017-5-5',6,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Cool','stanko',8,'2017-7-7',5,2,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('Lame','marko',8,'2017-9-9',2,1,false);
INSERT INTO comment(text,owner,videoId,datePosted,likeNumber,dislikeNumber,deleted)
VALUES('So lamee','goran',8,'2018-1-1',4,2,false);


CREATE TABLE likedislike(
id BIGINT AUTO_INCREMENT,
liked BOOLEAN NOT NULL,
likeDate DATE NOT NULL,
owner VARCHAR(10),
deleted BOOLEAN,
PRIMARY KEY (id),
FOREIGN KEY (owner) REFERENCES users(userName) ON DELETE RESTRICT
);
INSERT INTO likedislike(liked,likeDate,owner,deleted)
VALUES(true,'2018-2-5','marko',false);
INSERT INTO likedislike(liked,likeDate,owner,deleted)
VALUES(true,'2018-2-3','darko',false);
INSERT INTO likedislike(liked,likeDate,owner,deleted)
VALUES(true,'2018-2-1','pera',false);
INSERT INTO likedislike(liked,likeDate,owner,deleted)
VALUES(true,'2017-9-5','stanko',false);
INSERT INTO likedislike(liked,likeDate,owner,deleted)
VALUES(true,'2017-4-5','marko',false);
INSERT INTO likedislike(liked,likeDate,owner,deleted)
VALUES(true,'2013-2-5','pera',false);
---------------------------------------------
CREATE TABLE likedislikevideo(
likeId BIGINT,
videoId BIGINT,
deleted BOOLEAN,
FOREIGN KEY (likeId) REFERENCES likedislike (id) ON DELETE RESTRICT,
FOREIGN KEY (videoId) REFERENCES video(id) ON DELETE RESTRICT
);
INSERT INTO likedislikevideo(likeId,videoId,deleted)
VALUES(1,1,false);
INSERT INTO likedislikevideo(likeId,videoId,deleted)
VALUES(2,1,false);
INSERT INTO likedislikevideo(likeId,videoId,deleted)
VALUES(3,2,false);
INSERT INTO likedislikevideo(likeId,videoId,deleted)
VALUES(4,2,false);

CREATE TABLE likedislikecomment(
likeId BIGINT,
commentId BIGINT,
deleted BOOLEAN,
FOREIGN KEY (likeId) REFERENCES likedislike (id) ON DELETE RESTRICT,
FOREIGN KEY (commentId) REFERENCES comment (id) ON DELETE RESTRICT
);
INSERT INTO likedislikecomment(likeId,commentId,deleted)
VALUES(5,1,false);
INSERT INTO likedislikecomment(likeId,commentId,deleted)
VALUES(6,1,false);