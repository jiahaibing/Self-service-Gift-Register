# 礼账记录 - Gift Register App

一个Android应用，用于记录和管理礼账信息。支持增删改查功能，使用SQLite本地数据库存储数据。

## 功能特性

✅ **增加** - 添加新的礼账记录
✅ **删除** - 长按或编辑详情页删除记录
✅ **修改** - 点击列表项编辑礼账信息
✅ **查询** - 查看所有记录或通过关键词搜索
✅ **统计** - 显示礼账总金额
✅ **日期选择** - 集成日期选择器
✅ **统一UI风格** - 使用Material Design设计

## 技术栈

- **语言**: Java
- **数据库**: SQLite
- **UI框架**: Android Material Design Components
- **最低版本**: Android 7.0 (API 24)
- **目标版本**: Android 14 (API 34)

## 项目结构

```
app/src/main/
├── java/com/example/giftregister/
│   ├── MainActivity.java              # 主页面 - 礼账列表
│   ├── AddEditGiftActivity.java       # 添加/编辑礼账
│   ├── GiftDetailActivity.java        # 礼账详情
│   ├── adapter/
│   │   └── GiftAdapter.java           # RecyclerView适配器
│   ├── database/
│   │   ├── GiftDatabaseHelper.java    # 数据库管理
│   │   └── GiftDAO.java               # 数据访问对象
│   └── model/
│       └── Gift.java                  # 礼账数据模型
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_add_edit_gift.xml
│   │   ├── activity_gift_detail.xml
│   │   └── item_gift.xml
│   ├── values/
│   │   ├── colors.xml                 # 颜色定义
│   │   ├── themes.xml                 # 样式定义
│   │   └── strings.xml                # 字符串资源
│   └── AndroidManifest.xml
└── build.gradle
```

## 数据库设计

### 表: gifts

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | INTEGER PRIMARY KEY | 主键(自增) |
| gift_from | TEXT | 礼品来自 |
| gift_item | TEXT | 礼品内容 |
| amount | REAL | 金额 |
| date | TEXT | 日期 |
| notes | TEXT | 备注 |
| created_at | INTEGER | 创建时间戳 |

## 安装和运行

### 前提条件
- Android Studio
- JDK 11 或更高版本
- Android SDK (API 34)

### 步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/jiahaibing/Self-service-Gift-Register.git
   cd Self-service-Gift-Register
   ```

2. **在Android Studio中打开项目**
   - 打开 Android Studio
   - 选择 "Open an existing Android Studio project"
   - 选择项目文件夹

3. **构建项目**
   - 点击 "Build" > "Make Project" 或按 Ctrl+F9
   - 等待Gradle完成构建

4. **运行应用**
   - 连接Android设备或启动模拟器
   - 点击 "Run" > "Run app" 或按 Shift+F10

## 使用说明

### 主页面
- **查看列表**: 显示所有礼账记录，按最新创建时间排序
- **总金额**: 屏幕顶部显示所有礼账的总金额
- **搜索**: 通过礼品来源或内容进行模糊搜索
- **添加**: 点击右下角浮动按钮添加新记录
- **编辑**: 点击列表项进入编辑页面
- **删除**: 长按列表项删除（需要确认）

### 添加/编辑页面
- **礼品来自**: 输入礼品的送礼者
- **礼品内容**: 输入礼品的具体内容
- **金额**: 输入礼品价值金额
- **日期**: 点击日期字段选择日期
- **备注**: 可选，输入额外信息
- **保存**: 保存记录后自动返回列表

### 详情页面
- 查看完整的礼账信息
- 点击 "编辑" 按钮修改记录
- 点击 "删除" 按钮删除记录

## UI设计

### 色彩方案
- **主色**: 紫色 (#6C63FF)
- **副色**: 粉红色 (#FF6B9D)
- **背景**: 浅灰色 (#F5F5F5)
- **文字**: 深灰色 (#333333)

### 组件风格
- Material Design Cards
- Material Design Buttons
- Material TextInput
- RecyclerView 列表

## 开发建议

### 如何添加新功能

1. **分类统计**
   ```java
   // 在GiftDAO中添加按来源分组统计
   public Map<String, Double> getTotalBySource() { ... }
   ```

2. **导出数据**
   ```java
   // 导出为CSV或PDF
   public void exportToCSV() { ... }
   ```

3. **提醒功能**
   - 添加 AlarmManager 和 NotificationManager
   - 设置回礼提醒

4. **云同步**
   - 集成 Firebase Firestore
   - 实现多设备同步

## 常见问题

**Q: 数据会保存在哪里？**
A: 数据保存在设备本地的SQLite数据库中，路径为 `/data/data/com.example.giftregister/databases/gift_register.db`

**Q: 卸载应用后数据会丢失吗？**
A: 是的，卸载应用会删除所有本地数据。建议定期备份或导出。

**Q: 支持多用户吗？**
A: 当前版本不支持多用户，所有记录共享。可以在未来版本添加用户功能。

## 许可证

MIT License

## 联系方式

- GitHub: [@jiahaibing](https://github.com/jiahaibing)
- Email: 138492023@qq.com

## 更新日志

### v1.0 (2026-07-11)
- ✅ 初始版本发布
- ✅ 完成增删改查功能
- ✅ 实现搜索功能
- ✅ 统一UI设计
- ✅ SQLite数据库集成

---

**感谢使用礼账记录应用！** 💝
