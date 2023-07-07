package servlets;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.book.backend.dao.CarOwnerDao;
import edu.bbte.idde.book.backend.dao.DaoFactory;
import edu.bbte.idde.book.backend.model.CarOwner;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/carOwners")
public class CarOwnerServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(CarAdServlet.class);

    // JSON írást támogató objektum
    private final ObjectMapper objectMapper = new ObjectMapper();

    //private final MemCarOwnerDao carOwnerMemoryDao = new MemCarOwnerDao();
    //private final JdbcCarOwnerDao carOwnerMemoryDao = new JdbcCarOwnerDao();
    private final CarOwnerDao carOwnerMemoryDao = DaoFactory.getInstance().getCarOwnerDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("GET /carOwners");

        // jelezzük a HTTP válaszban, hogy JSON típusú kimenetet küldünk
        resp.setHeader("Content-Type", "application/json");

        if (req.getParameterMap().containsKey("id")) {
            try {
                Long carid = Long.parseLong(req.getParameter("id"));
                CarOwner carowner = carOwnerMemoryDao.findById(carid);
                if (carowner == null) {
                    resp.sendError(404, "get error, carowner not found with given id");
                    LOG.info("get error non-existent id");
                } else {
                    objectMapper.writeValue(resp.getOutputStream(), carOwnerMemoryDao.findById(carid));
                }
            } catch (NumberFormatException e) {
                resp.sendError(400, "get error, parselong exception");
                LOG.info(e.getMessage());
            }
        } else {
            objectMapper.writeValue(resp.getOutputStream(), carOwnerMemoryDao.findAll());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("POST /carOwners");
        // megpróbáljuk a request body-t JSON-ként értelmezni s a megadott osztály példányára alakítani
        // sok hibakezelés szükséges ennél a lépésnél
        try {
            CarOwner carOwner = objectMapper.readValue(req.getInputStream(), CarOwner.class);
            if (carOwner.getName() == null
                    || carOwner.getNationality() == null
                    || carOwner.getPersonalEmail() == null
                    || carOwner.getPhoneNumber() == null
                    || carOwner.getId() == null) {
                //resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.sendError(400, "post error not all fields entered");
                LOG.info("post error not all fields entered");
            } else {
                carOwnerMemoryDao.create(carOwner);
                LOG.info("Received car owner: {}", carOwnerMemoryDao);
            }
        } catch (JsonMappingException e) {
            resp.sendError(400, "post error objectmapper failed to read input");
            LOG.info(String.valueOf(e.getCause()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("PUT /carOwners");
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            CarOwner carOwner = objectMapper.readValue(req.getInputStream(), CarOwner.class);
            CarOwner carowner = carOwnerMemoryDao.findById(id);
            if (carowner == null) {
                resp.sendError(404, "put error, carowner not found with given id");
                LOG.info("put error non-existent id");
            } else {
                carOwnerMemoryDao.update(id, carOwner);
                LOG.info("Updated car ad: {}", carOwnerMemoryDao);
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
        LOG.info("DELETE /carOwners");
        if (req.getParameterMap().containsKey("id")) {
            try {
                Long ownerid = objectMapper.readValue(req.getParameter("id"), Long.class);
                if (carOwnerMemoryDao.findById(ownerid) == null) {
                    resp.sendError(404, "delete error, carowner not found with given id");
                    LOG.info("delete error non-existent id");
                } else {
                    carOwnerMemoryDao.delete(ownerid);
                    LOG.info("Deleted car owner: {}", carOwnerMemoryDao);
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
