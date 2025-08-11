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
public class Button extends UIComponent {
    
    /**
     * 按钮文本
     */
    @JsonProperty("text")
    private String text;
    
    /**
     * 按钮类型 (primary, secondary, danger, success, warning)
     */
    @JsonProperty("buttonType")
    private String buttonType = "primary";
    
    /**
     * 按钮大小 (sm, md, lg)
     */
    @JsonProperty("size")
    private String size = "md";
    
    /**
     * 是否禁用
     */
    @JsonProperty("disabled")
    private boolean disabled = false;
    
    /**
     * 点击事件回调
     */
    @JsonProperty("onClick")
    private String onClick;
    
    /**
     * 构造函数
     * 
     * @param text 按钮文本
     */
    public Button(String text) {
        super("button");
        this.text = text;
        setDefaultClasses();
    }
    
    /**
     * 设置默认CSS类
     */
    private void setDefaultClasses() {
        addClass("px-4 py-2 rounded-md font-medium transition-colors duration-200");
        updateButtonTypeClasses();
        updateSizeClasses();
    }
    
    /**
     * 更新按钮类型样式
     */
    private void updateButtonTypeClasses() {
        switch (buttonType) {
            case "primary":
                addClass("bg-blue-500 hover:bg-blue-600 text-white");
                break;
            case "secondary":
                addClass("bg-gray-500 hover:bg-gray-600 text-white");
                break;
            case "danger":
                addClass("bg-red-500 hover:bg-red-600 text-white");
                break;
            case "success":
                addClass("bg-green-500 hover:bg-green-600 text-white");
                break;
            case "warning":
                addClass("bg-yellow-500 hover:bg-yellow-600 text-white");
                break;
            default:
                addClass("bg-blue-500 hover:bg-blue-600 text-white");
        }
    }
    
    /**
     * 更新按钮大小样式
     */
    private void updateSizeClasses() {
        switch (size) {
            case "sm":
                addClass("px-2 py-1 text-sm");
                break;
            case "lg":
                addClass("px-6 py-3 text-lg");
                break;
            default:
                addClass("px-4 py-2");
        }
    }
    
    /**
     * 设置按钮类型
     * 
     * @param buttonType 按钮类型
     * @return 当前按钮
     */
    public Button setButtonType(String buttonType) {
        this.buttonType = buttonType;
        updateButtonTypeClasses();
        return this;
    }
    
    /**
     * 设置按钮大小
     * 
     * @param size 按钮大小
     * @return 当前按钮
     */
    public Button setSize(String size) {
        this.size = size;
        updateSizeClasses();
        return this;
    }
    
    /**
     * 设置点击事件
     * 
     * @param callbackName 回调函数名
     * @return 当前按钮
     */
    public Button setOnClick(String callbackName) {
        this.onClick = callbackName;
        return this;
    }
}
