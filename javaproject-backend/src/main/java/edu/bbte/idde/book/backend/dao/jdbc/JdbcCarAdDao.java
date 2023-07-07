package edu.bbte.idde.book.backend.dao.jdbc;

import edu.bbte.idde.book.backend.config.Config;
import edu.bbte.idde.book.backend.config.ConfigFactory;
import edu.bbte.idde.book.backend.dao.CarAdDao;
import edu.bbte.idde.book.backend.model.CarAd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class JdbcCarAdDao implements CarAdDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcCarAdDao.class);
    private final DataSource dataSource;
    public Integer loggedQueriesDb = 0;
    public Integer loggedUpdatesDb = 0;
    private Config config = ConfigFactory.getConfig();
    //public boolean logquerybool = config.getLogQueries();
    //public boolean logupdatebool = config.getUpdateQueries();
    //itt probaltam beolvasni configbol de nem mukodott ezert hardcodeolt false es true-t raktam^
    public boolean logquerybool = false;
    public boolean logupdatebool = true;

    public JdbcCarAdDao() {
        dataSource = DataSourceFactory.getDataSource();
    }

    public void prepCarAd(CarAd carad, PreparedStatement prep) {
        try {
            prep.setString(1, carad.getModel());
            prep.setString(2, carad.getManufacturer());
            prep.setInt(3, carad.getPrice());
            prep.setString(4, carad.getQuality());
            prep.setInt(5, carad.getYearOfMake());
            prep.setLong(6, carad.getOwnerID());
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
    }

    public void prepCarAdWithId(CarAd carad, PreparedStatement prep) {
        try {
            prep.setString(1, carad.getModel());
            prep.setString(2, carad.getManufacturer());
            prep.setInt(3, carad.getPrice());
            prep.setString(4, carad.getQuality());
            prep.setInt(5, carad.getYearOfMake());
            prep.setLong(6, carad.getOwnerID());
            prep.setLong(7, carad.getId());
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
    }

    public void prepCarAdDelete(Long id, PreparedStatement prep) {
        try {
            prep.setLong(1, id);
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
    }

    public CarAd prepSqlToCarAd(Long id, ResultSet set) {
        try {
            return new CarAd(set.getLong("carAdId"),
                    set.getString("model"),
                    set.getString("manufacturer"),
                    set.getInt("price"),
                    set.getString("quality"),
                    set.getInt("yearOfMake"),
                    set.getLong("ownerID"));
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        return null;
    }

    public Collection<CarAd> prepSqlToCarAdAll(ResultSet set) {
        try {
            Collection<CarAd> carAds = new ArrayList<>();
            while (set.next()) {
                carAds.add(new CarAd(set.getLong("carAdID"),
                        set.getString("model"),
                        set.getString("manufacturer"),
                        set.getInt("price"),
                        set.getString("quality"),
                        set.getInt("yearOfMake"),
                        set.getLong("ownerID")));
            }
            return carAds;
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        //Collection<CarAd> carAds = new ArrayList<>();
        //return carAds;
        return Collections.emptyList();
    }

    @Override
    public void create(CarAd carad) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("insert into CarAds values(default, ?, ?, ?, ?, ?, ?)");
            prepCarAd(carad, prep);
            prep.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        if(logupdatebool){
            LOG.info("insert into CarAds values(default, ?, ?, ?, ?, ?, ?)");
        }
        loggedUpdatesDb = loggedUpdatesDb + 1;
    }

    @Override
    public CarAd findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from CarAds where carAdID = ?");
            prep.setLong(1, id);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                return prepSqlToCarAd(id, set);
            }
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        LOG.info("select * from CarAds where carAdID = ?");
        loggedQueriesDb = loggedQueriesDb + 1;
        return null;
    }

    @Override
    public Collection<CarAd> findAll() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from CarAds");
            ResultSet set = prep.executeQuery();
            return prepSqlToCarAdAll(set);
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        //Collection<CarAd> carAds = new ArrayList<>();
        //return carAds;
        if(logquerybool){
            LOG.info("select * from CarAds");
        }
        loggedQueriesDb = loggedQueriesDb + 1;
        return Collections.emptyList();
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("delete from CarAds where carAdID = ?");
            //CarAd carToDelete = findById(id);
            //prepCarAd(carToDelete, prep);
            //prep.setLong(1, id);
            prepCarAdDelete(id, prep);
            prep.executeUpdate();
            LOG.info("CarAd with id: " + id + " successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        if(logupdatebool){
            LOG.info("delete from CarAds where carAdID = ?");
        }
        loggedUpdatesDb = loggedUpdatesDb + 1;
    }

    @Override
    public void update(Long id, CarAd carad) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("update CarAds "
                            + "set model = ?, manufacturer = ?, price = ?, quality = ?, yearOfMake = ?, ownerID = ? "
                            + "where carAdID = ?");
            prepCarAdWithId(carad, prep);
            /*prep.setString(1, carad.getModel());
            prep.setString(2, carad.getManufacturer());
            prep.setInt(3, carad.getPrice());
            prep.setString(4, carad.getQuality());
            prep.setInt(5, carad.getYearOfMake());
            prep.setLong(6, carad.getOwnerID());
            prep.setLong(7, carad.getId());*/
            prep.executeUpdate();
            LOG.info("CarAd with id: " + id + " successfully updated.");
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        if(logupdatebool){
            LOG.info("update CarAds "
                    + "set model = ?, manufacturer = ?, price = ?, quality = ?, yearOfMake = ?, ownerID = ? "
                    + "where carAdID = ?");
        }
        loggedUpdatesDb = loggedUpdatesDb + 1;
    }

    @Override
    public Collection<CarAd> findByYearOfMake(Integer yearOfMake) {
        //Collection<CarAd> carAds = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from CarAds where carAdID = ?");
            prep.setLong(1, yearOfMake);
            ResultSet set = prep.executeQuery();
            return prepSqlToCarAdAll(set);
            /*while (set.next()) {
                carAds.add(new CarAd(set.getLong("carAdID"),
                        set.getString("model"),
                        set.getString("manufacturer"),
                        set.getInt("price"),
                        set.getString("quality"),
                        set.getInt("yearOfMake"),
                        set.getLong("ownerID")));
            }*/
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        //return carAds;
        //Collection<CarAd> carAds = new ArrayList<>();
        //return carAds;
        if(logquerybool){
            LOG.info("select * from CarAds where carAdID = ?");
        }
        loggedQueriesDb = loggedQueriesDb + 1;
        return Collections.emptyList();
    }

    public Integer getLoggedUpdatesDb(){
        return loggedUpdatesDb;
    }

    public Integer getLoggedQueriesDb(){
        return loggedQueriesDb;
    }
}
