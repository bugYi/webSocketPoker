package com.bbg.open.b2b4pos.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bbg.open.b2b4pos.utils.StringUtils;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

    private List<String> allowUrls;
    
    public void setAllowUrls(List<String> allowUrls) {
        this.allowUrls = allowUrls;
    }

    //执行action之前来执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        
        String requestUri = request.getRequestURI();
        if(requestUri.startsWith(request.getContextPath())){
            requestUri = requestUri.substring(request.getContextPath().length(), requestUri.length());
        }
        //系统根目录
        if (StringUtils.equals("/",requestUri)) {
            return true;
        }
        //放行exceptUrls中配置的url
        for (String url:allowUrls
             ) {
            if(url.endsWith("/**")){
                if (requestUri.startsWith(url.substring(0, url.length() - 3))) {
                    return true;
                }
            } else if (requestUri.startsWith(url)) {
                return true;
            }
        }
        return true;
    }
}
