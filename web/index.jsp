<%--
  Created by IntelliJ IDEA.
  User: yangch
  Date: 2019/10/17
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>登录页面</title>
  </head>
  <script>
  </script>
  <body>
    <div style="color: red" id="msg">欢迎登录</div>
    <form action="/login" method="post">
      用户名<input type="text" name="username"><br>
      密码<input type="text" name="password"><br>
      记住用户名<input type="checkbox" name="remember"><br>
      <input type="submit" value="登录">
    </form>
  </body>
</html>
