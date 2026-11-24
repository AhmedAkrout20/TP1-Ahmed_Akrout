package isep.lab1.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtil {

    public static final int ONE_WEEK = 7 * 24 * 60 * 60;
    public static final int ONE_YEAR = 365 * 24 * 60 * 60;

    private CookieUtil() {
    }

    public static void add(HttpServletRequest req, HttpServletResponse resp,
                           String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8));
        cookie.setMaxAge(maxAgeSeconds);
        cookie.setPath(path(req));
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);
    }

    public static String get(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie c : cookies) {
            if (c.getName().equals(name)) {
                return URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    public static void delete(HttpServletRequest req, HttpServletResponse resp, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath(path(req));
        resp.addCookie(cookie);
    }

    private static String path(HttpServletRequest req) {
        String ctx = req.getContextPath();
        return ctx.isEmpty() ? "/" : ctx;
    }
}
