use potato_base;
-- ----------------------------
-- 用户表
-- ----------------------------
drop table if exists t_user;
create table t_user (
    user_id       bigint(20)      not null auto_increment    comment '主键',
    user_name           varchar(100)    not null                   comment '用户名',
    password            varchar(100)    not null                   comment '密码',
    primary key (user_id)
) engine=innodb auto_increment=200 comment = '用户表';

INSERT INTO potato_base.t_user (user_id, user_name, password) VALUES (200, '小马', '123qwe');
