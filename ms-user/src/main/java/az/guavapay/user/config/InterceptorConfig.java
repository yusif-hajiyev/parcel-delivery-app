package az.guavapay.user.config;

import az.guavapay.user.util.Constants;
import az.guavapay.user.util.RequestUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterceptorConfig extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        MDC.put(Constants.URI, request.getRequestURI());
        MDC.put(Constants.IP, RequestUtil.getHeaderValue("X-Forwarded-For", request));
        MDC.put(Constants.AGENT, RequestUtil.getHeaderValue("User-Agent", request));

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        MDC.remove(Constants.URI);
        MDC.remove(Constants.IP);
        MDC.remove(Constants.AGENT);
        super.postHandle(request, response, handler, modelAndView);
    }

}


