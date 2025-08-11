package cn.langya.jtailwind.components;

import cn.langya.jtailwind.core.UIComponent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author LangYa466
 * @date 2025/1/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DataDisplay extends UIComponent {
    
    /**
     * 显示的数据值
     */
    @JsonProperty("value")
    private Object value;
    
    /**
     * 数据类型 (string, int, long, double, boolean)
     */
    @JsonProperty("dataType")
    private String dataType = "string";
    
    /**
     * 显示格式
     */
    @JsonProperty("format")
    private String format;
    
    /**
     * 标签文本
     */
    @JsonProperty("label")
    private String label;
    
    /**
     * 是否显示标签
     */
    @JsonProperty("showLabel")
    private boolean showLabel = false;
    
    /**
     * 显示样式 (text, badge, card, table)
     */
    @JsonProperty("displayStyle")
    private String displayStyle = "text";
    
    /**
     * 构造函数
     * 
     * @param value 显示的数据值
     */
    public DataDisplay(Object value) {
        super("dataDisplay");
        this.value = value;
        setDataType();
        setDefaultClasses();
    }
    
    /**
     * 构造函数
     * 
     * @param label 标签文本
     * @param value 显示的数据值
     */
    public DataDisplay(String label, Object value) {
        super("dataDisplay");
        this.label = label;
        this.value = value;
        this.showLabel = true;
        setDataType();
        setDefaultClasses();
    }
    
    /**
     * 设置数据类型
     */
    private void setDataType() {
        if (value instanceof Integer) {
            this.dataType = "int";
        } else if (value instanceof Long) {
            this.dataType = "long";
        } else if (value instanceof Double || value instanceof Float) {
            this.dataType = "double";
        } else if (value instanceof Boolean) {
            this.dataType = "boolean";
        } else {
            this.dataType = "string";
        }
    }
    
    /**
     * 设置默认CSS类
     */
    private void setDefaultClasses() {
        switch (displayStyle) {
            case "badge":
                // Badge样式不在这里设置，而是在渲染时动态设置
                break;
            case "card":
                addClass("bg-white dark:bg-gray-800 shadow rounded-lg p-4 border border-gray-200 dark:border-gray-700");
                break;
            case "table":
                addClass("border-collapse border border-gray-300 dark:border-gray-600");
                break;
            default:
                addClass("text-gray-900 dark:text-white");
        }
    }
    
    /**
     * 更新徽章样式
     */
    private void updateBadgeStyle() {
        switch (dataType) {
            case "int":
            case "long":
            case "double":
                addClass("bg-blue-100 text-blue-800");
                break;
            case "boolean":
                if (Boolean.TRUE.equals(value)) {
                    addClass("bg-green-100 text-green-800");
                } else {
                    addClass("bg-red-100 text-red-800");
                }
                break;
            default:
                addClass("bg-gray-100 text-gray-800");
        }
    }
    
    /**
     * 设置显示样式
     * 
     * @param displayStyle 显示样式
     * @return 当前组件
     */
    public DataDisplay setDisplayStyle(String displayStyle) {
        this.displayStyle = displayStyle;
        setDefaultClasses();
        return this;
    }
    
    /**
     * 设置数据格式
     * 
     * @param format 格式字符串
     * @return 当前组件
     */
    public DataDisplay setFormat(String format) {
        this.format = format;
        return this;
    }
    
    /**
     * 获取格式化后的值
     * 
     * @return 格式化后的值
     */
    public String getFormattedValue() {
        if (value == null) {
            return "";
        }
        
        if (format != null && !format.isEmpty()) {
            try {
                if (value instanceof Number) {
                    return String.format(format, value);
                }
            } catch (Exception e) {
                // 格式错误时返回原值
            }
        }
        
        return value.toString();
    }
}
