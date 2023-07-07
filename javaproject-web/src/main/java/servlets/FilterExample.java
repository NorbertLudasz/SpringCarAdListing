package servlets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter(urlPatterns = {"/carAdsList"})
public class FilterExample extends HttpFilter {
    private static final Logger LOG = LoggerFactory.getLogger(FilterExample.class);

    @Override
    public void init() {
        LOG.info("Initializing example filter");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // naplózzuk az összes HTTP hívást
        // itt végezhetnénk bármilyen ellenőrzést
        LOG.info("{} {}", req.getMethod(), req.getRequestURI());
        HttpSession currentsession = req.getSession(false);

        // mindig továbbengedjük a szűrőláncot
        if (currentsession == null) {
            res.sendRedirect(req.getContextPath() + "/login");
        } else if ("user".equals(currentsession.getAttribute("user"))) {
            //LOG.info("{}", currentsession.getAttribute("user"));
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
