/**
 * @author Maserhe
 *
 * 一个请求与响应。就是一个 Dispatcher
 * @Date 2020-10-13  20:48
 */

package server;

import servlet.Servlet;
import util.IOCloseUtil;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable{
    private Request request;
    private Response response;

    // 监听端口， 和 状态码。
    private Socket client;
    private int statusCode;

    public Dispatcher(Socket client) {
        this.client = client; // 将局部变量的值赋值给成员变量。
        try {
            request = new Request(this.client.getInputStream());
            response = new Response(this.client.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            // 出现错误修改状态码，返回
            statusCode = 500;
            return;
        }
    }

    @Override
    public void run() {
        // 根据不同url 获取指定的 servlet 对象。
        Servlet servlet = WebApp.getServlet(request.getUrl());

        if (servlet == null) {
            this.statusCode = 404;
        } else {
            try {
                servlet.service(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                this.statusCode = 500;
            }
        }
        // 将响应结果返回客户端。
        response.pushToClient(statusCode);
        IOCloseUtil.closeAll(client);
    }
}
