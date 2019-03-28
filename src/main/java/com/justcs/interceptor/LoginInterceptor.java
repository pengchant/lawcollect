package com.justcs.interceptor;

import com.justcs.utils.Constrants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 自定义登录拦截器某些方法需要登录才能访问
 */
public class LoginInterceptor implements HandlerInterceptor {


    /**
     * 请求处理之前执行的方法
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            // 获取session
            HttpSession session = request.getSession();
            Object record = session.getAttribute(Constrants.CURRENT_USER);
            if (record != null) {
                return true;
            } else {
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return false;
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 请求处理之后执行该方法
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * 请求处理完成之后执行的方法
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }


}
