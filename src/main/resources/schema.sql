use blogDB;

CREATE TABLE users (
                       user_id int(10) unsigned AUTO_INCREMENT PRIMARY key comment '主键',
                       username VARCHAR(50) NOT NULL unique comment '用户名',
                       password VARCHAR(100) NOT null comment '密码',
                       email VARCHAR(100) not null comment '邮箱',
                       created timestamp not null default current_timestamp comment '创建时间',
                       last_modified timestamp not null default current_timestamp on update current_timestamp comment '更新时间'
)ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

CREATE TABLE posts (
                       post_id int(10) unsigned AUTO_INCREMENT PRIMARY key comment '主键',
                       title VARCHAR(200) NOT null comment '标题',
                       content TEXT NOT null comment '内容',
                       user_id int(10) not null comment '作者ID',
                       created timestamp not null default current_timestamp comment '创建时间',
                       last_modified timestamp not null default current_timestamp on update current_timestamp comment '更新时间'
)ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表';