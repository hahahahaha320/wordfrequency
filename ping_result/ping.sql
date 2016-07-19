insert into jyutping_ping (pingyin,times,numbers)
select pingyin,sum(times),count(0) from jyutping_ori group by pingyin;

insert into pt_ping (pingyin,times,numbers)
select hanyu_pingyin,sum(times),count(0) from jyutping_ori group by hanyu_pingyin;
#按次数排序
select good_pingyin '拼音',group_concat(concat(word,'(',times,')',all_pingyin,' || ') ORDER BY times DESC) '详情',sum(times) '出现次数',count(*) '字数'
(select times from jyutping_ping where pingyin=j.pingyin) ping_sum 
from jyutping_ori j
group by pingyin,tone order by ping_sum desc,sum(times) desc limit 10000

#按韵母排序
select good_pingyin '拼音',
group_concat(concat(word,'(',times,')',all_pingyin,' || ') ORDER BY times DESC) '详情'
,sum(times) '出现次数',count(*) '字数'
from jyutping_ori j
group by pingyin,tone order by substring(pingyin,2) asc,substring(pingyin,1,2) asc limit 10000

#按声母排序
select good_pingyin '拼音',
group_concat(concat(word,'(',times,')',all_pingyin,' || ') ORDER BY times DESC) '详情',
sum(times) '出现次数',count(*) '字数'
from jyutping_ori j
group by pingyin,tone order by substring(pingyin,1,2) asc,substring(pingyin,2) asc limit 10000


#按汉语拼音到粤语拼音相同的转化度
select hanyu_pingyin '汉语拼音',pingyin '粤语拼音',
(select numbers from pt_ping where pingyin=o.hanyu_pingyin) hanyu_num ,count(0) '粤拼数',
group_concat(concat(word,all_pingyin)) '所有字'
from jyutping_ori o where hanyu_pingyin in 
(select hanyu_pingyin from
(
select hanyu_pingyin,
(select numbers from pt_ping where pingyin=o.hanyu_pingyin) hanyu_num
from jyutping_ori o 
group by pingyin,hanyu_pingyin  having (count(*) > hanyu_num*0.6 and hanyu_num>=3 )
limit 1000
) temp)
group by pingyin,hanyu_pingyin
having count(*) >= hanyu_num*0.5 #此条件可去掉
#order by hanyu_pingyin asc,hanyu_num desc,count(0) desc limit 1000
order by substring(hanyu_pingyin,2) asc,substring(hanyu_pingyin,1,2) asc,hanyu_num desc,count(0) desc limit 1000

#前500高频字
select id,word,good_pingyin from jyutping_ori where id <=500 
order by substring(pingyin,1,2) asc,substring(pingyin,2) asc,times desc