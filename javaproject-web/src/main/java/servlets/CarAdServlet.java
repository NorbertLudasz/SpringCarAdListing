package servlets;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.book.backend.dao.CarAdDao;
import edu.bbte.idde.book.backend.dao.DaoFactory;
import edu.bbte.idde.book.backend.model.CarAd;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/carAds")
public class CarAdServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(CarAdServlet.class);

    // JSON írást támogató objektum
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CarAdDao carAdMemoryDao = DaoFactory.getInstance().getCarAdDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("GET /carAds");

        // jelezzük a HTTP válaszban, hogy JSON típusú kimenetet küldünk
        resp.setHeader("Content-Type", "application/json");

        if (req.getParameterMap().containsKey("id")) {
            try {
                Long carid = Long.parseLong(req.getParameter("id"));
                CarAd carad = carAdMemoryDao.findById(carid);
                if (carad == null) {
                    resp.sendError(404, "get error, carad not found with given id");
                    LOG.info("get error non-existent id");
                } else {
                    objectMapper.writeValue(resp.getOutputStream(), carAdMemoryDao.findById(carid));
                }
            } catch (NumberFormatException e) {
                resp.sendError(400, "get error, parselong exception");
                LOG.info(e.getMessage());
            }
        } else {
            objectMapper.writeValue(resp.getOutputStream(), carAdMemoryDao.findAll());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("POST /carAds");
        // megpróbáljuk a request body-t JSON-ként értelmezni s a megadott osztály példányára alakítani
        // sok hibakezelés szükséges ennél a lépésnél
        try {
            CarAd carAd = objectMapper.readValue(req.getInputStream(), CarAd.class);
            if (carAd.getModel() == null
                    || carAd.getManufacturer() == null
                    || carAd.getPrice() == null
                    || carAd.getQuality() == null
                    || carAd.getYearOfMake() == null) {
                //resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.sendError(400, "post error not all fields entered");
                LOG.info("post error not all fields entered");
            } else {
                carAdMemoryDao.create(carAd);
                LOG.info("Received car ad: {}", carAdMemoryDao);
            }
        } catch (JsonMappingException e) {
            resp.sendError(400, "post error objectmapper failed to read input");
            LOG.info(String.valueOf(e.getCause()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("PUT /carAds");
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            CarAd carAd = objectMapper.readValue(req.getInputStream(), CarAd.class);
            CarAd carad = carAdMemoryDao.findById(id);
            if (carad == null) {
                resp.sendError(404, "put error, carad not found with given id");
                LOG.info("put error non-existent id");
            } else {
                carAdMemoryDao.update(id, carAd);
                LOG.info("Updated car ad: {}", carAdMemoryDao);
            }
        } catch (NumberFormatException e) {
            resp.sendError(400, "put error, parselong exception");
            LOG.info(e.getMessage());
        } catch (JsonMappingException e) {
            resp.sendError(400, "put error objectmapper failed to read input");
            LOG.info(String.valueOf(e.getCause()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("DELETE /carAds");
        if (req.getParameterMap().containsKey("id")) {
            try {
                Long carid = objectMapper.readValue(req.getParameter("id"), Long.class);
                if (carAdMemoryDao.findById(carid) == null) {
                    resp.sendError(404, "delete error, carad not found with given id");
                    LOG.info("delete error non-existent id");
                } else {
                    carAdMemoryDao.delete(carid);
                    LOG.info("Deleted car ad: {}", carAdMemoryDao);
                }
            } catch (JsonMappingException e) {
                resp.sendError(400, "delete error objectmapper failed to read input");
                LOG.info(String.valueOf(e.getCause()));
            }
        } else {
            resp.sendError(400, "delete error no id field given as parameter");
            LOG.info("delete error no given id");
        }
    }
}
