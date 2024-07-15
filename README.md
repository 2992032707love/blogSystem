# Spring Boot Blog System

这个项目是一个简单的博客系统，使用 Spring Boot、MyBatis-Plus 和 MySQL 实现。它为博客文章提供基本的用户身份验证和 CRUD 操作

## 功能

- 用户注册 
- 用户登录 
- 创建、阅读、更新、删除博客文章 
- 基于 JWT 的身份验证 
- 基本权限（只有帖子作者可以编辑或删除他们的帖子）

## 技术栈

- Java 17
- SpringBoot 3.2.0
- Mybatis-Plus 3.5.6
- MySQL 8.0.33
- Docker (optional, for containerized deployment)

## Getting Started

### 数据库设置

1. 创建一个Mysql库：
    ```sql
    CREATE DATABASE blogDB;
    ```

2. Run the `schema.sql` script to create the necessary tables.

### Application Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/2992032707love/blogSystem.git
    cd blogSystem
    ```

2.  使用您的Mysql数据库更新`application.yml` 文件.

3. Build and run the application:
    ```bash
    mvn clean package
    java -jar target/blogSystem-1.0-SNAPSHOT-spring-boot.jar
    ```

### Docker 部署

1. 构建一个Docker镜像:
    ```bash
    docker build -t blog:1.0 .
    ```

2. 创建一个Docker容器:
    ```bash
    docker run -d -p 9401:9401 blog:1.0
    ```

## API 端点

- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/auth/me` -  获取当前用户信息（需要认证）
- `POST /api/posts` - 创建新文章（需要登录）
- `GET /api/posts` -  获取某个用户的所有文章列表 支持分页/按创建时间正/倒叙
- `GET /api/posts/{id}` - 获取单篇文章详情
- `PUT /api/posts/{id}` - 更新文章（需要登录和权限判断）
- `DELETE /api/posts/{id}` - 删除文章（需要登录和权限判断）

## Notes

- 密码使用 BCrypt 加密。
- JWT 用于身份验证。
- 确保在受保护端点的“token”请求头中处理 JWT 令牌。
