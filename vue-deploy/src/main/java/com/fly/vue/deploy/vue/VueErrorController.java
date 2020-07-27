package com.fly.vue.deploy.vue;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

import static com.fly.vue.deploy.vue.VueCookieInterceptor.VUE_HTML_COOKIE_NAME;

@RequestMapping("/error")
public class VueErrorController extends AbstractErrorController {

    private static final String ONLINE_SAIL = VUE_HTML_COOKIE_NAME;

    private static final String ERROR_BEFORE_PATH = "javax.servlet.error.request_uri";

    public VueErrorController(DefaultErrorAttributes defaultErrorAttributes) {
        super(defaultErrorAttributes);
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public ModelAndView errorHtml(HttpServletRequest httpServletRequest, HttpServletResponse response, @CookieValue(name = ONLINE_SAIL, required = false, defaultValue = "") String cookie) {
        final Object attribute = httpServletRequest.getAttribute(ERROR_BEFORE_PATH);
        if (cookie.length() > 0 && Objects.nonNull(attribute)) {
            response.setStatus(HttpStatus.OK.value());
            String requestURI = attribute.toString();
            if (!requestURI.startsWith(cookie)) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setStatus(HttpStatus.OK);
                String viewName = "forward:" + cookie + requestURI;
                modelAndView.setViewName(viewName);
                return modelAndView;
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.setViewName("forward:/test/index.html");
        return modelAndView;
    }
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        final Map<String, Object> errorAttributes = getErrorAttributes(request, true);
        return new ResponseEntity<>(errorAttributes, status);
    }
}