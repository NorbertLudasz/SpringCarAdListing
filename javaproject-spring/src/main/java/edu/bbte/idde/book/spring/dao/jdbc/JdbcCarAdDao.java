package edu.bbte.idde.book.spring.dao.jdbc;

import edu.bbte.idde.book.spring.dao.CarAdDao;
import edu.bbte.idde.book.spring.model.CarAd;
import edu.bbte.idde.book.spring.model.CarOwner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcCarAdDao implements CarAdDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcCarAdDao.class);
    @Autowired
    private JdbcCarOwnerDao ownerDao;
    @Autowired
    private DataSource dataSource;

    @Override
    public CarAd saveAndFlush(CarAd entity) {
        Long entityid = entity.getId();
        Optional<CarAd> optional = findById(entityid);
        LOG.info("carad saveandflush optional at start = {}", optional);
        if (optional.isPresent()) {
            CarAd carAd = optional.get();
            LOG.info("carad saveandflush optionalget result in update s&f = {}", carAd);
            carAd = entity;
            LOG.info("carad after being given entity = {}", entity);
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement prep = connection
                        .prepareStatement("update CarAds "
                                + "set model = ?, manufacturer = ?, price = ?, "
                                + "quality = ?, yearOfMake = ?, ownerID = ? "
                                + "where carAdID = ?");
                prep.setString(1, carAd.getModel());
                prep.setString(2, carAd.getManufacturer());
                prep.setInt(3, carAd.getPrice());
                prep.setString(4, carAd.getQuality());
                prep.setInt(5, carAd.getYearOfMake());
                prep.setLong(6, carAd.getCarOwner().getId());
                prep.setLong(7, carAd.getId());
                prep.executeUpdate();
                LOG.info("CarOwner with id: " + entityid + " successfully updated.");
                return carAd;
            } catch (SQLException e) {
                LOG.error("Hiba: {}", e.toString());
                return null;
            }
        }
        CarOwner carowner = entity.getCarOwner();
        LOG.info("{}", entity);
        Long carownerid = carowner.getId();
        try (PreparedStatement prep1 = dataSource.getConnection()
                .prepareStatement("SELECT * FROM CarOwners where ownerID = ?")) {
            prep1.setLong(1, carownerid);
            ResultSet set = prep1.executeQuery();
            String query = "INSERT INTO CarAds values (default, ?, ?, ?, ?, ?, ?)";
            if (set.next()) {
                try (PreparedStatement stmt = dataSource
                        .getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, entity.getModel());
                    stmt.setString(2, entity.getManufacturer());
                    stmt.setInt(3, entity.getPrice());
                    stmt.setString(4, entity.getQuality());
                    stmt.setInt(5, entity.getYearOfMake());
                    stmt.setLong(6, carowner.getId());
                    stmt.executeUpdate();
                    ResultSet keys = stmt.getGeneratedKeys();
                    if (keys.next()) {
                        entity.setId(keys.getLong(1));
                    }
                    LOG.info("CarAd with idL " + entityid + " succesffully created.");
                    return entity;
                } catch (SQLException e) {
                    LOG.error("SQLException", e);
                    return null;
                }
            }

        } catch (SQLException e) {
            LOG.error("SQLException", e);
            return null;

        }

        return entity;
    }

    @Override
    public Optional<CarAd> findById(Long id) {
        String query = "SELECT * FROM CarAds WHERE carAdID = ?";
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {
            stmt.setLong(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                CarAd carad = new CarAd();
                carad.setId(result.getLong("carAdID"));
                carad.setModel(result.getString("model"));
                carad.setManufacturer(result.getString("manufacturer"));
                carad.setPrice(result.getInt("price"));
                carad.setQuality(result.getString("quality"));
                carad.setYearOfMake(result.getInt("yearOfMake"));
                Optional<CarOwner> optional = ownerDao.findById(result.getLong("ownerID"));
                if (optional.isEmpty()) {
                    LOG.info("baj van optional empty");
                    return Optional.empty();//null volt
                }
                CarOwner carOwner = optional.get();
                carad.setCarOwner(carOwner);
                LOG.info(carad.getModel());
                return Optional.of(carad);

            }
            LOG.info("baj van optional empty de set nem");
            return Optional.empty();
        } catch (SQLException e) {
            LOG.error("SQLException", e);
            return Optional.empty();
        }

    }

    @Override
    public Collection<CarAd> findAll() {
        String query = "SELECT * FROM CarAds";
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();

            Collection<CarAd> carAds = new LinkedList<>();

            while (result.next()) {
                CarAd carAd = new CarAd();
                carAd.setId(result.getLong("carAdID"));
                carAd.setModel(result.getString("model"));
                carAd.setManufacturer(result.getString("manufacturer"));
                carAd.setPrice(result.getInt("price"));
                carAd.setQuality(result.getString("quality"));
                carAd.setYearOfMake(result.getInt("yearOfMake"));
                Optional<CarOwner> optional = ownerDao
                        .findById(Long.parseLong(String.valueOf(result.getInt("ownerID"))));
                if (optional.isEmpty()) {
                    LOG.info("baj van optional empty");
                    return null;
                }

                CarOwner carOwner = optional.get();
                LOG.info("{}", carOwner);
                carAd.setCarOwner(carOwner);
                LOG.info("{}", carAd.getCarOwner());
                carAds.add(carAd);

            }
            LOG.info("{}", carAds);
            return carAds;
        } catch (SQLException e) {
            LOG.error("SQLException", e);
            return null;
        }

    }

    @Override
    public void delete(CarAd entity) {
        Long id = entity.getId();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("delete from CarAds where carAdID = ?");
            prep.setLong(1, id);
            prep.executeUpdate();
            LOG.info("CarAd with id: " + id + " successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("delete from CarAds where carAdID = ?");
            prep.setLong(1, id);
            prep.executeUpdate();
            LOG.info("CarAd with id: " + id + " successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
    }

    @Override
    public Collection<CarAd> findByYearOfMake(Integer yearOfMake) {
        Collection<CarAd> carAds = new LinkedList<>();
        String query = "SELECT * FROM CarAds WHERE yearOfMake = ?";
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {
            stmt.setInt(1, yearOfMake);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                CarAd carad = new CarAd();
                carad.setId(result.getLong("carAdID"));
                carad.setModel(result.getString("model"));
                carad.setManufacturer(result.getString("manufacturer"));
                carad.setPrice(result.getInt("price"));
                carad.setQuality(result.getString("quality"));
                carad.setYearOfMake(result.getInt("yearOfMake"));
                Optional<CarOwner> optional = ownerDao.findById(result.getLong("ownerID"));
                if (optional.isEmpty()) {
                    LOG.info("baj van optional empty");
                    return null;
                }
                CarOwner carOwner = optional.get();
                carad.setCarOwner(carOwner);
                carAds.add(carad);
            }

            return carAds;
        } catch (SQLException e) {
            LOG.error("SQLException", e);
            return null;
        }
    }

}