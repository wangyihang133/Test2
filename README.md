# 宠物心情识别系统

## 项目概述

宠物心情识别系统是一个基于Android和Python的应用，用于识别宠物（猫/狗）的姿态和心情。系统通过分析宠物的图片和视频，识别其姿态并推断其心情状态，为宠物主人提供有价值的信息。

## 技术栈

### 前端（Android）
- **开发语言**：Java
- **Android SDK**：34
- **Gradle**：8.6
- **Android Gradle Plugin**：8.4.0
- **数据库**：SQLite
- **网络通信**：HttpURLConnection

### 后端（Python）
- **框架**：Flask
- **图像处理**：OpenCV
- **深度学习**：PyTorch/TensorFlow (计划集成YOLO)
- **视频处理**：OpenCV/MoviePy
- **数据存储**：JSON/SQLite

## 项目结构

### 前端结构

```
app/src/main/java/edu/hebut/test1/
├── db/                  # 数据库相关
│   ├── DatabaseHelper.java    # 数据库帮助类
│   └── UserDao.java           # 用户数据访问对象
├── network/             # 网络通信相关
│   └── ApiClient.java         # 网络请求工具类
├── LoginActivity.java         # 登录活动
└── MainActivity.java          # 主活动
```

### 后端结构

```
D:/Android/pyteat/
├── app.py                     # Flask后端服务
├── models/                    # 模型目录
│   └── yolo/                  # YOLO模型
├── utils/                     # 工具函数
│   ├── image_processing.py    # 图像处理
│   └── video_processing.py    # 视频处理
└── data/                      # 数据存储
    └── reports/               # 报告数据
```

## 功能说明

### 前端功能

1. **用户系统**
   - 用户注册和登录
   - 本地SQLite数据库存储

2. **主界面**
   - 拍照识别：拍摄宠物照片并分析
   - 视频分析：上传或录制视频进行分析
   - 查看报告：查看历史分析报告

3. **前后端通信**
   - 测试通信连接
   - 发送图片和视频数据
   - 接收分析结果

### 后端功能

1. **姿态识别**
   - 使用YOLO模型识别宠物姿态
   - 支持坐、卧、站、走等多种姿态

2. **心情分析**
   - 基于姿态分析宠物心情
   - 支持平静、放松、兴奋、焦虑等心情状态

3. **视频分析**
   - 分析长视频中的宠物行为
   - 识别行为模式和心情变化

4. **报告生成**
   - 生成日/周/月心情报告
   - 分析心情分布和行为模式

5. **异常检测**
   - 识别宠物异常行为
   - 提供就医建议

## API接口

### 测试接口
- **POST /test** - 测试前后端通信
  - 请求：`{"message": "Hello from Android"}`
  - 响应：`{"response": "Backend received: Hello from Android", "status": "success"}`

### 分析接口
- **POST /analyze/image** - 分析单张图片
  - 请求：`{"image": "base64编码的图片数据"}`
  - 响应：`{"pose": {"pose": "sitting", "confidence": 0.95}, "emotion": {"emotion": "calm", "confidence": 0.9}, "status": "success"}`

- **POST /analyze/video** - 分析视频文件
  - 请求：`{"video_url": "视频文件URL"}`
  - 响应：`{"pose": {"pose": "lying", "confidence": 0.92}, "emotion": {"emotion": "relaxed", "confidence": 0.85}, "status": "success"}`

### 报告接口
- **GET /reports** - 获取历史报告
  - 响应：`{"reports": [{"date": "2026-04-14", "emotions": {...}, "behaviors": {...}}], "status": "success"}`

## 数据库配置

### SQLite数据库
- **数据库文件**：pet_emotion.db
- **表结构**：
  - users表：存储用户账号信息
    | 字段名 | 数据类型 | 约束 | 描述 |
    |-------|---------|------|------|
    | id | INTEGER | PRIMARY KEY AUTOINCREMENT | 用户ID |
    | username | TEXT | UNIQUE NOT NULL | 用户名 |
    | password | TEXT | NOT NULL | 密码 |

## 部署和运行

### 后端部署

1. **环境准备**
   - 安装Python 3.7+
   - 安装依赖：`pip install flask opencv-python numpy`

2. **启动服务**
   - 进入`D:/Android/pyteat`目录
   - 运行：`python app.py`
   - 服务将在`http://192.168.3.8:5000`启动

### 前端运行

1. **环境准备**
   - Android Studio
   - Android SDK 34

2. **构建和运行**
   - 使用Android Studio打开项目
   - 构建并运行应用
   - 确保设备可以访问后端服务

## 测试流程

1. **启动后端服务**
   - 运行`python app.py`

2. **运行前端应用**
   - 安装并启动Android应用

3. **测试功能**
   - 注册新用户
   - 登录系统
   - 点击"测试前后端通信"按钮
   - 测试拍照识别功能
   - 测试视频分析功能
   - 查看报告

## 后续开发计划

1. **模型集成**
   - 集成YOLOv5/YOLOv8模型
   - 训练专门的宠物姿态识别模型

2. **功能扩展**
   - 面部微表情识别
   - 情绪日报生成
   - 异常行为警告
   - 大模型集成（饲养建议）

3. **性能优化**
   - 模型量化和加速
   - 异步处理视频分析
   - 缓存机制优化

4. **用户体验**
   - 美化界面
   - 添加更多交互功能
   - 支持多宠物管理

## 技术支持

### 常见问题

1. **前后端通信失败**
   - 检查后端服务是否启动
   - 检查网络连接
   - 确认BASE_URL配置正确

2. **模型识别准确率低**
   - 确保宠物在图片/视频中清晰可见
   - 光线充足
   - 宠物姿态明显

3. **应用崩溃**
   - 检查权限设置
   - 确保存储空间充足
   - 检查日志获取详细错误信息

### 联系方式

- 项目维护者：[Your Name]
- 邮箱：[your.email@example.com]
-  GitHub：[your-github-username]

## 许可证

MIT License

---

*项目启动日期：2026-04-14*