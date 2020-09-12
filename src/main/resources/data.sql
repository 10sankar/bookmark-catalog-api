insert into card
(id, title, description, image_url,hidden,org_id,created_by, last_updated_by)
values
(1, 'Card title', 'say something about card', 'http://image.url',false,1,'sankar','sankar'),
(2, 'Card title', 'say something about card', 'http://image.url',true, 2,'sankar','sankar'),
(3, 'Card title', 'say something about card', 'http://image.url',false,3,'sankar','sankar'),
(4, 'Card title', 'say something about card', 'http://image.url',false,4,'sankar','sankar'),
(5, 'Card title', 'say something about card', 'http://image.url',false,5,'sankar','sankar'),
(6, 'Card title', 'say something about card', 'http://image.url',true, 6,'sankar','sankar'),
(7, 'Card title', 'say something about card', 'http://image.url',false,1,'sankar','sankar'),
(8, 'Card title', 'say something about card', 'http://image.url',false,2,'sankar','sankar');


insert into org
(id, tribe_name,team_name)
values
(1, 'LTL', 'CRX'),
(2, 'GTG','YER'),
(3, 'HTH','JAM'),
(4, 'LTL','OLJ'),
(5, 'GTG','PLQ'),
(6, 'HTH','KTM');


insert into tags
(id, value)
values
(1, 'java'),
(2, 'docker'),
(3, 'monitoring'),
(4, 'frontend'),
(5, 'springboot'),
(6, 'api');


insert into card_tag
(cid, tid)
values
(1, 1),(1,3),(1,6),
(2, 2),(2,5),(2,4),
(3, 3),(3,1),(3,6),
(4, 4),(4,2),(4,5),
(5, 5),(5,3),(5,4),
(6, 6),(6,1),(6,2),
(7, 5),(7,3),(7,4),
(8, 6),(8,1),(8,2);


------ Card Queue --------

insert into card_queue
(queue_id,cid, title,suggested_by)
values
(1,3,'change title', 'normal');

insert into card_queue
(queue_id,cid,image_url,suggested_by)
values
(2,5,'new image','normal');

insert into card_queue
(queue_id,cid,hidden,suggested_by)
values
(3,6,true,'normal');

insert into card_queue
(queue_id,cid,remove,suggested_by)
values
(4,8,true,'write');

insert into card_queue
(queue_id,cid,team_id,suggested_by)
values
(5,2,4,'normal');

insert into card_queue
(queue_id,title,description,image_url,hidden,team_id,suggested_by)
values
(6, 'Card title', 'say something about card', 'http://image.url',false,3,'read');

insert into card_queue
(queue_id,title,description,image_url,hidden,team_id,suggested_by)
values
(7, 'Card title', 'say something about card', 'http://image.url',true,4,'normal');


---- Group Creation Queue -------

insert into team_creation_queue
(id,team_name, under_tribe,created_by)
values
(1,'NEW','LTL', 'new team manager');


----- Users ------

insert into user(id, name, org_id,access_level)
values
(1,'sankar',1,10),
(2,'write',2, 5),
(3,'read',2, 3),
(4,'team mgr2',2, 7),
(5,'tribe mgr2',2, 9),
(6,'tribe mgr1',1, 9),
(7,'tribe mgr1',1, 7),
(8,'new team manager',1,3);

--------------------
--- ACCESS_LEVEL ---
--------------------
-- READ  : 3
-- WRITE : 5
-- TEAM_MGR: 7
-- TRIBE_MGR: 9




-- select card.*, group_concat(tags.value separator ',') as tags, org.tribe_name, org.team_name
-- from card
-- left join card_tag on card.id = card_tag.cid
-- left join tags on card_tag.tid = tags.id
-- left join team on org.id = org.team_id
-- group by card.id;

