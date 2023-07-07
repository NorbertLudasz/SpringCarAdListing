package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.book.backend.dao.CarAdDao;
import edu.bbte.idde.book.backend.dao.DaoFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/lognum")
public class LogServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(CarAdServlet.class);

    // JSON írást támogató objektum
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CarAdDao carAdMemoryDao = DaoFactory.getInstance().getCarAdDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("GET /lognum");

        // jelezzük a HTTP válaszban, hogy JSON típusú kimenetet küldünk
        resp.setHeader("Content-Type", "application/json");

        //Integer updatenum = 10;
        //Integer querynum = 5;
        Integer updatenum = carAdMemoryDao.getLoggedUpdatesDb();
        Integer querynum = carAdMemoryDao.getLoggedQueriesDb();
        String result = updatenum.toString() + querynum.toString();
        LOG.info("result = {}", result);
        objectMapper.writeValue(resp.getOutputStream(), result);
        //return result;
    }

}

