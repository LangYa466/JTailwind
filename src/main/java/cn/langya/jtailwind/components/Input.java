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
public class Input extends UIComponent {
    
    /**
     * 输入框类型 (text, password, email, number, tel, url)
     */
    @JsonProperty("inputType")
    private String inputType = "text";
    
    /**
     * 占位符文本
     */
    @JsonProperty("placeholder")
    private String placeholder;
    
    /**
     * 输入框值
     */
    @JsonProperty("value")
    private String value;
    
    /**
     * 是否只读
     */
    @JsonProperty("readonly")
    private boolean readonly = false;
    
    /**
     * 是否禁用
     */
    @JsonProperty("disabled")
    private boolean disabled = false;
    
    /**
     * 是否必填
     */
    @JsonProperty("required")
    private boolean required = false;
    
    /**
     * 输入框大小 (sm, md, lg)
     */
    @JsonProperty("size")
    private String size = "md";
    
    /**
     * 输入事件回调
     */
    @JsonProperty("onInput")
    private String onInput;
    
    /**
     * 焦点事件回调
     */
    @JsonProperty("onFocus")
    private String onFocus;
    
    /**
     * 失焦事件回调
     */
    @JsonProperty("onBlur")
    private String onBlur;
    
    /**
     * 构造函数
     * 
     * @param placeholder 占位符文本
     */
    public Input(String placeholder) {
        super("input");
        this.placeholder = placeholder;
        setDefaultClasses();
    }
    
    /**
     * 设置默认CSS类
     */
    private void setDefaultClasses() {
        addClass("w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500");
        updateSizeClasses();
    }
    
    /**
     * 更新输入框大小样式
     */
    private void updateSizeClasses() {
        switch (size) {
            case "sm":
                addClass("px-2 py-1 text-sm");
                break;
            case "lg":
                addClass("px-4 py-3 text-lg");
                break;
            default:
                addClass("px-3 py-2");
        }
    }
    
    /**
     * 设置输入框类型
     * 
     * @param inputType 输入框类型
     * @return 当前输入框
     */
    public Input setInputType(String inputType) {
        this.inputType = inputType;
        return this;
    }
    
    /**
     * 设置输入框大小
     * 
     * @param size 输入框大小
     * @return 当前输入框
     */
    public Input setSize(String size) {
        this.size = size;
        updateSizeClasses();
        return this;
    }
    
    /**
     * 设置输入事件
     * 
     * @param callbackName 回调函数名
     * @return 当前输入框
     */
    public Input setOnInput(String callbackName) {
        this.onInput = callbackName;
        return this;
    }
    
    /**
     * 设置焦点事件
     * 
     * @param callbackName 回调函数名
     * @return 当前输入框
     */
    public Input setOnFocus(String callbackName) {
        this.onFocus = callbackName;
        return this;
    }
    
    /**
     * 设置失焦事件
     * 
     * @param callbackName 回调函数名
     * @return 当前输入框
     */
    public Input setOnBlur(String callbackName) {
        this.onBlur = callbackName;
        return this;
    }
}
