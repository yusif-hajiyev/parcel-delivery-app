package com.guavapay.parcel.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.guavapay.parcel.util.Constants.*;
import static com.guavapay.parcel.util.RequestUtil.getHeaderValue;

@Component
public class InterceptorConfig extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(URI, request.getRequestURI());
        MDC.put(IP, getHeaderValue("X-Forwarded-For", request));
        MDC.put(AGENT, getHeaderValue("User-Agent", request));
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        MDC.remove(URI);
        MDC.remove(IP);
        MDC.remove(AGENT);
        super.postHandle(request, response, handler, modelAndView);
    }

}


