# 常见问题

本文档收集了用户在使用Meblog-Java过程中可能遇到的常见问题，并提供了详细的解答。如果您在使用过程中遇到了其他问题，可以通过[联系支持](/guide#12-联系支持)获取帮助。

## 1. 安装部署问题

### 1.1 环境要求是什么？

Meblog-Java的环境要求如下：

- JDK 11+
- MySQL 8.x
- Redis 6.x
- Minio 2023.x
- Maven 3.6+
- Git 2.x+

### 1.2 如何修改默认端口？

修改配置文件中的`server.port`配置项：

```yaml
server:
  port: 8081
```

### 1.3 数据库连接失败怎么办？

请检查以下几点：

1. MySQL服务是否正常运行
2. 数据库地址、端口是否正确
3. 数据库用户名和密码是否正确
4. 数据库是否存在
5. 防火墙是否允许连接

### 1.4 Redis连接失败怎么办？

请检查以下几点：

1. Redis服务是否正常运行
2. Redis地址、端口是否正确
3. Redis密码是否正确
4. Redis数据库是否存在
5. 防火墙是否允许连接

### 1.5 Minio连接失败怎么办？

请检查以下几点：

1. Minio服务是否正常运行
2. Minio地址、端口是否正确
3. Minio访问密钥和秘密密钥是否正确
4. 存储桶是否存在
5. 防火墙是否允许连接

### 1.6 如何部署到生产环境？

部署到生产环境的步骤：

1. 编译项目：`mvn clean install -DskipTests`
2. 准备生产环境配置文件
3. 使用`nohup`或`systemd`启动应用
4. 配置Nginx反向代理
5. 配置HTTPS
6. 设置防火墙规则

## 2. 功能使用问题

### 2.1 如何创建管理员账号？

目前需要手动在数据库中创建管理员账号，后续版本将提供初始化脚本。

### 2.2 如何修改文章的状态？

使用更新文章API，修改`status`字段：

- 1：已发布
- 0：草稿

### 2.3 如何批量删除文章？

目前暂不支持批量删除文章，后续版本将添加此功能。

### 2.4 如何添加新的分类？

调用分类管理API创建新分类：

```
POST http://localhost:8080/api/admin/category
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "新分类",
  "parentId": 0,
  "sort": 1
}
```

### 2.5 如何上传图片？

调用文件上传API上传图片：

```
POST http://localhost:8080/api/admin/file/upload
Content-Type: multipart/form-data
Authorization: Bearer <token>

file: <图片文件>
```

### 2.6 如何修改网站设置？

调用博客设置API更新网站设置：

```
PUT http://localhost:8080/api/admin/settings
Content-Type: application/json
Authorization: Bearer <token>

{
  "siteName": "新网站名称",
  "siteDescription": "新网站描述"
}
```

### 2.7 如何创建Wiki页面？

调用Wiki管理API创建Wiki页面：

```
POST http://localhost:8080/api/admin/wiki
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "新Wiki页面",
  "content": "Wiki页面内容",
  "parentId": 0,
  "sort": 1
}
```

## 3. 性能优化问题

### 3.1 如何优化数据库查询？

1. 为频繁查询的字段创建索引
2. 优化SQL查询，避免全表扫描
3. 使用分页查询，避免一次性查询大量数据
4. 定期优化表结构

### 3.2 如何优化缓存使用？

1. 合理设置缓存过期时间
2. 对频繁访问的数据进行缓存
3. 避免缓存雪崩和缓存穿透
4. 使用缓存预热机制

### 3.3 如何优化图片加载速度？

1. 压缩图片大小
2. 使用适当的图片格式（如WebP）
3. 实现图片懒加载
4. 使用CDN加速图片访问

### 3.4 如何优化网站加载速度？

1. 优化数据库查询
2. 使用缓存
3. 压缩图片和静态资源
4. 使用CDN加速
5. 优化代码结构
6. 启用HTTP/2

## 4. 安全配置问题

### 4.1 如何修改JWT密钥？

修改配置文件中的`jwt.secret`配置项：

```yaml
jwt:
  secret: 新的JWT密钥
```

### 4.2 如何修改JWT过期时间？

修改配置文件中的`jwt.tokenExpireTime`配置项，单位为分钟：

```yaml
jwt:
  tokenExpireTime: 60 # 1小时
```

### 4.3 如何设置强密码策略？

目前暂不支持密码策略配置，后续版本将添加此功能。

### 4.4 如何防止SQL注入？

Meblog-Java使用了MyBatis Plus，默认使用参数化查询，可以有效防止SQL注入。

### 4.5 如何防止XSS攻击？

1. 对输入内容进行过滤和转义
2. 使用CSP（Content Security Policy）
3. 对输出内容进行转义

### 4.6 如何配置HTTPS？

1. 申请SSL证书
2. 配置Nginx支持HTTPS
3. 修改网站域名配置为HTTPS地址

## 5. 技术支持问题

### 5.1 如何获取帮助？

可以通过以下方式获取帮助：

1. 查看[使用指南](/guide)
2. 查看[API文档](/api)
3. 提交[Issue](<项目仓库地址>/issues)
4. 联系项目维护者

### 5.2 如何提交Bug？

提交Bug时，请提供以下信息：

1. 环境信息（JDK版本、MySQL版本、Redis版本等）
2. 问题描述
3. 复现步骤
4. 错误日志
5. 预期结果
6. 实际结果

### 5.3 如何贡献代码？

贡献代码的步骤：

1. Fork项目仓库
2. 创建分支：`git checkout -b feature/xxx`
3. 提交代码：`git commit -m "添加xxx功能"`
4. 推送分支：`git push origin feature/xxx`
5. 创建Pull Request

### 5.4 如何获取最新版本？

可以通过以下方式获取最新版本：

1. 查看[更新日志](/changelog)
2. 拉取最新代码：`git pull origin master`
3. 编译最新版本：`mvn clean install -DskipTests`

## 6. 其他问题

### 6.1 如何备份数据？

1. 备份数据库：`mysqldump -u root -p meblog > meblog_backup.sql`
2. 备份文件：使用Minio客户端备份存储桶

### 6.2 如何恢复数据？

1. 恢复数据库：`mysql -u root -p meblog < meblog_backup.sql`
2. 恢复文件：使用Minio客户端恢复存储桶

### 6.3 如何迁移数据到新服务器？

1. 备份旧服务器的数据库和文件
2. 在新服务器上安装相同版本的系统
3. 恢复数据库和文件
4. 修改配置文件中的相关配置

### 6.4 如何查看日志？

日志文件默认存储在以下位置：

```
meblog-web/logs/meblog-web.log
meblog-admin/logs/meblog-admin.log
```

### 6.5 如何修改日志级别？

修改日志配置文件`logback-meblog.xml`中的日志级别：

```xml
<logger name="cn.iswxl.meblog" level="debug" />
```

### 6.6 如何添加新的功能？

添加新功能的步骤：

1. 分析需求
2. 设计API
3. 实现业务逻辑
4. 编写测试用例
5. 更新文档

## 7. 后续规划

### 7.1 后续版本将添加哪些功能？

后续版本计划添加的功能：

- 完整的前端管理后台
- 文章评论功能
- 文章点赞功能
- 社交媒体分享功能
- 邮件通知功能
- 数据统计和分析
- Docker和K8s支持
- 更多的主题和模板

### 7.2 如何获取版本更新信息？

可以通过以下方式获取版本更新信息：

1. 查看[更新日志](/changelog)
2. 关注项目仓库的Release信息
3. 订阅项目的更新通知

## 8. 联系我们

如果您在使用过程中遇到了其他问题，可以通过以下方式联系我们：

- 项目仓库：<项目仓库地址>
- 邮箱：<项目维护者邮箱>
- 社交媒体：<社交媒体链接>

我们将尽快回复您的问题。