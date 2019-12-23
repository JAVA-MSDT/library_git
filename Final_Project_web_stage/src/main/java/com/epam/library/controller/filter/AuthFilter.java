package com.epam.library.controller.filter;

import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.Role;
import com.epam.library.util.constant.CommandName;
import com.epam.library.util.constant.RedirectTo;
import com.epam.library.util.constant.entityconstant.UserConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Authorised filter to direct the users depends on their role to the right page where they are authorised
 * to use and to go
 */
@WebFilter("/controller")
public class AuthFilter implements Filter {

    private static final List<String> ADMINISTRATION_COMMANDS = Arrays.asList(
            CommandName.PROFILE,

            CommandName.ADMINISTRATION_BOOK_STORE,
            CommandName.ADMINISTRATION_EDIT_BOOK,
            CommandName.ADMINISTRATION_UPDATE_BOOK,

            CommandName.ADMINISTRATION_ORDER_LIST,
            CommandName.ADMINISTRATION_EDIT_ORDER,
            CommandName.ADMINISTRATION_UPDATE_ORDER,
            CommandName.ADMINISTRATION_SORT_ORDER,
            CommandName.ADMINISTRATION_SEARCH_ORDER,

            CommandName.ADMINISTRATION_DISPLAY_USER,
            CommandName.ADMINISTRATION_EDIT_USER,
            CommandName.ADMINISTRATION_UPDATE_USER,
            CommandName.ADMINISTRATION_SEARCH_USER,
            CommandName.ADMINISTRATION_SORT_USER,

            // Admin Only
            CommandName.ADMIN_REMOVE_BOOK,
            CommandName.ADMIN_REMOVE_USER
    );

    private final static List<String> USER_COMMANDS = Arrays.asList(
            CommandName.PROFILE,
            CommandName.DISPLAY_BOOK,
            CommandName.CONFIRM_ORDER,
            CommandName.USER_ORDER
    );

    private final static List<String> COMMON_COMMANDS = Arrays.asList(
            CommandName.REGISTRATION,
            CommandName.LOGIN,
            CommandName.LOGOUT,
            CommandName.CHANGE_LANGUAGE,
            CommandName.DISPLAY_BOOK,
            CommandName.VIEW_BOOK,
            CommandName.ORDER_BOOK,
            CommandName.SEARCH_BOOK,
            CommandName.SORT_BOOK
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(UserConstant.USER_ATTRIBUTE);
        String command = request.getParameter(CommandName.COMMAND_NAME);

        if (!isCommonCommand(command)) {
            if (user.getRole() == Role.LIBRARIAN && isAdministrationCommand(command)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (user.getRole() == Role.ADMIN && isAdministrationCommand(command)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (user.getRole() == Role.READER && isUserCommand(command)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(RedirectTo.LOGIN_PAGE);

            }
        } else {

            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * @param command to be checked if the administration authorised to access it or not
     * @return true if the admin authorised or false if it is not authorised
     */
    private boolean isAdministrationCommand(String command) {

        return ADMINISTRATION_COMMANDS.contains(command);
    }

    /**
     * @param command to be checked if the user authorised to access on of this command or not
     * @return true if the user authorised or false if it is not authorised
     */
    private boolean isUserCommand(String command) {
        return USER_COMMANDS.contains(command);
    }

    /**
     * @param command that are common for all the users registered or not
     * @return true if it is for all the users or false.
     */
    private boolean isCommonCommand(String command) {

        return COMMON_COMMANDS.contains(command);
    }
}
