# 快速开始

本文将指导您快速部署和使用Meblog-Java系统。按照以下步骤操作，您将在短时间内搭建起一个功能完整的博客系统后端。

## 1. 环境要求

在开始部署之前，请确保您的系统满足以下环境要求：

| 软件 | 版本 | 用途 |
|------|------|------|
| JDK | 11+ | Java开发环境 |
| MySQL | 8.x | 关系型数据库 |
| Redis | 6.x | 缓存数据库 |
| Minio | 2023.x | 对象存储服务 |
| Maven | 3.6+ | 项目构建工具 |
| Git | 2.x+ | 版本控制工具 |

## 2. 安装部署步骤

### 2.1 克隆代码

首先，克隆Meblog-Java项目代码到本地：

```bash
git clone <项目仓库地址>
cd meblog-java-page/meblog
```

### 2.2 数据库配置

1. 创建MySQL数据库：

```sql
CREATE DATABASE meblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 导入数据库脚本（如果有）：

> 注意：目前项目中可能没有提供数据库脚本，您需要根据实体类自行创建数据库表。后续版本将提供完整的数据库脚本。

### 2.3 配置文件修改

1. 修改`meblog-web/src/main/resources/application-dev.yml`文件：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/meblog?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
  redis:
    host: localhost
    port: 6379
    password: ''
    database: 0

minio:
  endpoint: http://localhost:9000
  access-key: minioadmin
  secret-key: minioadmin
  bucket-name: meblog
  url: http://localhost:9000/meblog

# 网站域名配置
site:
  domain: localhost:8080
  external-link-domain: localhost:8080
```

2. 修改`meblog-admin/src/main/resources/application-dev.yml`文件（如果有）：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/meblog?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
  redis:
    host: localhost
    port: 6379
    password: ''
    database: 0

minio:
  endpoint: http://localhost:9000
  access-key: minioadmin
  secret-key: minioadmin
  bucket-name: meblog
  url: http://localhost:9000/meblog
```

### 2.4 构建项目

使用Maven构建项目：

```bash
mvn clean install -DskipTests
```

## 3. 启动方式

### 3.1 直接运行

1. 启动Redis服务：

```bash
redis-server
```

2. 启动Minio服务：

```bash
minio server /data
```

3. 运行Meblog-Web应用：

```bash
cd meblog-web
sjava -jar target/meblog-web-0.7.0.jar
```

4. 运行Meblog-Admin应用（如果需要）：

```bash
cd meblog-admin
java -jar target/meblog-admin-0.7.0.jar
```

### 3.2 使用IDE运行

1. 导入项目到IntelliJ IDEA或Eclipse
2. 配置JDK 11+环境
3. 运行`meblog-web/src/main/java/cn/iswxl/meblog/web/MeblogWebApplication.java`类
4. 运行`meblog-admin/src/main/java/cn/iswxl/meblog/admin/MeblogAdminApplication.java`类（如果存在）

## 4. 验证部署

### 4.1 访问API文档

部署成功后，您可以通过以下地址访问API文档：

```
http://localhost:8080/swagger-ui.html
```

### 4.2 测试API

使用Postman或curl测试API是否正常工作：

1. 获取文章列表：

```bash
curl -X GET http://localhost:8080/api/article/list
```

2. 获取分类列表：

```bash
curl -X GET http://localhost:8080/api/category/list
```

3. 获取标签列表：

```bash
curl -X GET http://localhost:8080/api/tag/list
```

## 5. 快速使用

### 5.1 管理后台登录

1. 访问管理后台登录API：

```
POST http://localhost:8080/api/admin/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

> 注意：默认管理员账号可能需要在数据库中手动创建，后续版本将提供初始化脚本。

2. 登录成功后，会返回JWT令牌，后续请求需要在Header中携带该令牌：

```
Authorization: Bearer <token>
```

### 5.2 发布文章

使用管理员令牌发布文章：

```
POST http://localhost:8080/api/admin/article
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "测试文章",
  "content": "# 测试文章内容\n这是一篇测试文章。",
  "summary": "这是一篇测试文章的摘要",
  "categoryId": 1,
  "tagIds": [1, 2],
  "status": 1
}
```

### 5.3 管理分类

创建分类：

```
POST http://localhost:8080/api/admin/category
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "技术博客",
  "parentId": 0,
  "sort": 1
}
```

### 5.4 管理标签

创建标签：

```
POST http://localhost:8080/api/admin/tag
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Java"
}
```

## 6. 配置说明

### 6.1 数据库配置

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| spring.datasource.url | 数据库连接URL | jdbc:mysql://localhost:3306/meblog |
| spring.datasource.username | 数据库用户名 | root |
| spring.datasource.password | 数据库密码 | 123456 |

### 6.2 Redis配置

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| spring.redis.host | Redis主机地址 | localhost |
| spring.redis.port | Redis端口 | 6379 |
| spring.redis.password | Redis密码 | 空 |
| spring.redis.database | Redis数据库索引 | 0 |

### 6.3 Minio配置

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| minio.endpoint | Minio服务地址 | http://localhost:9000 |
| minio.access-key | Minio访问密钥 | minioadmin |
| minio.secret-key | Minio秘密密钥 | minioadmin |
| minio.bucket-name | 存储桶名称 | meblog |
| minio.url | 文件访问URL | http://localhost:9000/meblog |

### 6.4 JWT配置

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| jwt.issuer | JWT发行人 | aipno |
| jwt.secret | JWT密钥 | 随机生成的密钥 |
| jwt.tokenExpireTime | Token过期时间（分钟） | 30 |
| jwt.tokenHeaderKey | Token请求头Key | Authorization |
| jwt.tokenPrefix | Token前缀 | Bearer |

## 7. 常见问题

### 7.1 端口被占用

如果端口被占用，可以修改配置文件中的端口号：

```yaml
server:
  port: 8081
```

### 7.2 数据库连接失败

- 检查数据库地址、端口、用户名和密码是否正确
- 检查MySQL服务是否正常运行
- 检查防火墙是否允许连接

### 7.3 Redis连接失败

- 检查Redis地址、端口和密码是否正确
- 检查Redis服务是否正常运行
- 检查防火墙是否允许连接

### 7.4 Minio连接失败

- 检查Minio地址、端口和密钥是否正确
- 检查Minio服务是否正常运行
- 检查存储桶是否存在
- 检查防火墙是否允许连接

### 7.5 启动后无法访问API

- 检查应用是否成功启动
- 检查端口号是否正确
- 检查防火墙是否允许访问
- 检查是否有错误日志输出

## 8. 日志查看

应用启动后，日志默认输出到控制台和日志文件中。日志文件位于：

```
meblog-web/logs/meblog-web.log
meblog-admin/logs/meblog-admin.log
```

您可以通过查看日志文件来排查问题。

## 9. 下一步

- [查看API文档](/api)：了解系统提供的API接口
- [阅读使用指南](/guide)：学习如何使用管理后台
- [了解系统架构](/architecture)：深入理解系统设计
- [参与开发](/dev-guide)：为项目贡献代码

## 10. 联系方式

如果您在使用过程中遇到问题，可以通过以下方式获取帮助：

- 查看[常见问题](/faq)
- 提交[Issue](<项目仓库地址>/issues)
- 联系项目维护者

祝您使用愉快！