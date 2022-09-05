CREATE TABLE `Course` (
	`cid` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(30) NOT NULL UNIQUE,
	PRIMARY KEY (`cid`)
);

CREATE TABLE `Faculty` (
	`fid` INT NOT NULL,
	`name` VARCHAR(30) NOT NULL,
	`age` INT NOT NULL,
	PRIMARY KEY (`fid`)
);

CREATE TABLE `Student` (
	`rno` INT NOT NULL,
	`name` VARCHAR(30) NOT NULL,
	`ta` INT,
	PRIMARY KEY (`rno`)
);

CREATE TABLE `owned_by_fc` (
	`cid` INT NOT NULL,
	`fid` INT NOT NULL,
	PRIMARY KEY (`cid`,`fid`)
);

CREATE TABLE `owned_by_sc` (
	`cid` INT NOT NULL,
	`rno` INT NOT NULL,
	PRIMARY KEY (`cid`,`rno`)
);

ALTER TABLE `Student` ADD CONSTRAINT `Student_fk0` FOREIGN KEY (`ta`) REFERENCES `Course`(`cid`);

ALTER TABLE `owned_by_fc` ADD CONSTRAINT `owned_by_fc_fk0` FOREIGN KEY (`cid`) REFERENCES `Course`(`cid`);

ALTER TABLE `owned_by_fc` ADD CONSTRAINT `owned_by_fc_fk1` FOREIGN KEY (`fid`) REFERENCES `Faculty`(`fid`);

ALTER TABLE `owned_by_sc` ADD CONSTRAINT `owned_by_sc_fk0` FOREIGN KEY (`cid`) REFERENCES `Course`(`cid`);

ALTER TABLE `owned_by_sc` ADD CONSTRAINT `owned_by_sc_fk1` FOREIGN KEY (`rno`) REFERENCES `Student`(`rno`);

INSERT INTO Faculty (fid, name, age) VALUES
(1, "Amit Chattopadhyay", 40),
(2, "Pradeesha Ashok", 45),
(3, "Chandrashekar Ramanathan", 45),
(4, "Uttam Kumar", 40),
(5, "Bidisha Choudhary", 35),
(6, "Vinod Reddy", 45);

INSERT INTO Course (cid, name) VALUES
(1, "Computational Topology"),
(2, "DBMS"),
(3, "Maths"),
(4, "Signal Processing"),
(5, "Algorithm Design"),
(6, "History of ideas");

INSERT INTO Student (rno, name, ta) VALUES
(1, "Mukul Gupta", 1),
(2, "Rudransh Dixit", NULL),
(3, "Pushpang Patel", 5),
(4, "Aryan Bhatt", NULL),
(5, "Darpan Singh", 2),
(6, "Shashank Shekhar", NULL);

INSERT INTO owned_by_fc (cid, fid) VALUES
(1, 1),
(2, 3),
(2, 4),
(3, 1),
(3, 2),
(4, 6),
(5, 2),
(6, 5);

INSERT INTO owned_by_sc (cid, rno) VALUES
(1, 1),
(1, 2),
(1, 5),
(1, 6),
(2, 1),
(2, 2),
(2, 5),
(2, 6),
(3, 3),
(3, 4),
(4, 1),
(4, 5),
(4, 3),
(5, 6),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(6, 6);


