# API文档

Meblog-Java提供了完整的RESTful API，用于前端与后端的交互。本文档将详细介绍Meblog-Java的API设计规范、主要接口和调用示例。

## 1. API设计规范

### 1.1 基本规范

- **API风格**：RESTful API
- **请求方法**：GET、POST、PUT、DELETE
- **数据格式**：JSON
- **字符编码**：UTF-8
- **认证方式**：JWT（JSON Web Token）

### 1.2 URL设计

```
# 前端API
http://localhost:8080/api/{resource}/{action}

# 管理后台API
http://localhost:8080/api/admin/{resource}/{action}
```

### 1.3 响应格式

所有API返回统一的响应格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

| 字段 | 类型 | 说明 |
|------|------|------|
| code | Integer | 响应码，200表示成功，其他表示失败 |
| message | String | 响应消息，描述操作结果 |
| data | Object | 响应数据，根据不同接口返回不同数据 |

### 1.4 错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未授权，需要登录 |
| 403 | 禁止访问，没有权限 |
| 404 | 请求资源不存在 |
| 500 | 服务器内部错误 |

### 1.5 认证方式

管理后台API需要在请求头中携带JWT令牌：

```
Authorization: Bearer <token>
```

## 2. 前端API

### 2.1 文章API

#### 2.1.1 获取文章列表

- **URL**：`/api/article/list`
- **方法**：GET
- **参数**：
  - page：页码，默认1
  - size：每页条数，默认10
  - categoryId：分类ID，可选
  - tagId：标签ID，可选
  - keyword：关键词，可选
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "测试文章",
        "summary": "这是一篇测试文章的摘要",
        "categoryId": 1,
        "categoryName": "技术博客",
        "tags": [
          {
            "id": 1,
            "name": "Java"
          }
        ],
        "viewCount": 100,
        "createTime": "2023-01-01 12:00:00"
      }
    ],
    "total": 1,
    "size": 10,
    "current": 1,
    "pages": 1
  }
}
```

#### 2.1.2 获取文章详情

- **URL**：`/api/article/detail/{id}`
- **方法**：GET
- **参数**：
  - id：文章ID
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "测试文章",
    "content": "# 测试文章内容\n这是一篇测试文章。",
    "summary": "这是一篇测试文章的摘要",
    "categoryId": 1,
    "categoryName": "技术博客",
    "tags": [
      {
        "id": 1,
        "name": "Java"
      }
    ],
    "viewCount": 100,
    "createTime": "2023-01-01 12:00:00",
    "updateTime": "2023-01-01 12:00:00"
  }
}
```

#### 2.1.3 获取热门文章

- **URL**：`/api/article/hot`
- **方法**：GET
- **参数**：
  - limit：数量，默认10
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "title": "测试文章",
      "viewCount": 100
    }
  ]
}
```

### 2.2 分类API

#### 2.2.1 获取分类列表

- **URL**：`/api/category/list`
- **方法**：GET
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "技术博客",
      "parentId": 0,
      "children": [],
      "articleCount": 10
    }
  ]
}
```

### 2.3 标签API

#### 2.3.1 获取标签列表

- **URL**：`/api/tag/list`
- **方法**：GET
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "Java",
      "articleCount": 5
    }
  ]
}
```

### 2.4 搜索API

#### 2.4.1 全文搜索

- **URL**：`/api/search`
- **方法**：GET
- **参数**：
  - q：搜索关键词
  - page：页码，默认1
  - size：每页条数，默认10
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "测试文章",
        "summary": "这是一篇测试文章的摘要",
        "categoryName": "技术博客",
        "createTime": "2023-01-01 12:00:00"
      }
    ],
    "total": 1,
    "size": 10,
    "current": 1,
    "pages": 1
  }
}
```

### 2.5 统计API

#### 2.5.1 获取网站统计信息

- **URL**：`/api/statistics/site`
- **方法**：GET
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "articleCount": 100,
    "categoryCount": 10,
    "tagCount": 50,
    "viewCount": 10000
  }
}
```

### 2.6 归档API

#### 2.6.1 获取文章归档

- **URL**：`/api/archive/list`
- **方法**：GET
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "year": "2023",
      "month": "01",
      "articles": [
        {
          "id": 1,
          "title": "测试文章",
          "createTime": "2023-01-01 12:00:00"
        }
      ]
    }
  ]
}
```

### 2.7 博客设置API

#### 2.7.1 获取博客设置

- **URL**：`/api/settings`
- **方法**：GET
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "siteName": "Meblog",
    "siteDescription": "一个功能完整的博客系统",
    "siteLogo": "http://localhost:9000/meblog/logo.png",
    "author": "aipno",
    "email": "aipno@example.com"
  }
}
```

### 2.8 Wiki API

#### 2.8.1 获取Wiki列表

- **URL**：`/api/wiki/list`
- **方法**：GET
- **参数**：
  - parentId：父节点ID，默认0
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "title": "Wiki首页",
      "parentId": 0,
      "children": []
    }
  ]
}
```

#### 2.8.2 获取Wiki详情

- **URL**：`/api/wiki/detail/{id}`
- **方法**：GET
- **参数**：
  - id：Wiki ID
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "Wiki首页",
    "content": "# Wiki首页\n这是Wiki的首页内容。",
    "parentId": 0,
    "createTime": "2023-01-01 12:00:00",
    "updateTime": "2023-01-01 12:00:00"
  }
}
```

## 3. 管理后台API

### 3.1 认证API

#### 3.1.1 管理员登录

- **URL**：`/api/admin/auth/login`
- **方法**：POST
- **参数**：
  - username：用户名
  - password：密码
- **请求示例**：

```json
{
  "username": "admin",
  "password": "123456"
}
```

- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": 1,
      "username": "admin",
      "email": "admin@example.com",
      "role": {
        "id": 1,
        "name": "管理员"
      }
    }
  }
}
```

### 3.2 文章管理API

#### 3.2.1 创建文章

- **URL**：`/api/admin/article`
- **方法**：POST
- **参数**：
  - title：文章标题
  - content：文章内容
  - summary：文章摘要
  - categoryId：分类ID
  - tagIds：标签ID列表
  - status：状态，1表示已发布，0表示草稿
- **请求示例**：

```json
{
  "title": "测试文章",
  "content": "# 测试文章内容\n这是一篇测试文章。",
  "summary": "这是一篇测试文章的摘要",
  "categoryId": 1,
  "tagIds": [1, 2],
  "status": 1
}
```

#### 3.2.2 更新文章

- **URL**：`/api/admin/article/{id}`
- **方法**：PUT
- **参数**：与创建文章相同

#### 3.2.3 删除文章

- **URL**：`/api/admin/article/{id}`
- **方法**：DELETE

#### 3.2.4 获取文章列表

- **URL**：`/api/admin/article/list`
- **方法**：GET
- **参数**：
  - page：页码，默认1
  - size：每页条数，默认10
  - status：状态，可选
  - keyword：关键词，可选

### 3.3 分类管理API

#### 3.3.1 创建分类

- **URL**：`/api/admin/category`
- **方法**：POST
- **参数**：
  - name：分类名称
  - parentId：父分类ID，默认0
  - sort：排序，默认0
- **请求示例**：

```json
{
  "name": "技术博客",
  "parentId": 0,
  "sort": 1
}
```

#### 3.3.2 更新分类

- **URL**：`/api/admin/category/{id}`
- **方法**：PUT
- **参数**：与创建分类相同

#### 3.3.3 删除分类

- **URL**：`/api/admin/category/{id}`
- **方法**：DELETE

### 3.4 标签管理API

#### 3.4.1 创建标签

- **URL**：`/api/admin/tag`
- **方法**：POST
- **参数**：
  - name：标签名称
- **请求示例**：

```json
{
  "name": "Java"
}
```

#### 3.4.2 更新标签

- **URL**：`/api/admin/tag/{id}`
- **方法**：PUT
- **参数**：与创建标签相同

#### 3.4.3 删除标签

- **URL**：`/api/admin/tag/{id}`
- **方法**：DELETE

### 3.5 用户管理API

#### 3.5.1 创建用户

- **URL**：`/api/admin/user`
- **方法**：POST
- **参数**：
  - username：用户名
  - password：密码
  - email：邮箱
  - roleId：角色ID
- **请求示例**：

```json
{
  "username": "user",
  "password": "123456",
  "email": "user@example.com",
  "roleId": 2
}
```

#### 3.5.2 更新用户

- **URL**：`/api/admin/user/{id}`
- **方法**：PUT
- **参数**：与创建用户相同，密码可选

#### 3.5.3 删除用户

- **URL**：`/api/admin/user/{id}`
- **方法**：DELETE

### 3.6 角色管理API

#### 3.6.1 创建角色

- **URL**：`/api/admin/role`
- **方法**：POST
- **参数**：
  - name：角色名称
  - description：角色描述
  - permissionIds：权限ID列表
- **请求示例**：

```json
{
  "name": "编辑",
  "description": "可以编辑文章",
  "permissionIds": [1, 2, 3]
}
```

#### 3.6.2 更新角色

- **URL**：`/api/admin/role/{id}`
- **方法**：PUT
- **参数**：与创建角色相同

#### 3.6.3 删除角色

- **URL**：`/api/admin/role/{id}`
- **方法**：DELETE

### 3.7 权限管理API

#### 3.7.1 获取权限列表

- **URL**：`/api/admin/permission/list`
- **方法**：GET
- **响应示例**：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "name": "文章管理",
      "code": "article:manage",
      "url": "/api/admin/article",
      "parentId": 0,
      "children": []
    }
  ]
}
```

### 3.8 文件管理API

#### 3.8.1 上传文件

- **URL**：`/api/admin/file/upload`
- **方法**：POST
- **参数**：
  - file：文件对象（multipart/form-data格式）
- **响应示例**：

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

### 3.9 博客设置API

#### 3.9.1 更新博客设置

- **URL**：`/api/admin/settings`
- **方法**：PUT
- **参数**：
  - siteName：网站名称
  - siteDescription：网站描述
  - siteLogo：网站Logo
  - author：作者
  - email：邮箱
- **请求示例**：

```json
{
  "siteName": "Meblog",
  "siteDescription": "一个功能完整的博客系统",
  "siteLogo": "http://localhost:9000/meblog/logo.png",
  "author": "aipno",
  "email": "aipno@example.com"
}
```

### 3.10 Wiki管理API

#### 3.10.1 创建Wiki

- **URL**：`/api/admin/wiki`
- **方法**：POST
- **参数**：
  - title：Wiki标题
  - content：Wiki内容
  - parentId：父节点ID，默认0
  - sort：排序，默认0
- **请求示例**：

```json
{
  "title": "Wiki首页",
  "content": "# Wiki首页\n这是Wiki的首页内容。",
  "parentId": 0,
  "sort": 1
}
```

#### 3.10.2 更新Wiki

- **URL**：`/api/admin/wiki/{id}`
- **方法**：PUT
- **参数**：与创建Wiki相同

#### 3.10.3 删除Wiki

- **URL**：`/api/admin/wiki/{id}`
- **方法**：DELETE

## 4. API测试工具

### 4.1 Swagger UI

Meblog-Java集成了Springdoc OpenAPI，可以通过Swagger UI在线测试API：

```
http://localhost:8080/swagger-ui.html
```

### 4.2 Postman

您也可以使用Postman工具来测试API：

1. 下载并安装Postman
2. 创建请求，设置URL、方法和参数
3. 添加认证信息（如果需要）
4. 发送请求，查看响应结果

## 5. API调用示例

### 5.1 使用JavaScript调用API

```javascript
// 获取文章列表
fetch('http://localhost:8080/api/article/list')
  .then(response => response.json())
  .then(data => console.log(data));

// 管理员登录
fetch('http://localhost:8080/api/admin/auth/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    username: 'admin',
    password: '123456'
  })
})
  .then(response => response.json())
  .then(data => {
    const token = data.data.token;
    // 使用token调用需要认证的API
    fetch('http://localhost:8080/api/admin/article/list', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
      .then(response => response.json())
      .then(data => console.log(data));
  });
```

### 5.2 使用Java调用API

```java
// 使用RestTemplate调用API
RestTemplate restTemplate = new RestTemplate();

// 获取文章列表
String url = "http://localhost:8080/api/article/list";
ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
System.out.println(response.getBody());

// 管理员登录
url = "http://localhost:8080/api/admin/auth/login";
Map<String, String> requestBody = new HashMap<>();
requestBody.put("username", "admin");
requestBody.put("password", "123456");
ResponseEntity<String> loginResponse = restTemplate.postForEntity(url, requestBody, String.class);
// 解析token并调用需要认证的API
```

## 6. API版本管理

Meblog-Java的API版本管理采用URL前缀方式，例如：

```
http://localhost:8080/api/v1/article/list
```

当前版本为v1，后续版本将在URL中体现。

## 7. 最佳实践

1. **使用HTTPS**：在生产环境中，建议使用HTTPS协议来保证数据传输的安全性
2. **合理设置token过期时间**：根据实际需求设置token的过期时间，一般建议30分钟到2小时
3. **使用缓存**：对于频繁访问的数据，建议使用缓存来提高性能
4. **限流和熔断**：在高并发场景下，建议实现限流和熔断机制，保护系统稳定
5. **日志记录**：记录API调用日志，便于问题排查和分析
6. **参数校验**：在前端和后端都进行参数校验，确保数据的合法性
7. **错误处理**：统一错误处理机制，返回友好的错误信息

## 8. 后续规划

1. 增加更多API接口，支持更多功能
2. 完善API文档，提供更详细的接口说明和示例
3. 实现API版本管理，支持平滑升级
4. 提供SDK，便于不同语言调用API
5. 实现API网关，提供统一的API入口和管理

通过本文档，您可以了解Meblog-Java提供的API接口和使用方法。如果您在使用过程中遇到问题，可以查看[常见问题](/faq)或联系项目维护者。