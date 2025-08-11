package cn.langya.jtailwind.renderer;

import cn.langya.jtailwind.core.UIComponent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LangYa466
 * @date 2025/1/27
 */
@Data
@Accessors(chain = true)
public class PageRenderer {
    
    /**
     * 页面标题
     */
    private String title = "JTailwind Page";
    
    /**
     * 页面组件列表
     */
    private List<UIComponent> components = new ArrayList<>();
    
    /**
     * 页面样式
     */
    private String customStyles = "";
    
    /**
     * 页面脚本
     */
    private String customScripts = "";
    
    /**
     * 回调函数映射
     */
    private Map<String, String> callbacks = new HashMap<>();
    
    /**
     * 默认主题模式 (light, dark, auto)
     */
    private String defaultTheme = "light";
    
    /**
     * JSON对象映射器
     */
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 添加组件
     * 
     * @param component UI组件
     * @return 当前渲染器
     */
    public PageRenderer addComponent(UIComponent component) {
        this.components.add(component);
        return this;
    }
    
    /**
     * 添加回调函数
     * 
     * @param name 函数名
     * @param code JavaScript代码
     * @return 当前渲染器
     */
    public PageRenderer addCallback(String name, String code) {
        this.callbacks.put(name, code);
        return this;
    }
    
    /**
     * 设置默认主题模式
     * 
     * @param theme 主题模式 (light, dark, auto)
     * @return 当前渲染器
     */
    public PageRenderer setDefaultTheme(String theme) {
        this.defaultTheme = theme;
        return this;
    }
    
    /**
     * 渲染完整HTML页面
     * 
     * @return HTML字符串
     */
    public String render() {
        StringBuilder html = new StringBuilder();
        
        // HTML头部
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"zh-CN\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>").append(title).append("</title>\n");
        html.append("    <script src=\"https://cdn.tailwindcss.com\"></script>\n");
        html.append("    <script>\n");
        html.append("        tailwind.config = {\n");
        html.append("            darkMode: 'class',\n");
        html.append("            theme: {\n");
        html.append("                extend: {}\n");
        html.append("            }\n");
        html.append("        }\n");
        html.append("    </script>\n");
        html.append("    <style>\n");
        html.append("        /* 确保暗色模式样式生效 */\n");
        html.append("        .dark input, .dark select {\n");
        html.append("            background-color: rgb(55 65 81) !important;\n");
        html.append("            color: white !important;\n");
        html.append("            border-color: rgb(75 85 99) !important;\n");
        html.append("        }\n");
        html.append("        .dark input::placeholder, .dark select::placeholder {\n");
        html.append("            color: rgb(156 163 175) !important;\n");
        html.append("        }\n");
        html.append("        .dark option {\n");
        html.append("            background-color: rgb(55 65 81) !important;\n");
        html.append("            color: white !important;\n");
        html.append("        }\n");
        html.append("    </style>\n");
        html.append("    <style>\n");
        html.append(customStyles);
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body class=\"bg-gray-50 dark:bg-gray-900 min-h-screen transition-colors duration-200\">\n");
        
        // 页面容器
        html.append("    <div class=\"container mx-auto px-4 py-8\">\n");
        html.append("        <div class=\"flex justify-between items-center mb-8\">\n");
        html.append("            <h1 class=\"text-3xl font-bold text-gray-900 dark:text-white\">").append(title).append("</h1>\n");
        html.append("            <button id=\"theme-toggle\" class=\"p-2 rounded-lg bg-gray-200 dark:bg-gray-700 hover:bg-gray-300 dark:hover:bg-gray-600 transition-colors duration-200\">\n");
        html.append("                <svg id=\"sun-icon\" class=\"w-5 h-5 text-gray-800 dark:text-gray-200 hidden\" fill=\"currentColor\" viewBox=\"0 0 20 20\">\n");
        html.append("                    <path fill-rule=\"evenodd\" d=\"M10 2a1 1 0 011 1v1a1 1 0 11-2 0V3a1 1 0 011-1zm4 8a4 4 0 11-8 0 4 4 0 018 0zm-.464 4.95l.707.707a1 1 0 001.414-1.414l-.707-.707a1 1 0 00-1.414 1.414zm2.12-10.607a1 1 0 010 1.414l-.706.707a1 1 0 11-1.414-1.414l.707-.707a1 1 0 011.414 0zM17 11a1 1 0 100-2h-1a1 1 0 100 2h1zm-7 4a1 1 0 011 1v1a1 1 0 11-2 0v-1a1 1 0 011-1zM5.05 6.464A1 1 0 106.465 5.05l-.708-.707a1 1 0 00-1.414 1.414l.707.707zm1.414 8.486l-.707.707a1 1 0 01-1.414-1.414l.707-.707a1 1 0 011.414 1.414zM4 11a1 1 0 100-2H3a1 1 0 000 2h1z\" clip-rule=\"evenodd\"></path>\n");
        html.append("                </svg>\n");
        html.append("                <svg id=\"moon-icon\" class=\"w-5 h-5 text-gray-800 dark:text-gray-200\" fill=\"currentColor\" viewBox=\"0 0 20 20\">\n");
        html.append("                    <path d=\"M17.293 13.293A8 8 0 016.707 2.707a8.001 8.001 0 1010.586 10.586z\"></path>\n");
        html.append("                </svg>\n");
        html.append("            </button>\n");
        html.append("        </div>\n");
        
        // 组件容器
        html.append("        <div class=\"space-y-4 dark:text-white\">\n");
        
        // 渲染组件
        for (UIComponent component : components) {
            html.append(renderComponent(component));
        }
        
        html.append("        </div>\n");
        
        html.append("    </div>\n");
        
        // JavaScript代码
        html.append("    <script>\n");
        html.append("        // 主题管理\n");
        html.append("        const defaultTheme = '").append(defaultTheme).append("';\n");
        html.append("        let currentTheme = localStorage.getItem('theme') || defaultTheme;\n");
        html.append("        \n");
        html.append("        // 初始化主题\n");
        html.append("        function initTheme() {\n");
        html.append("            if (currentTheme === 'dark' || (currentTheme === 'auto' && window.matchMedia('(prefers-color-scheme: dark)').matches)) {\n");
        html.append("                document.documentElement.classList.add('dark');\n");
        html.append("                updateThemeIcon(true);\n");
        html.append("            } else {\n");
        html.append("                document.documentElement.classList.remove('dark');\n");
        html.append("                updateThemeIcon(false);\n");
        html.append("            }\n");
        html.append("        }\n");
        html.append("        \n");
        html.append("        // 切换主题\n");
        html.append("        function toggleTheme() {\n");
        html.append("            const isDark = document.documentElement.classList.contains('dark');\n");
        html.append("            if (isDark) {\n");
        html.append("                document.documentElement.classList.remove('dark');\n");
        html.append("                localStorage.setItem('theme', 'light');\n");
        html.append("                currentTheme = 'light';\n");
        html.append("                updateThemeIcon(false);\n");
        html.append("            } else {\n");
        html.append("                document.documentElement.classList.add('dark');\n");
        html.append("                localStorage.setItem('theme', 'dark');\n");
        html.append("                currentTheme = 'dark';\n");
        html.append("                updateThemeIcon(true);\n");
        html.append("            }\n");
        html.append("        }\n");
        html.append("        \n");
        html.append("        // 更新主题图标\n");
        html.append("        function updateThemeIcon(isDark) {\n");
        html.append("            const sunIcon = document.getElementById('sun-icon');\n");
        html.append("            const moonIcon = document.getElementById('moon-icon');\n");
        html.append("            if (isDark) {\n");
        html.append("                sunIcon.classList.remove('hidden');\n");
        html.append("                moonIcon.classList.add('hidden');\n");
        html.append("            } else {\n");
        html.append("                sunIcon.classList.add('hidden');\n");
        html.append("                moonIcon.classList.remove('hidden');\n");
        html.append("            }\n");
        html.append("        }\n");
        html.append("        \n");
        html.append("        // 监听系统主题变化\n");
        html.append("        if (currentTheme === 'auto') {\n");
        html.append("            window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', function(e) {\n");
        html.append("                if (currentTheme === 'auto') {\n");
        html.append("                    if (e.matches) {\n");
        html.append("                        document.documentElement.classList.add('dark');\n");
        html.append("                        updateThemeIcon(true);\n");
        html.append("                    } else {\n");
        html.append("                        document.documentElement.classList.remove('dark');\n");
        html.append("                        updateThemeIcon(false);\n");
        html.append("                    }\n");
        html.append("                }\n");
        html.append("            });\n");
        html.append("        }\n");
        html.append("        \n");
        html.append("        // 组件数据\n");
        html.append("        const components = ").append(renderComponentsJson()).append(";\n");
        html.append("        \n");
        html.append("        // 渲染组件\n");
        html.append("        function renderComponents() {\n");
        html.append("            components.forEach(component => {\n");
        html.append("                const element = document.getElementById(component.id);\n");
        html.append("                if (element) {\n");
        html.append("                    updateComponent(element, component);\n");
        html.append("                }\n");
        html.append("            });\n");
        html.append("        }\n");
        html.append("        \n");
        html.append("        // 更新组件\n");
        html.append("        function updateComponent(element, component) {\n");
        html.append("            if (component.className) {\n");
        html.append("                element.className = component.className;\n");
        html.append("            }\n");
        html.append("            if (component.style) {\n");
        html.append("                element.style = component.style;\n");
        html.append("            }\n");
        html.append("            if (component.visible !== undefined) {\n");
        html.append("                element.style.display = component.visible ? '' : 'none';\n");
        html.append("            }\n");
        html.append("        }\n");
        html.append("        \n");
        html.append("        // 发送更新请求\n");
        html.append("        function sendUpdate(componentId, data) {\n");
        html.append("            fetch('/update', {\n");
        html.append("                method: 'POST',\n");
        html.append("                headers: {\n");
        html.append("                    'Content-Type': 'application/json',\n");
        html.append("                },\n");
        html.append("                body: JSON.stringify({\n");
        html.append("                    componentId: componentId,\n");
        html.append("                    data: data\n");
        html.append("                })\n");
        html.append("            })\n");
        html.append("            .then(response => response.json())\n");
        html.append("            .then(data => {\n");
        html.append("                if (data.components) {\n");
        html.append("                    data.components.forEach(comp => {\n");
        html.append("                        const element = document.getElementById(comp.id);\n");
        html.append("                        if (element) {\n");
        html.append("                            updateComponent(element, comp);\n");
        html.append("                        }\n");
        html.append("                    });\n");
        html.append("                }\n");
        html.append("            })\n");
        html.append("            .catch(error => console.error('Error:', error));\n");
        html.append("        }\n");
        html.append("        \n");
        
        // 添加自定义回调函数
        for (Map.Entry<String, String> entry : callbacks.entrySet()) {
            html.append("        ").append(entry.getValue()).append("\n");
        }
        
        html.append("        \n");
        html.append("        // 初始化\n");
        html.append("        document.addEventListener('DOMContentLoaded', function() {\n");
        html.append("            initTheme();\n");
        html.append("            renderComponents();\n");
        html.append("            \n");
        html.append("            // 绑定主题切换按钮事件\n");
        html.append("            document.getElementById('theme-toggle').addEventListener('click', toggleTheme);\n");
        html.append("        });\n");
        html.append("    </script>\n");
        html.append("</body>\n");
        html.append("</html>");
        
        return html.toString();
    }
    
    /**
     * 渲染单个组件
     * 
     * @param component UI组件
     * @return HTML字符串
     */
    private String renderComponent(UIComponent component) {
        StringBuilder html = new StringBuilder();
        
        switch (component.getType()) {
            case "button":
                html.append(renderButton(component));
                break;
            case "input":
                html.append(renderInput(component));
                break;
            case "select":
                html.append(renderSelect(component));
                break;
            case "dataDisplay":
                html.append(renderDataDisplay(component));
                break;
            default:
                html.append("        <!-- 未知组件类型: ").append(component.getType()).append(" -->\n");
        }
        
        return html.toString();
    }
    
    /**
     * 渲染按钮组件
     * 
     * @param component 按钮组件
     * @return HTML字符串
     */
    private String renderButton(UIComponent component) {
        String baseClass = component.getClassName() != null ? component.getClassName() : "";
        // 如果没有设置按钮样式，添加默认的暗色模式支持
        if (baseClass.isEmpty() || !baseClass.contains("bg-")) {
            baseClass += " bg-blue-500 hover:bg-blue-600 dark:bg-blue-600 dark:hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-lg transition-colors duration-200";
        }
        
        return String.format(
            "        <button id=\"%s\" class=\"%s\" %s %s>%s</button>\n",
            component.getId(),
            baseClass,
            getComponentProperty(component, "onClick", null) != null ? "onclick=\"" + getComponentProperty(component, "onClick", "") + "()\"" : "",
            !component.isVisible() ? "style=\"display: none;\"" : "",
            getComponentProperty(component, "text", "")
        );
    }
    
    /**
     * 渲染输入框组件
     * 
     * @param component 输入框组件
     * @return HTML字符串
     */
    private String renderInput(UIComponent component) {
        String baseClass = component.getClassName() != null ? component.getClassName() : "";
        // 如果没有设置输入框样式，添加默认的暗色模式支持
        if (baseClass.isEmpty() || !baseClass.contains("border")) {
            baseClass += " w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm placeholder-gray-400 dark:placeholder-gray-500 focus:outline-none focus:ring-blue-500 focus:border-blue-500 bg-white dark:bg-gray-700 text-gray-900 dark:text-white disabled:bg-gray-100 dark:disabled:bg-gray-800 disabled:text-gray-500 dark:disabled:text-gray-400 transition-colors duration-200";
        }
        
        return String.format(
            "        <input id=\"%s\" type=\"%s\" class=\"%s\" placeholder=\"%s\" value=\"%s\" %s %s %s %s>\n",
            component.getId(),
            getComponentProperty(component, "inputType", "text"),
            baseClass,
            getComponentProperty(component, "placeholder", ""),
            getComponentProperty(component, "value", ""),
            getComponentProperty(component, "readonly", false) ? "readonly" : "",
            getComponentProperty(component, "disabled", false) ? "disabled" : "",
            getComponentProperty(component, "required", false) ? "required" : "",
            !component.isVisible() ? "style=\"display: none;\"" : ""
        );
    }
    
    /**
     * 渲染选择框组件
     * 
     * @param component 选择框组件
     * @return HTML字符串
     */
    private String renderSelect(UIComponent component) {
        String baseClass = component.getClassName() != null ? component.getClassName() : "";
        // 如果没有设置选择框样式，添加默认的暗色模式支持
        if (baseClass.isEmpty() || !baseClass.contains("border")) {
            baseClass += " w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 bg-white dark:bg-gray-700 text-gray-900 dark:text-white disabled:bg-gray-100 dark:disabled:bg-gray-800 disabled:text-gray-500 dark:disabled:text-gray-400 transition-colors duration-200";
        }
        
        StringBuilder html = new StringBuilder();
        html.append(String.format(
            "        <select id=\"%s\" class=\"%s\" %s %s %s %s>\n",
            component.getId(),
            baseClass,
            getComponentProperty(component, "disabled", false) ? "disabled" : "",
            getComponentProperty(component, "required", false) ? "required" : "",
            getComponentProperty(component, "onChange", null) != null ? "onchange=\"" + getComponentProperty(component, "onChange", "") + "()\"" : "",
            !component.isVisible() ? "style=\"display: none;\"" : ""
        ));
        
        // 添加占位符选项
        html.append("            <option value=\"\" class=\"bg-white dark:bg-gray-700 text-gray-900 dark:text-white\">").append(getComponentProperty(component, "placeholder", "")).append("</option>\n");
        
        // 添加动态选项
        try {
            java.lang.reflect.Field optionsField = component.getClass().getDeclaredField("options");
            optionsField.setAccessible(true);
            Object optionsObj = optionsField.get(component);
            
            if (optionsObj instanceof java.util.List) {
                java.util.List<?> options = (java.util.List<?>) optionsObj;
                for (Object option : options) {
                    if (option instanceof cn.langya.jtailwind.components.Select.SelectOption) {
                        cn.langya.jtailwind.components.Select.SelectOption selectOption = 
                            (cn.langya.jtailwind.components.Select.SelectOption) option;
                        html.append(String.format(
                            "            <option value=\"%s\"%s class=\"bg-white dark:bg-gray-700 text-gray-900 dark:text-white\">%s</option>\n",
                            selectOption.getValue() != null ? selectOption.getValue() : "",
                            selectOption.isDisabled() ? " disabled" : "",
                            selectOption.getLabel() != null ? selectOption.getLabel() : ""
                        ));
                    }
                }
            }
        } catch (Exception e) {
            // 如果获取选项失败，忽略错误
        }
        
        html.append("        </select>\n");
        return html.toString();
    }
    
    /**
     * 渲染数据显示组件
     * 
     * @param component 数据显示组件
     * @return HTML字符串
     */
    private String renderDataDisplay(UIComponent component) {
        String label = getComponentProperty(component, "label", "");
        Object valueObj = getComponentProperty(component, "value", "");
        String value = valueObj != null ? valueObj.toString() : "";
        boolean showLabel = getComponentProperty(component, "showLabel", false);
        String displayStyle = getComponentProperty(component, "displayStyle", "text");
        
        StringBuilder html = new StringBuilder();
        
        // 根据显示样式调整布局
        if ("badge".equals(displayStyle)) {
            // 获取badge的CSS类
            String badgeClass = getBadgeClass(component);
            
            // Badge样式，使用内联布局
            if (showLabel && label != null && !label.isEmpty()) {
                // 有标签的badge，使用水平布局，标签和badge分开
                html.append("        <div id=\"").append(component.getId()).append("\" class=\"flex items-center space-x-2\"");
                if (!component.isVisible()) {
                    html.append(" style=\"display: none;\"");
                }
                html.append(">\n");
                
                html.append("            <span class=\"text-sm font-medium text-gray-700 dark:text-gray-300\">").append(label).append("</span>\n");
                html.append("            <span class=\"").append(badgeClass).append("\">").append(value).append("</span>\n");
                html.append("        </div>\n");
            } else {
                // 没有标签的badge，直接显示
                html.append("        <span id=\"").append(component.getId()).append("\" class=\"").append(badgeClass).append("\"");
                if (!component.isVisible()) {
                    html.append(" style=\"display: none;\"");
                }
                html.append(">").append(value).append("</span>\n");
            }
        } else {
            // 其他样式，使用原有布局
            String containerClass = component.getClassName() != null ? component.getClassName() : "";
            // 如果是card样式但没有设置样式，添加默认的暗色模式支持
            if ("card".equals(displayStyle) && (containerClass.isEmpty() || !containerClass.contains("bg-"))) {
                containerClass += " bg-white dark:bg-gray-800 shadow rounded-lg p-4 border border-gray-200 dark:border-gray-700";
            }
            
            if (showLabel && label != null && !label.isEmpty()) {
                // 有标签的情况，使用垂直布局
                html.append("        <div id=\"").append(component.getId()).append("\" class=\"").append(containerClass).append("\"");
                if (!component.isVisible()) {
                    html.append(" style=\"display: none;\"");
                }
                html.append(">\n");
                
                html.append("            <label class=\"block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1\">").append(label).append("</label>\n");
                html.append("            <div class=\"mt-1\">\n");
                html.append("                <span class=\"text-gray-900 dark:text-white\">").append(value).append("</span>\n");
                html.append("            </div>\n");
                html.append("        </div>\n");
            } else {
                // 没有标签的情况，使用内联布局
                html.append("        <div id=\"").append(component.getId()).append("\" class=\"").append(containerClass).append("\"");
                if (!component.isVisible()) {
                    html.append(" style=\"display: none;\"");
                }
                html.append(">\n");
                
                html.append("            <span class=\"text-gray-900 dark:text-white\">").append(value).append("</span>\n");
                html.append("        </div>\n");
            }
        }
        
        return html.toString();
    }
    
    /**
     * 获取组件属性值
     * 
     * @param component 组件
     * @param property 属性名
     * @param defaultValue 默认值
     * @return 属性值
     */
    private <T> T getComponentProperty(UIComponent component, String property, T defaultValue) {
        try {
            java.lang.reflect.Field field = component.getClass().getDeclaredField(property);
            field.setAccessible(true);
            Object value = field.get(component);
            return value != null ? (T) value : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    /**
     * 获取badge的CSS类
     * 
     * @param component 数据显示组件
     * @return badge的CSS类
     */
    private String getBadgeClass(UIComponent component) {
        String baseClass = "inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium";
        String dataType = getComponentProperty(component, "dataType", "string");
        Object value = getComponentProperty(component, "value", "");
        
        switch (dataType) {
            case "int":
            case "long":
            case "double":
                return baseClass + " bg-blue-100 text-blue-800";
            case "boolean":
                if (Boolean.TRUE.equals(value)) {
                    return baseClass + " bg-green-100 text-green-800";
                } else {
                    return baseClass + " bg-red-100 text-red-800";
                }
            default:
                return baseClass + " bg-gray-100 text-gray-800";
        }
    }
    
    /**
     * 渲染组件JSON数据
     * 
     * @return JSON字符串
     */
    private String renderComponentsJson() {
        try {
            return objectMapper.writeValueAsString(components);
        } catch (Exception e) {
            return "[]";
        }
    }
}
