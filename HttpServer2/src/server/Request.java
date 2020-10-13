package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


/**
 * 分解保存。
 * 请求参数。
 *
 */
public class Request {
    private InputStream is;
    private String info;    // 请求的字符串，请求样式，请求路径，参数，协议，协议版本。
    private String method;
    private String url;

    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";

    private Map<String, List<String>> parameterMapValues;   //参数。

    private String parseString;
    // 构造。

    public Request() {
        parameterMapValues = new TreeMap<>();
        method = "";
        url = "";
        method = "";
        info = "";
    }

    public Request(InputStream is) {
        // 先调用无参构造。
        this();
        this.is = is;
        byte[] data = new byte[1024 * 20];
        int len = 0;
        try {
            len = is.read(data);
            info = new String(data, 0, len);
            this.parseRequestInfo();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    // 接续请求数据。

    /**
     * 请求参数。
     * 请求路径。
     * 请求方式。
     *
     */
    public void parseRequestInfo(){
        // 先 把第一行 拿出来。
        String first = info.substring(0, info.indexOf(CRLF)).trim();

        // 分解出请求方式。
        int index = info.indexOf('/');
        this.method = first.substring(0, index).trim();
        // 分解url
        String urlString = first.substring(index, first.indexOf("HTTP/")); //
        // 判断请求方式。
        //http://localhost:8888/log?username=982289931%40qq.com&password=12123312

        if ("get".equalsIgnoreCase(method)){
            if (urlString.contains("?")){
                String [] urlArray = urlString.split("\\?");
                this.url = urlArray[0];
                parseString = urlArray[1];
            }
            else this.url = urlString;
        } else {
            // post 请求。有一个空行。
            this.url = urlString;
            parseString = info.substring(info.lastIndexOf(CRLF)).trim();

        } if (parseString == null || parseString.equals("")) return;

        // 分解请求信息。
        this.parseParam(parseString);
    }

    public String getUrl() {
        return url;
    }

    //username=dssfas&password=fadff&hobby=ball&hobby=paint
    private void parseParam(String parseString){
        //
        String[] token = parseString.split("&");
        for (int i = 0; i < token.length; i ++ ){
            String keyValues = token[i];
            String[] keyValue = keyValues.split("=");

            if (keyValue.length == 1){ //说明 只有等号而没有值。
                // 将长度扩成二倍，然后第二个元素置空。
                keyValue = Arrays.copyOf(keyValue, 2);
                keyValue[1] = null;
            }
            // 将 表单元素的name 与 name 存到Map
            String key = keyValue[0].trim();
            String value = keyValue[1] == null? null: decode(keyValue[1].trim(), "utf-8");

            if (!parameterMapValues.containsKey(key)){
                parameterMapValues.put(key, new ArrayList<>());
            }
            // 添加value.
            parameterMapValues.get(key).add(value);
        }

    }
    public String[] getParameterValues(String name){
        //根据key 获取 value.
        List<String> values = parameterMapValues.get(name);
        if (values == null) return null;
        else return values.toArray(new String[0]);  //根据实际长度获取，创建数组。
    }

    public String getParameter(String name){
        //根据key 获取 value.
        String[] values = this.getParameterValues(name);
        if (values == null) return null;
        else {
            return values[0];
        }
    }
    // 处理中文。
    private String decode(String value, String code){
        try {
            return URLDecoder.decode(value,code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 请求参数。

    // 测试参数结果。
    public void show(){
        System.out.println(url);
        System.out.println(method);
        System.out.println(parseString);

    }

    public static void main(String[] args) {

        Request request = new Request();
        // 测试数据是否成功拿出。保存。
        request.parseParam("username=%E4%B8%AD%E6%96%87%E8%A7%A3%E6%9E%90&password=hahahfa&hobby=ball&hobby=paint");
        System.out.println(request.parameterMapValues);

        String[] str = request.getParameterValues("hobby");
        for (String i: str){
            System.out.println(i);
        }



    }
}
