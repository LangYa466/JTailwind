package cn.langya.jtailwind.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LangYa466
 * @date 2025/1/27
 */
@Data
@Accessors(chain = true)
public abstract class UIComponent {
    
    /**
     * 组件ID
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * 组件类型
     */
    @JsonProperty("type")
    private String type;
    
    /**
     * CSS类名
     */
    @JsonProperty("className")
    private String className;
    
    /**
     * 组件样式
     */
    @JsonProperty("style")
    private String style;
    
    /**
     * 是否可见
     */
    @JsonProperty("visible")
    private boolean visible = true;
    
    /**
     * 更新回调函数名
     */
    @JsonProperty("onUpdate")
    private String onUpdate;
    
    /**
     * 构造函数
     * 
     * @param type 组件类型
     */
    protected UIComponent(String type) {
        this.type = type;
        this.id = generateId();
    }
    
    /**
     * 生成唯一ID
     * 
     * @return 唯一ID
     */
    private String generateId() {
        return type.toLowerCase() + "_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    /**
     * 设置更新回调
     * 
     * @param callbackName 回调函数名
     * @return 当前组件
     */
    public UIComponent setOnUpdate(String callbackName) {
        this.onUpdate = callbackName;
        return this;
    }
    
    /**
     * 添加CSS类
     * 
     * @param cssClass CSS类名
     * @return 当前组件
     */
    public UIComponent addClass(String cssClass) {
        if (this.className == null) {
            this.className = cssClass;
        } else {
            this.className += " " + cssClass;
        }
        return this;
    }
}
