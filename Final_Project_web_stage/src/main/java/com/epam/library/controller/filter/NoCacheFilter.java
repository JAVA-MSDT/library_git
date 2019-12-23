package com.epam.library.controller.filter;

import com.epam.library.entity.User;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.RedirectTo;
import com.epam.library.util.constant.entityconstant.UserConstant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Experimental class
 * To prevent the user from backing into his account after logging out, by clearing the cache.
 */
//@WebFilter("/controller")
public class NoCacheFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute(UserConstant.USER_ATTRIBUTE);
        String command = request.getParameter(CommandName.COMMAND_NAME);
        if (user == null && !command.equalsIgnoreCase(CommandName.LOGIN)) {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revlaidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.sendRedirect(RedirectTo.LOGIN_PAGE);
        } else {
            filterChain.doFilter(servletRequest, response);
        }


    }

    @Override
    public void destroy() {

    }
}
