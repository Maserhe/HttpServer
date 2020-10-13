package server;

import servlet.Servlet;
import util.IOCloseUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * 服务器， 用于启动和停止服务。
 *
 */
public class Server {
    private ServerSocket server;

    public void start(int port){
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        receive();
    }
    public void stop(){


    }

    private void receive(){
        try {
            //(1) 开启监听。
            Socket client = server.accept();
            //(2) 获取用户请求。
            /*
            InputStream is = client.getInputStream();
            byte[] buf = new byte[1024 * 20];
            int len = is.read(buf);
            System.out.println(new String(buf,0, len));
            */
            // 封装请求信息。
            Request req = new Request(client.getInputStream());

            /** 做出响应 **/
            /*
            StringBuilder sb = new StringBuilder();
            sb.append("HTTP/1.1").append(" ").append(200).append(" ").append("OK").append("\r\n");
            sb.append("content-Type:text/html;charset=utf-8").append("\r\n");
            // 内容
            String str = "<html><head><title>响应成功</title></head><body><h1>成功！！！</h1></body></html>";
            sb.append("content-Length:" + str.getBytes().length).append("\r\n");
            sb.append("\r\n").append(str);

            // 通过输出流将信息发送出去。
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "utf-8"));

            bw.write(sb.toString());

            bw.flush();
            bw.close();
            */
            Response response = new Response(client.getOutputStream());
            Servlet servlet = WebApp.getServlet(req.getUrl());

            int statusCode = 200;
            if (servlet == null){
                statusCode = 404;
            }
            // 调用Servlet 的 服务方法。
            try {
                servlet.service(req, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.pushToClient(statusCode);

            IOCloseUtil.closeAll(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8888);

    }
}
