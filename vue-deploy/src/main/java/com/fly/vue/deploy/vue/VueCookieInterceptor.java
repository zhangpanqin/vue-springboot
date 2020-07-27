package com.fly.vue.deploy.vue;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author 张攀钦
 * @date 2020-07-24-11:14
 */
public class VueCookieInterceptor implements HandlerInterceptor {
    public static final String VUE_HTML_COOKIE_NAME = "x_vue_path";

    public static final String VUE_HTML_COOKIE_VALUE = "/test";

    /**
     * 配置请求资源路径 /test 下全部加上 cookie
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Cookie cookieByName = getCookieByName(request, VUE_HTML_COOKIE_NAME);
        if (Objects.isNull(cookieByName)) {
            final Cookie cookie = new Cookie(VUE_HTML_COOKIE_NAME, VUE_HTML_COOKIE_VALUE);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
        return true;
    }

    public static Cookie getCookieByName(HttpServletRequest httpServletRequest, String cookieName) {
        final Cookie[] cookies = httpServletRequest.getCookies();
        if (Objects.isNull(cookieName) || Objects.isNull(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            final String name = cookie.getName();
            if (Objects.equals(cookieName, name)) {
                return cookie;
            }
        }
        return null;
    }
}
