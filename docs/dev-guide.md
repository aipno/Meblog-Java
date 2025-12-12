# 开发指南

本文档将详细介绍Meblog-Java的开发相关内容，包括代码结构、开发环境搭建、代码贡献流程、开发规范等，帮助开发者参与Meblog的开发。

## 1. 代码结构

Meblog-Java采用了多模块架构设计，主要包括以下模块：

### 1.1 项目结构

```
meblog/                  # 主项目目录
├── meblog-web/          # 主应用模块，处理前端HTTP请求
├── meblog-admin/        # 管理后台模块，提供管理功能
├── meblog-common/       # 公共模块，包含通用工具类和模型
├── meblog-jwt/          # JWT认证模块
├── meblog-search/       # 搜索模块，基于Lucene实现
└── pom.xml              # 父项目POM文件
```

### 1.2 模块结构

以`meblog-web`模块为例，其结构如下：

```
meblog-web/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── cn/iswxl/meblog/web/
│   │   │       ├── config/          # 配置类
│   │   │       ├── controller/      # 控制器
│   │   │       ├── convert/         # 对象转换
│   │   │       ├── service/         # 业务逻辑
│   │   │       ├── utils/           # 工具类
│   │   │       └── MeblogWebApplication.java  # 应用入口
│   │   └── resources/               # 资源文件
│   │       ├── application.yml      # 主配置文件
│   │       ├── application-dev.yml  # 开发环境配置
│   │       └── application-prod.yml # 生产环境配置
│   └── test/                        # 测试代码
└── pom.xml                          # 模块POM文件
```

## 2. 开发环境搭建

### 2.1 环境要求

- JDK 11+
- Maven 3.6+
- Git 2.x+
- MySQL 8.x
- Redis 6.x
- Minio 2023.x
- IntelliJ IDEA 2020.3+（推荐）

### 2.2 安装步骤

1. **安装JDK**：下载并安装JDK 11或更高版本，设置`JAVA_HOME`环境变量
2. **安装Maven**：下载并安装Maven 3.6或更高版本，设置`MAVEN_HOME`环境变量
3. **安装Git**：下载并安装Git 2.x或更高版本
4. **安装MySQL**：下载并安装MySQL 8.x，创建数据库`meblog`
5. **安装Redis**：下载并安装Redis 6.x，启动Redis服务
6. **安装Minio**：下载并安装Minio 2023.x，启动Minio服务
7. **安装IntelliJ IDEA**：下载并安装IntelliJ IDEA 2020.3或更高版本

### 2.3 导入项目

1. 克隆项目：`git clone <项目仓库地址>`
2. 打开IntelliJ IDEA，选择`Open`，找到项目目录
3. 等待Maven依赖下载完成
4. 配置JDK版本为11+
5. 配置Maven版本

### 2.4 配置文件修改

修改`meblog-web/src/main/resources/application-dev.yml`文件，配置数据库、Redis、Minio等信息。

## 3. 代码贡献流程

### 3.1 分支管理

- **master**：主分支，用于发布稳定版本
- **develop**：开发分支，用于集成新功能
- **feature/xxx**：功能分支，用于开发新功能
- **bugfix/xxx**：bug修复分支，用于修复bug
- **release/xxx**：发布分支，用于准备发布版本
- **hotfix/xxx**：热修复分支，用于修复生产环境bug

### 3.2 开发流程

1. **创建分支**：从`develop`分支创建功能分支或bug修复分支
2. **开发代码**：在分支上开发代码，遵循开发规范
3. **编写测试**：编写单元测试和集成测试
4. **提交代码**：提交代码到本地仓库，编写清晰的提交信息
5. **推送分支**：将分支推送到远程仓库
6. **创建PR**：创建Pull Request，请求合并到`develop`分支
7. **代码审查**：等待其他开发者审查代码
8. **合并分支**：审查通过后，合并分支到`develop`
9. **删除分支**：删除已合并的分支

### 3.3 提交信息规范

提交信息应遵循以下格式：

```
<类型>: <描述>

<详细说明>（可选）
```

类型包括：

- **feat**：新功能
- **fix**：bug修复
- **docs**：文档更新
- **style**：代码风格修改
- **refactor**：代码重构
- **test**：测试代码修改
- **chore**：构建或辅助工具修改

例如：

```
feat: 添加文章评论功能

- 实现文章评论的创建、查询、删除功能
- 添加评论数量统计
- 更新API文档
```

## 4. 开发规范

### 4.1 命名规范

- **包名**：小写字母，使用`.`分隔，如`cn.iswxl.meblog.web`
- **类名**：首字母大写的驼峰命名，如`ArticleController`
- **方法名**：首字母小写的驼峰命名，如`getArticleList`
- **变量名**：首字母小写的驼峰命名，如`articleId`
- **常量名**：全大写，使用`_`分隔，如`MAX_PAGE_SIZE`
- **接口名**：首字母大写的驼峰命名，使用`I`前缀（可选），如`IArticleService`
- **实现类名**：首字母大写的驼峰命名，使用`Impl`后缀，如`ArticleServiceImpl`

### 4.2 代码风格

- 使用4个空格缩进
- 每行最多120个字符
- 方法之间空一行
- 类成员之间空一行
- 大括号不换行
- 运算符前后加空格
- 逗号后加空格

### 4.3 注释规范

- **类注释**：使用Javadoc注释，说明类的功能和用途
- **方法注释**：使用Javadoc注释，说明方法的功能、参数、返回值和异常
- **变量注释**：对关键变量添加注释
- **代码块注释**：对复杂的代码块添加注释

例如：

```java
/**
 * 文章控制器
 * 处理文章相关的HTTP请求
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    
    /**
     * 获取文章列表
     * 
     * @param page     页码
     * @param size     每页条数
     * @param categoryId 分类ID
     * @param tagId    标签ID
     * @param keyword  关键词
     * @return 文章列表
     */
    @GetMapping("/list")
    public Result<Page<ArticleVO>> getArticleList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) String keyword) {
        // 实现代码
    }
}
```

### 4.4 API设计规范

- 遵循RESTful API设计规范
- 使用合适的HTTP方法：GET、POST、PUT、DELETE
- 使用有意义的URL路径
- 使用JSON格式返回数据
- 返回统一的响应格式
- 使用合适的HTTP状态码
- 添加必要的请求参数和响应参数
- 提供API文档

## 5. 测试指南

### 5.1 测试类型

- **单元测试**：测试单个类或方法
- **集成测试**：测试多个组件的集成
- **功能测试**：测试系统的功能
- **性能测试**：测试系统的性能
- **安全测试**：测试系统的安全性

### 5.2 测试框架

- **JUnit 5**：单元测试框架
- **Spring Boot Test**：集成测试框架
- **Mockito**：模拟对象框架
- **AssertJ**：断言库

### 5.3 编写测试用例

测试用例应遵循以下原则：

- 测试用例应独立，不依赖其他测试用例
- 测试用例应覆盖主要功能和边界情况
- 测试用例应易于理解和维护
- 测试用例应包含断言
- 测试用例应添加必要的注释

例如：

```java
@SpringBootTest
class ArticleServiceTest {
    
    @Autowired
    private ArticleService articleService;
    
    @Test
    void testGetArticleList() {
        // 准备测试数据
        ArticleQuery query = new ArticleQuery();
        query.setPage(1);
        query.setSize(10);
        
        // 执行测试
        Page<ArticleVO> result = articleService.getArticleList(query);
        
        // 断言结果
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getRecords());
    }
}
```

### 5.4 运行测试

1. **在IDE中运行**：在IntelliJ IDEA中右键点击测试类或方法，选择`Run`
2. **使用Maven运行**：`mvn test`
3. **运行特定测试**：`mvn test -Dtest=ArticleServiceTest`

## 6. 部署指南

### 6.1 开发环境部署

1. 启动MySQL、Redis、Minio服务
2. 在IDE中运行`MeblogWebApplication`和`MeblogAdminApplication`类
3. 访问API文档：`http://localhost:8080/swagger-ui.html`

### 6.2 生产环境部署

1. **编译项目**：`mvn clean install -DskipTests`
2. **准备配置文件**：创建生产环境配置文件`application-prod.yml`
3. **启动应用**：使用`nohup`或`systemd`启动应用

```bash
# 使用nohup启动
nohup java -jar meblog-web-0.7.0.jar --spring.profiles.active=prod > meblog-web.log 2>&1 &
nohup java -jar meblog-admin-0.7.0.jar --spring.profiles.active=prod > meblog-admin.log 2>&1 &
```

4. **配置Nginx**：配置Nginx反向代理，支持HTTPS

```nginx
server {
    listen 80;
    server_name example.com;
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl;
    server_name example.com;
    
    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;
    
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
    
    location /admin {
        proxy_pass http://localhost:8081;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

## 7. 监控和日志

### 7.1 日志配置

日志使用Logback框架，配置文件位于`src/main/resources/templates/logback-meblog.xml`。

日志级别包括：

- **TRACE**：最详细的日志，用于调试
- **DEBUG**：调试日志，用于开发
- **INFO**：信息日志，用于生产环境
- **WARN**：警告日志，用于潜在问题
- **ERROR**：错误日志，用于错误情况

### 7.2 日志文件

日志文件默认存储在：

```
meblog-web/logs/meblog-web.log
meblog-admin/logs/meblog-admin.log
```

日志文件按天分割，保留30天的日志。

### 7.3 监控

后续版本将集成Prometheus和Grafana，实现系统监控。

## 8. 性能优化

### 8.1 数据库优化

- 为频繁查询的字段创建索引
- 优化SQL查询，避免全表扫描
- 使用分页查询，避免一次性查询大量数据
- 定期优化表结构
- 使用读写分离（预留功能）

### 8.2 缓存优化

- 对频繁访问的数据进行缓存
- 合理设置缓存过期时间
- 使用缓存预热机制
- 避免缓存雪崩和缓存穿透
- 使用多级缓存（本地缓存+Redis）

### 8.3 代码优化

- 减少数据库查询次数
- 使用批量操作
- 避免不必要的计算
- 使用异步处理
- 优化算法和数据结构

### 8.4 服务器优化

- 配置合适的JVM参数
- 优化Tomcat配置
- 使用CDN加速静态资源
- 启用HTTP/2
- 使用负载均衡

## 9. 安全最佳实践

### 9.1 认证授权

- 使用JWT进行身份认证
- 使用Spring Security进行权限控制
- 实现基于角色的访问控制（RBAC）
- 定期更换JWT密钥
- 设置合理的JWT过期时间

### 9.2 输入验证

- 对所有输入进行验证
- 使用Bean Validation进行参数校验
- 过滤和转义输入内容
- 防止SQL注入、XSS攻击、CSRF攻击

### 9.3 输出编码

- 对所有输出进行编码
- 防止XSS攻击
- 使用Content Security Policy（CSP）

### 9.4 安全配置

- 使用HTTPS
- 配置安全的密码策略
- 定期更新依赖库
- 关闭不必要的服务和端口
- 配置防火墙规则

## 10. 常见开发问题

### 10.1 如何添加新的依赖？

在模块的`pom.xml`文件中添加依赖：

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>example-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 10.2 如何添加新的配置项？

在配置类中添加配置项：

```java
@ConfigurationProperties(prefix = "example")
@Component
@Data
public class ExampleProperties {
    private String name;
    private String value;
}
```

然后在`application.yml`文件中配置：

```yaml
example:
  name: example
  value: 123
```

### 10.3 如何添加新的API接口？

1. 在`controller`包中创建控制器类
2. 添加`@RestController`和`@RequestMapping`注解
3. 编写API方法，添加相应的注解
4. 在`service`包中实现业务逻辑
5. 编写测试用例
6. 更新API文档

### 10.4 如何调试代码？

1. 在IDE中设置断点
2. 启动应用的调试模式
3. 发送请求，触发断点
4. 查看变量值和调用栈
5. 单步调试，查看代码执行流程

## 11. 后续学习

- [Spring Boot官方文档](https://spring.io/projects/spring-boot)
- [Spring Security官方文档](https://spring.io/projects/spring-security)
- [Redis官方文档](https://redis.io/documentation)
- [Lucene官方文档](https://lucene.apache.org/core/documentation.html)
- [Minio官方文档](https://min.io/docs/)
- [MyBatis Plus官方文档](https://baomidou.com/guide/)

## 12. 联系我们

如果您在开发过程中遇到问题，可以通过以下方式获取帮助：

- 查看[常见问题](/faq)
- 提交[Issue](<项目仓库地址>/issues)
- 联系项目维护者

## 13. 贡献者

感谢所有为Meblog-Java做出贡献的开发者！

## 14. 许可证

Meblog-Java采用MIT许可证，详情请查看[LICENSE](<项目仓库地址>/LICENSE)文件。