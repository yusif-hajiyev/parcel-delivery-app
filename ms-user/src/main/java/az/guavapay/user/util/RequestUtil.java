package az.guavapay.user.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;

public class RequestUtil {

    public static String getHeaderValue(String headerName, HttpServletRequest servletRequest) {
        if (servletRequest.getHeaderNames() == null) return null;
        return Collections.list(servletRequest.getHeaderNames())
                .stream()
                .filter(i -> Objects.nonNull(i) && headerName.equalsIgnoreCase(i))
                .findFirst()
                .map(servletRequest::getHeader)
                .orElse(null);
    }
}
