package cn.langya.jtailwind;

import cn.langya.jtailwind.components.*;
import cn.langya.jtailwind.renderer.PageRenderer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 简化测试程序 - 不依赖外部依赖
 * 
 * @author LangYa466
 * @date 2025/1/27
 */
public class SimpleTest {
    
    /**
     * 主方法
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println("=== JTailwind 简化测试 ===");
        
        try {
            // 创建页面渲染器
            PageRenderer renderer = new PageRenderer();
            renderer.setTitle("JTailwind测试页面");
            
            // 添加按钮组件
            Button button = new Button("测试按钮");
            button.setButtonType("primary");
            button.setSize("lg");
            button.addClass("mb-4");
            renderer.addComponent(button);
            
            // 添加输入框组件
            Input input = new Input("请输入文本");
            input.setInputType("text");
            input.setSize("md");
            input.addClass("mb-4");
            renderer.addComponent(input);
            
            // 添加选择框组件
            Select select = new Select("请选择选项");
            select.setSize("md");
            select.addClass("mb-4");
            select.addOption("option1", "选项1");
            select.addOption("option2", "选项2");
            select.addOption("option3", "选项3");
            renderer.addComponent(select);
            
            // 添加数据显示组件
            DataDisplay display1 = new DataDisplay("用户ID", 12345);
            display1.setDisplayStyle("badge");
            display1.addClass("mb-4");
            renderer.addComponent(display1);
            
            DataDisplay display2 = new DataDisplay("用户名", "张三");
            display2.setDisplayStyle("text");
            display2.addClass("mb-4");
            renderer.addComponent(display2);
            
            DataDisplay display3 = new DataDisplay("余额", 1234.56);
            display3.setDisplayStyle("card");
            display3.setFormat("%.2f");
            display3.addClass("mb-4");
            renderer.addComponent(display3);
            
            DataDisplay display4 = new DataDisplay("是否激活", true);
            display4.setDisplayStyle("badge");
            display4.addClass("mb-4");
            renderer.addComponent(display4);
            
            // 渲染HTML
            String html = renderer.render();
            
            // 输出HTML到控制台
            System.out.println("生成的HTML页面:");
            System.out.println("==================================================");
            System.out.println(html);
            System.out.println("==================================================");
            
            // 保存到文件
            try {
                Files.write(
                    Paths.get("run\\test_output.html"),
                    html.getBytes(StandardCharsets.UTF_8)
                );
                System.out.println("HTML已保存到 test_output.html");
            } catch (Exception e) {
                System.out.println("保存文件失败: " + e.getMessage());
            }
            
            System.out.println("=== 测试完成 ===");
            
        } catch (Exception e) {
            System.err.println("测试过程中发生错误:");
            e.fillInStackTrace();
        }
    }
}
