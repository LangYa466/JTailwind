# JTailwind

官网：[https://jtailwind.vercel.app/](https://jtailwind.vercel.app/)

一个基于Java的后端UI组件库，让后端开发者能够直接编写前端代码。使用H5、Tailwind CSS和JavaScript，实现后端到前端的无缝集成。

## ⚠️ 重要声明

**本项目大部分代码由AI编写，可能存在不完善之处。使用前请充分测试，欢迎提交Issue和Pull Request来改进项目。**

## 🚀 项目优势

- **后端即前端**：在Java后端代码中直接定义UI组件，自动生成前端代码
- **零前端知识**：无需掌握复杂的前端技术栈，专注于后端逻辑
- **现代化UI**：基于Tailwind CSS，提供美观的现代化界面
- **响应式设计**：自动适配不同屏幕尺寸
- **暗色模式**：内置暗色/亮色主题切换功能
- **组件丰富**：支持按钮、输入框、选择框、数据显示等多种组件

## 📦 快速开始

### Maven 依赖

```xml
<dependency>
    <groupId>com.github.LangYa466</groupId>
    <artifactId>JTailwind</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```

### Gradle 依赖

```gradle
implementation 'com.github.LangYa466:JTailwind:-SNAPSHOT'
```

### 添加 JitPack 仓库

在 `pom.xml` 中添加：

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

或在 `build.gradle` 中添加：

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```

## 🛠️ 使用教程

### 1. 基础组件使用

#### 创建按钮

```java
import cn.langya.jtailwind.components.Button;
import cn.langya.jtailwind.renderer.PageRenderer;

// 创建按钮组件
Button button = new Button("点击我")
    .setId("myButton")
    .setOnClick("handleClick");

// 创建页面渲染器
PageRenderer renderer = new PageRenderer()
    .setTitle("我的页面")
    .addComponent(button);

// 渲染HTML
String html = renderer.render();
```

#### 创建输入框

```java
import cn.langya.jtailwind.components.Input;

Input input = new Input()
    .setId("username")
    .setPlaceholder("请输入用户名")
    .setRequired(true)
    .setOnInput("handleInput");

renderer.addComponent(input);
```

#### 创建选择框

```java
import cn.langya.jtailwind.components.Select;
import java.util.Arrays;

Select select = new Select()
    .setId("country")
    .setPlaceholder("请选择国家")
    .addOption("中国", "CN")
    .addOption("美国", "US")
    .addOption("日本", "JP")
    .setOnChange("handleSelectChange");

renderer.addComponent(select);
```

#### 创建数据显示

```java
import cn.langya.jtailwind.components.DataDisplay;

// 文本显示
DataDisplay textDisplay = new DataDisplay("用户名", "张三")
    .setId("userDisplay")
    .setDisplayStyle("text");

// 徽章显示
DataDisplay badgeDisplay = new DataDisplay("状态", true)
    .setId("statusBadge")
    .setDisplayStyle("badge");

// 卡片显示
DataDisplay cardDisplay = new DataDisplay("余额", 1234.56)
    .setId("balanceCard")
    .setDisplayStyle("card");

renderer.addComponent(textDisplay)
    .addComponent(badgeDisplay)
    .addComponent(cardDisplay);
```

### 2. 页面渲染

```java
// 设置页面标题和主题
PageRenderer renderer = new PageRenderer()
    .setTitle("JTailwind组件演示")
    .setDefaultTheme("light"); // 可选: light, dark, auto

// 添加组件
renderer.addComponent(button)
    .addComponent(input)
    .addComponent(select)
    .addComponent(textDisplay);

// 渲染完整HTML页面
String html = renderer.render();

// 保存到文件或返回给前端
try (FileWriter writer = new FileWriter("page.html")) {
    writer.write(html);
}
```

### 3. 事件处理

#### 添加JavaScript回调

```java
// 添加自定义JavaScript函数
renderer.addCallback("handleClick", 
    "function handleClick() {" +
    "    alert('按钮被点击了！');" +
    "    sendUpdate('myButton', {clicked: true});" +
    "}");

renderer.addCallback("handleInput", 
    "function handleInput() {" +
    "    const value = document.getElementById('username').value;" +
    "    sendUpdate('username', {value: value});" +
    "}");
```

### 4. 服务器集成

#### 使用内置HTTP服务器

```java
import cn.langya.jtailwind.TestServer;

public class Main {
    public static void main(String[] args) {
        // 创建测试服务器
        TestServer server = new TestServer();
        
        // 启动服务器 (默认端口8080)
        server.start();
        
        System.out.println("服务器已启动: http://localhost:8080");
    }
}
```

#### 集成到Spring Boot

```java
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class UIController {
    
    @GetMapping("/ui")
    @ResponseBody
    public String renderUI() {
        PageRenderer renderer = new PageRenderer()
            .setTitle("Spring Boot + JTailwind");
            
        // 添加组件...
        
        return renderer.render();
    }
    
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> handleUpdate(@RequestBody Map<String, Object> data) {
        // 处理组件更新
        String componentId = (String) data.get("componentId");
        Map<String, Object> componentData = (Map<String, Object>) data.get("data");
        
        // 返回更新后的组件数据
        return Map.of("success", true, "components", new ArrayList<>());
    }
}
```

## 🎨 组件样式

### 自定义样式

```java
// 自定义按钮样式
Button customButton = new Button("自定义按钮")
    .setClassName("bg-red-500 hover:bg-red-600 text-white px-6 py-3 rounded-lg");

// 自定义输入框样式
Input customInput = new Input()
    .setClassName("border-2 border-blue-300 focus:border-blue-500 rounded-lg");

// 自定义数据显示样式
DataDisplay customDisplay = new DataDisplay("自定义显示", "值")
    .setClassName("bg-gradient-to-r from-blue-500 to-purple-500 text-white p-4 rounded-lg");
```

### 暗色模式支持

所有组件都内置暗色模式支持，可以通过页面右上角的主题切换按钮进行切换：

```java
// 设置默认主题
renderer.setDefaultTheme("dark"); // 默认暗色模式
renderer.setDefaultTheme("auto"); // 跟随系统设置
```

## 📋 组件列表

### 基础组件

| 组件 | 类名 | 描述 |
|------|------|------|
| 按钮 | `Button` | 可点击的按钮组件 |
| 输入框 | `Input` | 文本输入组件 |
| 选择框 | `Select` | 下拉选择组件 |
| 数据显示 | `DataDisplay` | 数据展示组件 |

### 数据显示样式

| 样式 | 描述 | 适用数据类型 |
|------|------|-------------|
| `text` | 普通文本显示 | 所有类型 |
| `badge` | 徽章样式 | 所有类型 |
| `card` | 卡片样式 | 所有类型 |
| `table` | 表格样式 | 所有类型 |

### 支持的数据类型

- `string` - 字符串
- `int` - 整数
- `long` - 长整数
- `double` - 浮点数
- `boolean` - 布尔值

## 🔧 配置选项

### PageRenderer 配置

```java
PageRenderer renderer = new PageRenderer()
    .setTitle("页面标题")
    .setDefaultTheme("light") // light, dark, auto
    .addCallback("functionName", "function code...");
```

### 组件通用属性

所有组件都支持以下属性：

- `id` - 组件唯一标识
- `className` - 自定义CSS类
- `style` - 内联样式
- `visible` - 是否可见
- `onUpdate` - 更新回调

## 🚀 部署指南

### 1. 编译项目

```bash
mvn clean compile
```

### 2. 运行测试

```bash
mvn test
```

### 3. 打包

```bash
mvn package
```

### 4. 集成到项目

将生成的JAR文件添加到你的项目中，或者直接使用Maven/Gradle依赖。

## 🤝 贡献指南

欢迎提交Issue和Pull Request来改进项目！

### 开发环境

- Java 8+
- Maven 3.6+
- IDE: IntelliJ IDEA 或 Eclipse

### 提交规范

- 使用中文提交信息
- 描述清楚修改内容和原因
- 确保代码通过测试

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

- [Tailwind CSS](https://tailwindcss.com/) - 提供优秀的CSS框架
- [Lombok](https://projectlombok.org/) - 简化Java代码
- [Jackson](https://github.com/FasterXML/jackson) - JSON处理库

## 📞 联系方式

- GitHub: [@LangYa466](https://github.com/LangYa466)
- 项目地址: [https://github.com/LangYa466/JTailwind](https://github.com/LangYa466/JTailwind)

---

**注意**: 本项目大部分代码由AI编写，可能存在不完善之处。使用前请充分测试，欢迎提交Issue和Pull Request来改进项目。
