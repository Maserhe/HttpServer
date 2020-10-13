package servlet;

import server.Request;
import server.Response;

public abstract class Servlet {  //是所有Servlet的父类。
    public void service(Request request, Response response) throws Exception{
        this.doGet(request, response);
        this.doPost(request, response);
    }
    public abstract void doGet(Request request, Response response) throws Exception;
    public abstract void doPost(Request request, Response response) throws Exception;
}
