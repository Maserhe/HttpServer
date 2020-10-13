package server;

/**
 *
 *
 * <servlet>
 *      <!-- 包名 点 类名-->
 *      <servlet-name>login</servlet-name>
 *      <servlet-class>servlet.LoginServlet</servlet-class>
 * </servlet>
 *
 *
 *
 */
public class Entity {   // servlet-name 和 一个 servlet-name 对应的实体类。
    private String name;    //Servlet-name
    private String clazz;   //Servlet-class

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Entity(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public Entity() {
        super();
    }
}
