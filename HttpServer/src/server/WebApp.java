package server;

import servlet.Servlet;

import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 初始化 运行的 数据。
 * 构建好 映射关系
 * 便于下一步的 映射做准备。
 * @author Maserhe
 */
public class WebApp {
    public static ServletContext context;
    static {
        context = new ServletContext();
        // 分别获取 两个集合。
        Map<String, String> servlet = context.getServlet();
        Map<String, String> map = context.getMapping();

        WebDom4j webDom4j = new WebDom4j();
        webDom4j.parse(webDom4j.getDocument());
        // 获取 解析 后 的 xml 文件。
        List<Entity> entityList = webDom4j.getEntityList();
        List<Mapping> mappings = webDom4j.getMappingList();

        //将 list 中的数据 存储到 Map 集合中。
        for (Entity i: entityList){
            servlet.put(i.getName(), i.getClazz());
        }
        for (Mapping i: mappings){
            for (String str: i.getUrlPattern()){
                map.put(str, i.getName());
            }
        }

    }
    public static Servlet getServlet(String url){
        if(url == null || url.equals("")) return null;

        // 创建类。
        // 根据 url 的 key 获取servlet-name 的 值.
        // 得到 报名。类名的 字符串。

        String servletName = context.getMapping().get(url);
        String servletClass = context.getServlet().get(servletName);
        // 反射 创建对象。
        try {

            Class<?> clazz = Class.forName(servletClass);
            Servlet servlet = null;
            try {
                servlet = (Servlet)clazz.getConstructor().newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return servlet;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 测试。
    public static void main(String[] args) {
        System.out.println(getServlet("/log"));
        System.out.println(getServlet("/login"));
    }
}
