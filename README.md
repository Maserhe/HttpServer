# 手写Http Server服务器
写了登陆功能，并返回登陆结果。
123.html是测试的登陆网页。
服务器默认监听端口为8888。
写的比较仓促就没有加数据库，一个测试账号。
- 账号:maserhe
- 密码:123456
这里的xml使用的是Dom4j解析。不过感觉还是SAX解析好用，可以看我博客SAX解析:[传送门](https://maserhe.top/2020/10/09/xml-jie-xi/)

httpServer没有加入多线程版本。
httpServer2是加入了多线程版本。

**多线程运行效果**

![image](https://github.com/Maserhe/HttpServer/blob/master/image/%E5%AA%92%E4%BD%931.gif)
