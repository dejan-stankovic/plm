use plm;

create table `Status`(
	id int auto_increment primary key,
	name varchar(50) not null
);

create table `User`(
	id int auto_increment primary key,
	name varchar(50) not null,
	password varchar(50) not null
);

create table `Role`(
	id int auto_increment primary key,
	name varchar(50) not null
);

create table `Project`(
	id int auto_increment primary key,
	name varchar(50) not null
);

create table `UserProject`(
	id int auto_increment primary key,
	userId int,
	roleId int,
	projectId int,
	foreign key (userId) references `User`(id),
	foreign key (roleId) references `Role`(id),
	foreign key (projectId) references `Project`(id)
);

create table `Release`(
	id int auto_increment primary key,
	version varchar(50) not null,
	projectId int,
	startdate DATE,
	enddate DATE, 
	foreign key (projectId) references `Project`(id)
);

create table `UserStory`(
	id int auto_increment primary key,
	name varchar(50) not null,
	description varchar(200),
	points int,
	statusId int,
	ownerId int,
	releaseId int,
	foreign key (statusId) references `Status`(id),
	foreign key (ownerId) references `User`(id),
	foreign key (releaseId) references `Release`(id)
);

create table `UserStoryComments`(
	id int auto_increment primary key,
	comments varchar(50) not null,
	authorId int,
	createdOn  DATE,
	userStoryId int,
	foreign key (authorId) references `User`(id),
	foreign key (userStoryId) references `UserStory`(id)
);

create table `Bug`(
	id int auto_increment primary key,
	name varchar(50) not null,
	description varchar(200),
	projectId int,
	createdId int,
	assignedId int,
	statusId int,
	foreign key (projectId) references `Project`(id),
	foreign key (createdId) references `User`(id),
	foreign key (assignedId) references `User`(id),
	foreign key (statusId) references `Status`(id)
);

create table `Task`(
	id int auto_increment primary key,
	name varchar(50) not null,
	description varchar(200),
	userStoryId int,
	assignedId int,
	statusId int,
	foreign key (userStoryId) references `UserStory`(id),
	foreign key (assignedId) references `User`(id),
	foreign key (statusId) references `Status`(id)
);
