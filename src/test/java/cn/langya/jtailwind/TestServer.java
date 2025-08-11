package cn.langya.jtailwind;

import cn.langya.jtailwind.components.*;
import cn.langya.jtailwind.renderer.PageRenderer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LangYa466
 * @date 2025/1/27
 */
@Slf4j
public class TestServer {
    
    /**
     * HTTP服务器
     */
    private HttpServer server;
    
    /**
     * JSON对象映射器
     */
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 当前页面渲染器
     */
    @Setter
    private PageRenderer pageRenderer;
    
    /**
     * 组件状态管理
     */
    private final Map<String, Object> componentStates = new HashMap<>();

    public void start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // 设置路由
        server.createContext("/", new HomeHandler());
        server.createContext("/update", new UpdateHandler());
        
        // 设置线程池
        server.setExecutor(null);
        
        // 启动服务器
        server.start();
        
        log.info("JTailwind测试服务器已启动 访问地址: http://localhost:{}", port);
    }
    
    /**
     * 停止服务器
     */
    public void stop() {
        if (server != null) {
            server.stop(0);
            log.info("JTailwind测试服务器已停止");
        }
    }

    /**
     * 首页处理器
     */
    private class HomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = pageRenderer != null ? pageRenderer.render() : getDefaultPage();
                
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
    
    /**
     * 更新处理器
     */
    private class UpdateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // 读取请求体
                String requestBody = readInputStream(exchange.getRequestBody());
                
                try {
                    // 解析请求数据
                    Map<String, Object> requestData = objectMapper.readValue(requestBody, Map.class);
                    String componentId = (String) requestData.get("componentId");
                    Map<String, Object> data = (Map<String, Object>) requestData.get("data");
                    
                    // 更新组件状态
                    componentStates.put(componentId, data);
                    
                    // 处理更新逻辑
                    handleComponentUpdate(componentId, data);
                    
                    // 返回响应
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "组件更新成功");
                    
                    String responseJson = objectMapper.writeValueAsString(response);
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                    exchange.sendResponseHeaders(200, responseJson.getBytes(StandardCharsets.UTF_8).length);
                    
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(responseJson.getBytes(StandardCharsets.UTF_8));
                    }
                    
                } catch (Exception e) {
                    log.error("处理更新请求时发生错误", e);
                    exchange.sendResponseHeaders(500, -1);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
    
    /**
     * 处理组件更新
     * 
     * @param componentId 组件ID
     * @param data 更新数据
     */
    private void handleComponentUpdate(String componentId, Map<String, Object> data) {
        log.info("组件 {} 更新: {}", componentId, data);
        
        // 这里可以实现具体的更新逻辑
        // 例如：更新数据库、重新计算数据等
    }
    
    /**
     * 读取输入流内容
     * 
     * @param inputStream 输入流
     * @return 字符串内容
     * @throws IOException IO异常
     */
    private String readInputStream(java.io.InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
        }
        return sb.toString();
    }
    
    /**
     * 获取默认页面
     * 
     * @return 默认HTML页面
     */
    private String getDefaultPage() {
        return "<!DOCTYPE html>\n" +
               "<html lang=\"zh-CN\">\n" +
               "<head>\n" +
               "    <meta charset=\"UTF-8\">\n" +
               "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
               "    <title>JTailwind测试服务器</title>\n" +
               "    <script src=\"https://cdn.tailwindcss.com\"></script>\n" +
               "</head>\n" +
               "<body class=\"bg-gray-50 min-h-screen\">\n" +
               "    <div class=\"container mx-auto px-4 py-8\">\n" +
               "        <h1 class=\"text-3xl font-bold text-gray-900 mb-8\">JTailwind测试服务器</h1>\n" +
               "        <div class=\"bg-white shadow rounded-lg p-6\">\n" +
               "            <p class=\"text-gray-600\">请设置页面渲染器来显示自定义内容</p>\n" +
               "        </div>\n" +
               "    </div>\n" +
               "</body>\n" +
               "</html>";
    }
    
    /**
     * 主方法 - 启动测试服务器
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            TestServer testServer = new TestServer();
            
            // 创建示例页面
            PageRenderer renderer = createExamplePage();
            testServer.setPageRenderer(renderer);
            
            // 启动服务器
            testServer.start(8080);
            
            // 保持服务器运行
            System.out.println("按任意键停止服务器...");
            System.in.read();
            
            testServer.stop();
            
        } catch (Exception e) {
            log.error("启动测试服务器时发生错误", e);
        }
    }
    
    /**
     * 创建示例页面
     * 
     * @return 页面渲染器
     */
    private static PageRenderer createExamplePage() {
        PageRenderer renderer = new PageRenderer()
            .setTitle("JTailwind组件演示")
            .addCallback("handleButtonClick", 
                "function handleButtonClick() {\n" +
                "    console.log('按钮被点击了！');\n" +
                "    sendUpdate('button_1', {action: 'click'});\n" +
                "}")
            .addCallback("handleInputChange", 
                "function handleInputChange() {\n" +
                "    const input = document.getElementById('input_1');\n" +
                "    sendUpdate('input_1', {value: input.value});\n" +
                "}")
            .addCallback("handleSelectChange", 
                "function handleSelectChange() {\n" +
                "    const select = document.getElementById('select_1');\n" +
                "    sendUpdate('select_1', {value: select.value});\n" +
                "}");
        
        // 添加按钮组件
        renderer.addComponent(
            new Button("点击我")
                .setButtonType("primary")
                .setSize("lg")
                .setOnClick("handleButtonClick")
                .addClass("mb-4")
        );
        
        // 添加输入框组件
        renderer.addComponent(
            new Input("请输入文本")
                .setInputType("text")
                .setSize("md")
                .setOnInput("handleInputChange")
                .addClass("mb-4")
        );
        
        // 添加选择框组件
        Select select = new Select("请选择选项");
        select.setSize("md");
        select.setOnChange("handleSelectChange");
        select.addClass("mb-4");
        
        select.addOption("option1", "选项1");
        select.addOption("option2", "选项2");
        select.addOption("option3", "选项3");
        
        renderer.addComponent(select);
        
        // 添加数据显示组件
        renderer.addComponent(
            new DataDisplay("用户ID", 12345)
                .setDisplayStyle("badge")
                .addClass("mb-4")
        );
        
        renderer.addComponent(
            new DataDisplay("用户名", "张三")
                .setDisplayStyle("text")
                .addClass("mb-4")
        );
        
        renderer.addComponent(
            new DataDisplay("余额", 1234.56)
                .setDisplayStyle("card")
                .setFormat("%.2f")
                .addClass("mb-4")
        );
        
        renderer.addComponent(
            new DataDisplay("是否激活", true)
                .setDisplayStyle("badge")
                .addClass("mb-4")
        );
        
        return renderer;
    }
}
