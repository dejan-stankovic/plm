use plm;

insert into `Role`(id,name) values (1,"Stakeholder");
insert into `Role`(id,name) values (2,"Project Leader");
insert into `Role`(id,name) values (3,"Business Analyst");
insert into `Role`(id,name) values (4,"Quality Assurance");
insert into `Role`(id,name) values (5,"Developer");
insert into `Role`(id,name) values (6,"Release Manager");

insert into `User`(id,name,password) values (1,"auser","apassword");
insert into `User`(id,name,password) values (2,"anotheruser","apassword");

insert into `Project`(id,name) values(1,"My First Project");

insert into `UserProject`(id,userId,roleId,projectId) values(1,1,2,1);
insert into `UserProject`(id,userId,roleId,projectId) values(2,2,1,1);

insert into `Release`(id,version,projectId) values(1,"1.0",1);

insert into `UserStory`(id,name,description,releaseId) values(1,"New story","Some description here",1);

insert into `Bug`(id,name,description,userStoryId,createdId,assignedId) values(1,"New bug","Bad problem...",1,1,1);

insert into `Task`(id,name,description,userStoryId,assignedId) values(1,"New task","Task description here",1,1);
