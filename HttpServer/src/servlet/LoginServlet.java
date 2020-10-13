package servlet;

import server.Request;
import server.Response;

public class LoginServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) throws Exception {
        // 获取请求参数。
        String name = request.getParameter("username");
        String pwd = request.getParameter("password");

        if (login(name, pwd)) {
            response.println(name + "登陆成功！！");
        } else {
            response.println(name + "登陆失败");
        }
    }
    private boolean login(String name, String pwd){
        if ("maserhe".equals(name) && "123456".equals(pwd)) return true;
        else return false;
    }
    @Override
    public void doPost(Request request, Response response) throws Exception {

    }
}
