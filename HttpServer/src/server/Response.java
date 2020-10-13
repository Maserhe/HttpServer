package server;

import util.IOCloseUtil;

import java.io.*;
import java.security.cert.CRL;
import java.util.concurrent.BlockingDeque;

public class Response {
    private StringBuilder headInfo; //响应头
    private StringBuilder content;  //响应内容.
    private int length;             // 字节长度。

    // 流
    private BufferedWriter bw;

    // 两个常量 一个空格，一个换行。
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";

    // 构造方法。


    public Response() {
        headInfo = new StringBuilder();
        content = new StringBuilder();

    }

    public Response(OutputStream os) {
        this();
        // 转换流。
        try {
            bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            headInfo = null;
        }
        // 构造正文部分。

    }
    // 构造正文部分。
    public Response print(String info){
        content.append(info);
        try {
            length += info.getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;    //可以一直连着使用。
    }
    // 构造不换行正文部分。
    public Response println(String Info){
        content.append(Info).append(CRLF);
        try {
            length += (Info + CRLF).getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }
    // 构造响应头。 传入状态码。
    private void createHeadInfo(int statusCode){
        headInfo.append("HTTP/1.1").append(BLANK).append(statusCode).append(BLANK);
        switch(statusCode) {
            case 200:
                headInfo.append("OK");
                break;
            case 500:
                headInfo.append("SERVER ERROR");
                break;
            default:
                headInfo.append("NOT FOUNT");
                break;
        }
        headInfo.append(CRLF);
        headInfo.append("content-Type:text/html;charset=utf-8").append(CRLF);
        headInfo.append("content-Length:" + length).append(CRLF);
        headInfo.append(CRLF);
        // 接下来需要拼接返回的 内容。

    }
    public void pushToClient(int statusCode){
        if (headInfo == null) statusCode = 500;
        this.createHeadInfo(statusCode);

        // 将头部和 返回内容响应回去。
        try {
            bw.write(headInfo.toString());
            bw.write(content.toString());
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.close();

    }
    public void close(){
        IOCloseUtil.closeAll(bw);
    }

}
