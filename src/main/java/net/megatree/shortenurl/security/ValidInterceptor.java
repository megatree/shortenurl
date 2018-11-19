package net.megatree.shortenurl.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by mythss on 2018-11-19.
 */
@Component
public class ValidInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //携带信息过长，ban
        //超时时间过长，ban
        if (request.getContentLengthLong() > 4096L ) {
            ban(response);
            return false;
        }

        String longUrl = request.getParameter("longUrl");
        if (StringUtils.isBlank(longUrl)||isValidUrl(longUrl)){
            return true;
        }

        //参数不合法
        ban(response);
        return false;
    }

    private void ban(HttpServletResponse response){
        try {
            response.sendRedirect("ban");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidUrl(String urlString){
        URI uri = null;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        if(uri.getHost() == null){
            return false;
        }
        if(uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https")){
            return true;
        }
        return false;
    }
}
