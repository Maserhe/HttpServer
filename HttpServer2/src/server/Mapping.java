package server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <servlet-mapping>
 *     <sevlet-name>login</sevlet-name>
 *     <!-- log 浏览器访问 /log -->
 *     <url-pattern>/log</url-pattern>
 *     <url-pattern>/login</url-pattern>
 * </servlet-mapping>
 *
 */
public class Mapping {      // 一种映射关系。多个路径访问共享资源。
    private String name;
    private List<String> urlPattern;

    public void setName(String name) {
        this.name = name;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getName() {
        return name;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public Mapping(String name, List<String> urlPattern) {
        this.name = name;
        this.urlPattern = urlPattern;
    }

    public Mapping() {
        super();
        urlPattern = new ArrayList<>();
    }
}
