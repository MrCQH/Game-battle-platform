package com.kob.backend.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * cors + 预检请求
 * w3c规范要求，对那些可能对服务器数据产生副作用的HTTP 请求方法（特别是 GET 以外的 HTTP 请求，或者搭配某些 MIME 类型的 POST 请求）
 * 浏览器必须首先使用 OPTIONS 方法发起一个预检请求（preflight request），从而获知服务端是否允许该跨域请求。服务器确认允许之后，才发起实际的 HTTP 请求
 *
 * CORS规定，Content-Type不属于以下MIME类型的，都属于预检请求：
 * application/x-www-form-urlencoded
 * multipart/form-data
 * text/plain
 *
 * 所以 application/json的请求 会在正式通信之前，增加一次"预检"请求，这次"预检"请求会带上头部信息 Access-Control-Request-Headers: Content-Type:
 * OPTIONS /api/test HTTP/1.1
 * Origin: http://foo.example
 * Access-Control-Request-Method: POST
 * Access-Control-Request-Headers: Content-Type
 * ... 省略了一些
 *
 * 服务器回应时，返回的头部信息如果不包含Access-Control-Allow-Headers: Content-Type则表示不接受非默认的的Content-Type。即出现以下错误：
 * Request header field Content-Type is not allowed by Access-Control-Allow-Headers in preflight response.
 */
@Configuration
public class CorsConfig implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + this.getClass().getName());
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String origin = request.getHeader("Origin");
        if(origin!=null) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        String headers = request.getHeader("Access-Control-Request-Headers");
        if(headers!=null) {
            response.setHeader("Access-Control-Allow-Headers", headers);
            response.setHeader("Access-Control-Expose-Headers", headers);
        }

        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {
    }
}
