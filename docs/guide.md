# 使用指南

本文档将详细介绍Meblog-Java的使用方法，包括管理后台的使用、文章发布流程、分类标签管理、文件上传使用和系统设置配置等内容。

## 1. 管理后台介绍

Meblog-Java提供了功能完整的管理后台，用于管理博客系统的各项功能。管理后台采用了前后端分离的设计，通过RESTful API与前端交互。

### 1.1 管理后台功能模块

管理后台主要包括以下功能模块：

- **仪表盘**：展示网站统计信息和关键数据
- **文章管理**：管理文章的发布、编辑、删除等
- **分类管理**：管理文章分类
- **标签管理**：管理文章标签
- **用户管理**：管理系统用户
- **角色管理**：管理用户角色和权限
- **文件管理**：管理上传的文件和图片
- **博客设置**：配置博客的基本信息
- **Wiki管理**：管理Wiki文档

### 1.2 访问管理后台

管理后台的访问地址通常为：

```
http://localhost:8080/admin
```

> 注意：目前项目中可能没有提供前端管理后台，您需要自行开发前端页面或使用API工具（如Postman）来调用管理后台API。后续版本将提供完整的前端管理后台。

## 2. 登录与权限管理

### 2.1 管理员登录

1. 调用登录API获取JWT令牌：

```
POST http://localhost:8080/api/admin/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

2. 登录成功后，会返回JWT令牌，后续请求需要在Header中携带该令牌：

```
Authorization: Bearer <token>
```

### 2.2 权限管理

Meblog-Java采用了基于角色的访问控制（RBAC）模型，通过角色来管理用户的权限。

#### 2.2.1 角色管理

- **管理员**：拥有所有权限，可以管理系统的所有功能
- **编辑**：可以管理文章、分类、标签等内容
- **作者**：只能管理自己的文章

#### 2.2.2 权限分配

1. 创建角色：

```
POST http://localhost:8080/api/admin/role
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "编辑",
  "description": "可以编辑文章",
  "permissionIds": [1, 2, 3]
}
```

2. 为用户分配角色：

```
PUT http://localhost:8080/api/admin/user/{id}
Content-Type: application/json
Authorization: Bearer <token>

{
  "roleId": 2
}
```

## 3. 文章管理

### 3.1 文章发布流程

1. **创建文章**：填写文章标题、内容、摘要等信息
2. **选择分类**：为文章选择合适的分类
3. **添加标签**：为文章添加相关标签
4. **设置状态**：选择发布或保存为草稿
5. **发布文章**：点击发布按钮，文章将立即发布到网站

### 3.2 创建文章

调用创建文章API：

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

### 3.3 编辑文章

调用更新文章API：

```
PUT http://localhost:8080/api/admin/article/{id}
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "更新后的文章标题",
  "content": "更新后的文章内容",
  "summary": "更新后的文章摘要",
  "categoryId": 1,
  "tagIds": [1, 2, 3],
  "status": 1
}
```

### 3.4 删除文章

调用删除文章API：

```
DELETE http://localhost:8080/api/admin/article/{id}
Authorization: Bearer <token>
```

### 3.5 文章状态管理

文章支持以下状态：

- **已发布**：状态值为1，文章将显示在网站上
- **草稿**：状态值为0，文章不会显示在网站上，只有作者和管理员可以查看
- **回收站**：文章被删除后会进入回收站，可以恢复或永久删除（预留功能）

### 3.6 文章批量操作

支持对文章进行批量操作，如批量发布、批量删除等（预留功能）。

## 4. 分类与标签管理

### 4.1 分类管理

#### 4.1.1 创建分类

调用创建分类API：

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

#### 4.1.2 分类层级

Meblog-Java支持多级分类，您可以创建父子分类关系：

```
POST http://localhost:8080/api/admin/category
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Java",
  "parentId": 1, // 父分类ID
  "sort": 1
}
```

#### 4.1.3 分类排序

您可以通过设置`sort`字段来调整分类的显示顺序，数字越小，显示位置越靠前。

### 4.2 标签管理

#### 4.2.1 创建标签

调用创建标签API：

```
POST http://localhost:8080/api/admin/tag
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Java"
}
```

#### 4.2.2 标签云

系统会自动生成标签云，标签的大小根据使用频率自动调整（前端实现）。

#### 4.2.3 标签使用建议

- 标签名称要简洁明了
- 避免创建过多相似的标签
- 每个标签应该有明确的主题
- 文章标签数量建议控制在3-5个

## 5. 文件管理

### 5.1 文件上传

Meblog-Java集成了Minio对象存储，支持图片、文件的上传和管理。

#### 5.1.1 上传图片

调用上传文件API：

```
POST http://localhost:8080/api/admin/file/upload
Content-Type: multipart/form-data
Authorization: Bearer <token>

file: <图片文件>
```

#### 5.1.2 上传结果

上传成功后，会返回文件的访问URL：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "test.png",
    "path": "http://localhost:9000/meblog/test.png",
    "size": 102400,
    "type": "image/png"
  }
}
```

### 5.2 文件使用

1. **在文章中使用图片**：将返回的图片URL插入到文章内容中
2. **设置网站Logo**：在博客设置中使用返回的图片URL
3. **用户头像**：在用户管理中使用返回的图片URL（预留功能）

### 5.3 文件管理建议

- 定期清理无用文件，释放存储空间
- 为文件设置合理的访问权限
- 对重要文件进行备份
- 优化图片大小，提高网站加载速度

## 6. 博客设置

### 6.1 基本设置

调用更新博客设置API：

```
PUT http://localhost:8080/api/admin/settings
Content-Type: application/json
Authorization: Bearer <token>

{
  "siteName": "Meblog",
  "siteDescription": "一个功能完整的博客系统",
  "siteLogo": "http://localhost:9000/meblog/logo.png",
  "author": "aipno",
  "email": "aipno@example.com"
}
```

### 6.2 SEO设置

可以设置网站的SEO信息，如标题、关键词、描述等（预留功能）。

### 6.3 社交链接设置

可以配置网站的社交链接，如GitHub、微博、微信等（预留功能）。

## 7. Wiki管理

### 7.1 Wiki概述

Wiki是Meblog-Java的一个重要扩展功能，用于创建和管理知识库。Wiki支持树形结构，可以创建父子页面关系。

### 7.2 创建Wiki页面

调用创建Wiki API：

```
POST http://localhost:8080/api/admin/wiki
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "Wiki首页",
  "content": "# Wiki首页\n这是Wiki的首页内容。",
  "parentId": 0,
  "sort": 1
}
```

### 7.3 Wiki页面层级

与分类管理类似，Wiki也支持多级页面：

```
POST http://localhost:8080/api/admin/wiki
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "Java基础",
  "content": "# Java基础\nJava基础教程。",
  "parentId": 1, // 父页面ID
  "sort": 1
}
```

### 7.4 Wiki使用场景

- 企业内部知识库
- 产品文档管理
- 技术文档中心
- 帮助中心

## 8. 统计分析

### 8.1 网站统计

调用统计API获取网站统计信息：

```
GET http://localhost:8080/api/statistics/site
```

返回结果包括：

- 文章总数
- 分类总数
- 标签总数
- 总访问量

### 8.2 文章统计

可以查看单篇文章的统计信息，如阅读量、点赞数、评论数等（预留功能）。

### 8.3 访问统计

可以查看网站的访问统计，如每日访问量、访客来源、热门页面等（预留功能）。

## 9. 系统维护

### 9.1 数据备份

建议定期备份数据库和文件存储，以防止数据丢失。

#### 9.1.1 数据库备份

使用MySQL的备份命令备份数据库：

```bash
mysqldump -u root -p meblog > meblog_backup.sql
```

#### 9.1.2 文件备份

备份Minio存储的文件：

```bash
# 备份整个存储桶
mc cp --recursive minio/meblog /backup/meblog
```

### 9.2 日志管理

系统日志默认存储在以下位置：

```
meblog-web/logs/meblog-web.log
meblog-admin/logs/meblog-admin.log
```

建议定期清理旧日志，避免日志文件过大。

### 9.3 性能优化

#### 9.3.1 数据库优化

- 为频繁查询的字段创建索引
- 优化SQL查询，避免全表扫描
- 定期优化表结构

#### 9.3.2 缓存优化

- 合理设置缓存过期时间
- 对频繁访问的数据进行缓存
- 避免缓存雪崩和缓存穿透

#### 9.3.3 图片优化

- 压缩图片大小
- 使用适当的图片格式（如WebP）
- 实现图片懒加载

## 10. 最佳实践

### 10.1 文章写作建议

- **标题**：简洁明了，包含关键词
- **摘要**：概括文章主要内容，吸引读者阅读
- **内容**：结构清晰，段落分明，使用Markdown格式
- **图片**：使用高质量图片，添加适当的alt属性
- **标签**：选择相关的标签，提高文章的可发现性

### 10.2 分类标签管理建议

- **分类**：按照主题或领域进行分类，层级不要过深
- **标签**：使用具体、明确的标签，避免使用过于宽泛的标签
- **数量**：每个分类下的文章数量不要过少，标签数量不要过多

### 10.3 安全建议

- 定期更新系统和依赖库
- 使用强密码，并定期更换
- 限制登录失败次数，防止暴力破解
- 开启HTTPS，保护数据传输安全
- 定期备份数据，防止数据丢失

### 10.4 SEO优化建议

- 为文章设置合适的标题和摘要
- 使用语义化的HTML标签
- 优化网站加载速度
- 建立合理的内部链接结构
- 提交网站地图到搜索引擎

## 11. 常见问题

### 11.1 如何修改网站域名？

修改配置文件中的`site.domain`和`site.external-link-domain`配置项。

### 11.2 如何更换网站Logo？

在博客设置中上传新的Logo图片，并更新`siteLogo`配置项。

### 11.3 如何添加新的管理员？

调用用户管理API创建新用户，并为其分配管理员角色。

### 11.4 如何迁移数据到新服务器？

1. 备份旧服务器的数据库和文件
2. 在新服务器上安装相同版本的系统
3. 恢复数据库和文件
4. 修改配置文件中的相关配置

### 11.5 如何提高网站访问速度？

- 优化数据库查询
- 使用缓存
- 压缩图片和静态资源
- 使用CDN加速
- 优化代码结构

## 12. 联系支持

如果您在使用过程中遇到问题，可以通过以下方式获取帮助：

- 查看[常见问题](/faq)
- 查阅[API文档](/api)
- 提交[Issue](<项目仓库地址>/issues)
- 联系项目维护者

## 13. 后续学习

- [开发指南](/dev-guide)：学习如何开发和扩展Meblog-Java
- [更新日志](/changelog)：了解系统的更新和变化
- [技术栈](/tech-stack)：深入了解系统使用的技术栈

通过本文档，您应该已经掌握了Meblog-Java的基本使用方法。如果您想进一步了解系统的开发和扩展，可以查看[开发指南](/dev-guide)。