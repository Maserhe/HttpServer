package server;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 我的博客里写了Sax 解析。这里使用webDom4j 解析。https://maserhe.top
 *
 */
public class WebDom4j {     //Webdom4j 解析xml
    private List<Entity> entityList;    // 存放多个 Entity 的N多个实体类。
    private List<Mapping> mappingList;  // 用于存储 多个 Mapping, 每一个 Mapping是 servlet-name 与 多个 url-pattern 的映射。

    public WebDom4j() {
        entityList = new ArrayList<>();
        mappingList = new ArrayList<>();
    }

    public WebDom4j(List<Entity> entityList, List<Mapping> mappingList) {
        this.entityList = entityList;
        this.mappingList = mappingList;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public void setMappingList(List<Mapping> mappingList) {
        this.mappingList = mappingList;
    }

    // 开始

    public Document getDocument(){
        // 创建 SAXReader对象。
        SAXReader reader = new SAXReader();
        // 调用read方法。
        try {
            return reader.read(new File("HTTPServer/src/WEB_INFO/web.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void parse(Document doc){
        // (1) 获取根元素。
        Element root = doc.getRootElement();
        // (2) 获取 servlet 子元素。
        for (Iterator<Element> ite = root.elementIterator("servlet"); ite.hasNext();){
            Element subElement = ite.next(); // 得到的 每一个 servlet
            // 创建一个 实体类。
            Entity ent = new Entity(); // 用于 存储 servlet-name 和 servlet-class
            for (Iterator<Element> subIte = subElement.elementIterator(); subIte.hasNext();){
                //servlet 下面有 servlet-name 和 servlet-class 需要进行判断。
                Element element = subIte.next();
                if ("servlet-name".equals(element.getName())){
                    // 给实体类中的 name 赋值。
                     ent.setName(element.getText());
                } else if ("servlet-class".equals(element.getName())){
                    ent.setClazz(element.getText());
                }
            }
            entityList.add(ent);
        }

        // 解析 servlet-mapping
        for (Iterator<Element> ite = root.elementIterator("servlet-mapping"); ite.hasNext();){
            Element subElement = ite.next(); //得到每一个 servlet-mapping
            Mapping mapping = new Mapping();

            for (Iterator<Element> subIte = subElement.elementIterator(); subIte.hasNext();){
                Element element = subIte.next();

                if ("servlet-name".equals(element.getName())){
                    mapping.setName(element.getText());

                } else if("url-pattern".equals(element.getName())){
                    mapping.getUrlPattern().add(element.getText());
                }
            }
            mappingList.add(mapping);
        }

    }

    public static void main(String[] args) {
        WebDom4j webDom4j = new WebDom4j();
        webDom4j.parse(webDom4j.getDocument());

    }

}
