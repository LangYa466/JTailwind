package cn.langya.jtailwind.components;

import cn.langya.jtailwind.core.UIComponent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LangYa466
 * @date 2025/1/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Select extends UIComponent {
    
    /**
     * 选项列表
     */
    @JsonProperty("options")
    private List<SelectOption> options = new ArrayList<>();
    
    /**
     * 当前选中值
     */
    @JsonProperty("value")
    private String value;
    
    /**
     * 占位符文本
     */
    @JsonProperty("placeholder")
    private String placeholder;
    
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
     * 选择框大小 (sm, md, lg)
     */
    @JsonProperty("size")
    private String size = "md";
    
    /**
     * 选择事件回调
     */
    @JsonProperty("onChange")
    private String onChange;
    
    /**
     * 构造函数
     * 
     * @param placeholder 占位符文本
     */
    public Select(String placeholder) {
        super("select");
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
     * 更新选择框大小样式
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
     * 添加选项
     * 
     * @param value 选项值
     * @param label 选项标签
     * @return 当前选择框
     */
    public Select addOption(String value, String label) {
        this.options.add(new SelectOption(value, label));
        return this;
    }
    
    /**
     * 添加选项列表
     * 
     * @param options 选项列表
     * @return 当前选择框
     */
    public Select addOptions(List<SelectOption> options) {
        this.options.addAll(options);
        return this;
    }
    
    /**
     * 设置选择框大小
     * 
     * @param size 选择框大小
     * @return 当前选择框
     */
    public Select setSize(String size) {
        this.size = size;
        updateSizeClasses();
        return this;
    }
    
    /**
     * 设置选择事件
     * 
     * @param callbackName 回调函数名
     * @return 当前选择框
     */
    public Select setOnChange(String callbackName) {
        this.onChange = callbackName;
        return this;
    }
    
    /**
     * 选择框选项内部类
     */
    @Data
    @Accessors(chain = true)
    public static class SelectOption {
        
        /**
         * 选项值
         */
        @JsonProperty("value")
        private String value;
        
        /**
         * 选项标签
         */
        @JsonProperty("label")
        private String label;
        
        /**
         * 是否禁用
         */
        @JsonProperty("disabled")
        private boolean disabled = false;
        
        /**
         * 构造函数
         * 
         * @param value 选项值
         * @param label 选项标签
         */
        public SelectOption(String value, String label) {
            this.value = value;
            this.label = label;
        }
    }
}
