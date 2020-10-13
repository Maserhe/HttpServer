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
    private boolean isShutdown = false;

    public void start(int port){
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            isShutdown = true;
        }
        receive();
    }
    public void stop(){
        isShutdown = true;
        IOCloseUtil.closeAll(server); // 关闭服务。
    }

    private void receive(){

        try {
            while (!isShutdown) {
                //(1) 开启监听。
                Socket client = server.accept();
                //(2) 创建线程类对象。
                Dispatcher dispatcher = new Dispatcher(client);
                // 创建代理类 启动线程。

                new Thread(dispatcher).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            this.stop();    //关闭服务器。
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8888);

    }
}
