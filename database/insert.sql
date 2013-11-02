use plm;

insert into `Status`(id,name) values (1,"Initial");
insert into `Status`(id,name) values (2,"Pending");
insert into `Status`(id,name) values (3,"InProgress");
insert into `Status`(id,name) values (4,"Complete");

insert into `Role`(id,name) values (1,"Project Leader");
insert into `Role`(id,name) values (2,"Developer");

insert into `User`(id,name,password) values (1,"auser","apassword");
insert into `User`(id,name,password) values (2,"anotheruser","apassword");

insert into `Project`(id,name) values(1,"My First Project");

insert into `UserProject`(id,userId,roleId,projectId) values(1,1,1,1);
insert into `UserProject`(id,userId,roleId,projectId) values(2,2,2,1);

insert into `Release`(id,version,projectId,startDate,endDate) values(1,"1.0",1,CURDATE(),CURDATE());

insert into `UserStory`(id,name,description,releaseId,ownerId,statusId,points) values(1,"New story","Some description here",1,2,3,3);

insert into `UserStoryComment`(id,comments,authorId,createdOn,userStoryId) values(1,"this is an interesting idea.",1,CURDATE(),1);

insert into `Bug`(id,name,description,projectId,createdId,assignedId) values(1,"New bug","Bad problem...",1,1,1);

insert into `Task`(id,name,description,userStoryId,assignedId,statusId) values(1,"New task","Task description here",1,1,2);
