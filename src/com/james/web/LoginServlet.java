package com.james.web;

import com.james.service.UserService;
import com.james.utils.RequestUtil_V1_1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 不指定参数，默认输出全部请求参数、cookies、session信息
        RequestUtil_V1_1 util = new RequestUtil_V1_1.Builder(request).build();
        util.log();

        RequestUtil_V1_1 util2 = new RequestUtil_V1_1.Builder(request)
                .buildInfoType(RequestUtil_V1_1.INFO_TYPE.COOKIES) // 输出cookies信息
                .buildParam("username") // 输出 username 的请求参数
                .build();


        RequestUtil_V1_1 util3 = new RequestUtil_V1_1.Builder(request)
                .buildInfoType(RequestUtil_V1_1.INFO_TYPE.SESSION)
                .buildParam("username", "password") // 指定输出多参数， 参数列表是个可变参数
                .build();

    }
}
