use plm;

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
	foreign key (projectId) references `Project`(id)
);

create table `UserStory`(
	id int auto_increment primary key,
	name varchar(50) not null,
	description varchar(200),
	releaseId int,
	foreign key (releaseId) references `Release`(id)
);

create table `Bug`(
	id int auto_increment primary key,
	name varchar(50) not null,
	description varchar(200),
	userStoryId int,
	createdId int,
	assignedId int,
	foreign key (userStoryId) references `UserStory`(id),
	foreign key (createdId) references `User`(id),
	foreign key (assignedId) references `User`(id)
);

create table `Task`(
	id int auto_increment primary key,
	name varchar(50) not null,
	description varchar(200),
	userStoryId int,
	assignedId int,
	foreign key (userStoryId) references `UserStory`(id),
	foreign key (assignedId) references `User`(id)
);
