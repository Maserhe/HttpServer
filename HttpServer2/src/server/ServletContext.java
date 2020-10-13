package server;

import java.util.Map;
import java.util.TreeMap;

/**
 *  用于 存储 Entity 与 mapping 中的 映射关系。
 *  反射获取 对应的类。
 *
 */

public class ServletContext {    // Entity 和 Mapping 之间的映射关系。
    // key 是 Entity 中的 servlet-name, value 是 servlet-class
    private Map<String, String> servlet;
    // key 是 mapping中的 url-pattern, value 是mapping 中的 class-name;
    private Map<String, String> mapping;

    public ServletContext(Map<String, String> servlet, Map<String, String> mapping) {
        this.servlet = servlet;
        this.mapping = mapping;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    public Map<String, String> getServlet() {
        return servlet;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public ServletContext() {
        servlet = new TreeMap<>();
        mapping = new TreeMap<>();
    }

}
