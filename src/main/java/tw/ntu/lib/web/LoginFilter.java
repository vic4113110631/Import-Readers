package tw.ntu.lib.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        boolean loggedIn = session != null && session.getAttribute("userName") != null;
        String loginURI = req.getContextPath() + "/Login";

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if(loggedIn || path.equals("/")
                    || path.equals("/Login")
                    || path.equals("/Login.do")
                    || path.matches(".*(css|jpg|png|gif|js|map|woff|woff2|ttf)")){
            chain.doFilter(request, response);
        }else{
            res.sendRedirect(loginURI);
        }

    }

    @Override
    public void destroy() {

    }
}
