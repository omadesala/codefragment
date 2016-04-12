delimiter #
create procedure etldata(total int,num int)
BEGIN
	declare i int;
	declare datasize int;
	declare loopcount int;
	declare offset int;
	set i = 0;
	set offset = num+1;
	set loopcount = total - num;	
	while i < loopcount do
		insert into 600000SS11(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,target) select * from ( select close from 600000SS  limit i,offset) t1;

/* 		select close from 600000SS limit i,offset; */
		set i = i + 1;
	end while;
END #
