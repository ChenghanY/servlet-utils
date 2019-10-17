package com.james.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author james
 * @Description
 * @Date 2019/10/16
 */
public class RequestUtil_V1 {
    public final static int INFO_TYPE_NORMAL = 1;
    public final static int INFO_TYPE_COOKIES = 2;
    public final static int INFO_TYPE_SESSION = 3;
    public final static int INFO_TYPE_ALL = 4;

    private HttpServletRequest request;
    // 未指定infoType时, 默认为输出所有信息
    private int infoType = INFO_TYPE_ALL;
    private String[] params;
    private Map<String, String[]> parameterMap;

    private RequestUtil_V1() {
    }

    private void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    private void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    private void setParams(String[] params) {
        this.params = params;
    }

    private void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public static class Builder {
        RequestUtil_V1 servletRequestUtil = new RequestUtil_V1();

        public Builder(HttpServletRequest request) {
            servletRequestUtil.setRequest(request);
            servletRequestUtil.setParameterMap(request.getParameterMap());
        }

        /**
         * 只提供一个给外部获取实例的方法
         * @return ServletRequestUtil的实例
         * @see RequestUtil_V1
         */
        public RequestUtil_V1 build() {
            return servletRequestUtil;
        }

        public Builder buildInfoType(int infoType){
            servletRequestUtil.setInfoType(infoType);
            return this;
        }

        public Builder buildParam(String ... params) {
            servletRequestUtil.setParams(params);
            return this;
        }
    }

    /**
     * 暴露给外层的核心方法
     */
    public void log() {
        assert request != null;
        if (params == null) {
            printRequestInfo(request, infoType, parameterMap);
        } else {
            printRequestInfo(request, infoType, parameterMap, params);
        }
    }

    /**
     * 根据infoType打印HttpServletRequest的调试信息
     * @param request  请求
     * @param infoType  打印的类别
     * @param parameterMap 请求参数列表
     * @param parameter 指定需要打印的请求参数
     */
    private static synchronized void printRequestInfo(HttpServletRequest request,
                                                     int infoType,
                                                     Map<String, String[]> parameterMap,
                                                     String... parameter) {
        // 建立请求时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dateTime = sdf.format(date);

        StringBuilder sb = new StringBuilder();
        sb.append("===============").append(dateTime).append("===============").append("\n");
        sb.append("URL: ").append(request.getRequestURL()).append("\n");
        if (INFO_TYPE_NORMAL == infoType){
            // 请求未携带参数，直接返回
            if (parameterMap.size() == 0) {
                System.out.println(sb.toString());
                return;
            }
            // 获取servlet信息 todo INFO_TYPE_NORMAL 时直接返回
            appendServletInfo(parameterMap, sb, parameter);
            return;
        }

        // 获取servlet信息
        appendServletInfo(parameterMap, sb, parameter);

        if (INFO_TYPE_ALL == infoType) {
            appendCookiesInfo(request, parameterMap, sb);
            appendSessionInfo(request, sb);
        }
        if (INFO_TYPE_COOKIES == infoType) {
            appendCookiesInfo(request, parameterMap, sb);
        }
        if (INFO_TYPE_SESSION == infoType) {
            appendSessionInfo(request, sb);
        }

        System.out.println(sb.toString());
    }


    private static void appendCookiesInfo(HttpServletRequest request, Map<String, String[]> parameterMap, StringBuilder sb) {
        sb.append("Cookies: {").append("\n");
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            String cookieName = cookies[i].getName();
            sb.append("          ").append("{");
            sb.append(cookieName).append(" = ");
            sb.append(cookies[i].getValue());
            sb.append("}");
            if (i != cookies.length - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("         }").append("\n");
    }

    private static void appendSessionInfo(HttpServletRequest request, StringBuilder sb) {
        sb.append("Session: {").append("\n");
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();

        if (attributeNames.hasMoreElements()) {
            String key = attributeNames.nextElement();
            String value = session.getAttribute(key).toString();
            sb.append("          ").append("{");
            sb.append(key).append(" = ").append(value);
            sb.append("}");
        }
        while (attributeNames.hasMoreElements()) {
            sb.append(",").append("\n");
            String key = attributeNames.nextElement();
            String value = session.getAttribute(key).toString();
            sb.append("          ").append("{");
            sb.append(key).append(" = ").append(value);
            sb.append("}");
        }
        sb.append("\n").append("         }").append("\n");
    }

    private static void appendServletInfo(Map<String, String[]> parameterMap, StringBuilder sb, String... parameter) {
        Set<String> keys = parameterMap.keySet();
        sb.append("请求参数: {").append("\n");
        if (parameter.length == 0) { // 未指定参数，所有值都输出
            for (String key : keys) {
                sb.append("          ").append("{");
                sb.append(key).append(" = ");
                // 拿到键的值
                String[] value = parameterMap.get(key);
                if (value == null) {
                    sb.append("null");
                } else {
                    sb.append(Arrays.toString(value));
                }
                sb.append("}");
                sb.append("\n");
            }
        } else { // 指定了参数，则只显示参数的值
            for (String p : parameter) {
                for (String key : keys) {
                    if (key.equals(p)) {
                        sb.append("调试参数 ").append(key).append(": ");
                        // 拿到键的值
                        String[] value = parameterMap.get(key);
                        if (value == null) {
                            sb.append("null");
                        } else {
                            sb.append(Arrays.toString(value));
                        }
                        sb.append("\n");
                    }
                }
            }
        }
        sb.append("         }").append("\n");
    }
}
