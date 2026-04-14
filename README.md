# 宠物心情识别Android应用

## 项目简介
本项目是一个基于Android的宠物心情识别应用，使用Java开发前端，Python开发后端，实现宠物姿态识别和心情分析功能。

## 技术栈
- **前端**：Java、Android SDK 34
- **后端**：Python、Flask
- **数据库**：SQLite
- **模型**：YOLO或其他开源模型

## 项目结构

### 前端（Android）
```
app/src/main/java/edu/hebut/test1/
├── db/
│   ├── DatabaseHelper.java    # 数据库帮助类
│   └── UserDao.java           # 用户数据访问对象
├── network/
│   └── ApiClient.java         # 网络请求工具类
├── LoginActivity.java         # 登录活动
└── MainActivity.java          # 主活动
```

### 后端（Python）
后端文件应放置在 `D:\Android\pyteat` 目录下：
```
D:/Android/pyteat/
└── app.py                     # Flask后端服务
```

## 功能实现

### 1. 登录系统
- 实现用户注册和登录功能
- 使用SQLite存储用户账号密码

### 2. 前后端通信
- 使用HTTP POST请求与后端Python服务通信
- 实现简单的通信测试用例

## 后续功能规划
1. **宠物姿态识别**：使用YOLO模型识别猫狗姿态
2. **心情分析**：基于姿态分析宠物心情
3. **面部微表情识别**：后续添加
4. **情绪日报**：生成宠物心情分布和行为分布报告
5. **异常行为警告**：识别宠物异常行为并提供就医建议
6. **大模型集成**：接入大模型提供饲养建议

## 开发环境配置
- **Android Gradle Plugin**：8.4.0
- **Gradle**：8.6
- **compileSdk**：34
- **minSdk**：24
- **targetSdk**：34
- **Java**：11

## 运行步骤

### 1. 启动后端服务
在 `D:\Android\pyteat` 目录下执行：
```bash
pip install flask
python app.py
```

### 2. 运行Android应用
- 使用Android Studio打开项目
- 构建并运行应用
- 测试登录、注册和前后端通信功能

## 注意事项
- 确保后端服务运行在 `http://localhost:5000`
- 确保Android设备或模拟器可以访问后端服务
- 首次运行时会自动创建SQLite数据库