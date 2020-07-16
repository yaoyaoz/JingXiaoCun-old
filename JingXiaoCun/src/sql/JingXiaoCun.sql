create database JingXiaoCun character set utf8 collate utf8_general_ci;  
use JingXiaoCun;
-- set character_set_client=gb2312;
-- set character_set_results=gb2312;
 

create table goods(
    id varchar(40) primary key comment '序列',
    in_or_out_date date not null comment '入/出库时间',
    store_name varchar(40) not null comment '库房名称',
    goods_name varchar(40) not null comment '商品名称',
    goods_num int not null comment '入/出库数量',
    goods_price double not null comment '单价',
    in_or_out_type varchar(40) not null comment '操作类型',
    raw_add_time datetime not null comment '添加时间',
    raw_update_time timestamp not null comment '更新时间'
)character set utf8 collate utf8_general_ci;

alter table goods comment='商品入/出库表';

-- 注：timestamp 表示每次更新，这个值都会自动更新

insert into goods(id,in_or_out_date,store_name,goods_name,goods_num,goods_price,in_or_out_type,raw_add_time) 
values
('1','2015-07-04','大库房','香蕉',20,2.6,'IN_STORE',now());

