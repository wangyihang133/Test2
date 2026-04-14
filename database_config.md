# 数据库配置说明

## 数据库类型
- **类型**：SQLite
- **文件名**：pet_emotion.db
- **版本**：1

## 表结构

### users表
| 字段名 | 数据类型 | 约束 | 描述 |
|-------|---------|------|------|
| id | INTEGER | PRIMARY KEY AUTOINCREMENT | 用户ID |
| username | TEXT | UNIQUE NOT NULL | 用户名 |
| password | TEXT | NOT NULL | 密码 |

## 数据库操作

### 初始化数据库
- 首次启动应用时，系统会自动创建数据库和users表
- 数据库文件存储在应用的私有目录中

### 数据库升级
- 当数据库版本号变更时，系统会自动执行onUpgrade方法
- 升级过程会删除旧表并重新创建新表

## 安全注意事项
- 密码以明文形式存储在数据库中，建议在生产环境中使用加密存储
- 数据库文件位于应用的私有目录，仅应用本身可访问

## 相关文件
- `app/src/main/java/edu/hebut/test1/db/DatabaseHelper.java` - 数据库帮助类
- `app/src/main/java/edu/hebut/test1/db/UserDao.java` - 用户数据访问对象

## 使用示例

### 注册用户
```java
UserDao userDao = new UserDao(context);
boolean success = userDao.register("username", "password");
```

### 登录验证
```java
UserDao userDao = new UserDao(context);
boolean success = userDao.login("username", "password");
```