package servlets;

import edu.bbte.idde.book.backend.dao.CarAdDao;
import edu.bbte.idde.book.backend.dao.DaoFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/carAdsList")
public class CarAdListServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(FtlServlet.class);
    private CarAdDao dao;
    private Template template;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = DaoFactory.getInstance().getCarAdDao();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassForTemplateLoading(FtlServlet.class, "/");
        try {
            template = cfg.getTemplate("carAds.ftl");
        } catch (IOException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String, Object> templateData = new ConcurrentHashMap<>();
            LOG.info("{}", dao.findAll());
            templateData.put("carads", dao.findAll());
            template.process(templateData, resp.getWriter());
        } catch (TemplateException e) {
            throw new ServletException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}