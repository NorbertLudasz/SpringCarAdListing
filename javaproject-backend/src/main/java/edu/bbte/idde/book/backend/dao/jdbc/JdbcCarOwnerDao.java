package edu.bbte.idde.book.backend.dao.jdbc;

import edu.bbte.idde.book.backend.dao.CarOwnerDao;
import edu.bbte.idde.book.backend.model.CarOwner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcCarOwnerDao implements CarOwnerDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcCarAdDao.class);
    private final DataSource dataSource;

    public JdbcCarOwnerDao() {
        dataSource = DataSourceFactory.getDataSource();
    }

    @Override
    public void create(CarOwner carowner) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("insert into CarOwners values(default, ?, ?, ?, ?)");
            prep.setString(1, carowner.getName());
            prep.setString(2, carowner.getNationality());
            prep.setInt(3, carowner.getPhoneNumber());
            prep.setString(4, carowner.getPersonalEmail());
            prep.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
    }

    @Override
    public CarOwner findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from CarOwners where ownerID = ?");
            prep.setLong(1, id);
            ResultSet set = prep.executeQuery();
            if (set.next()) {
                return new CarOwner(set.getLong("id"),
                        set.getString("name"),
                        set.getString("nationality"),
                        set.getInt("phoneNumber"),
                        set.getString("personalEmail"));
            }
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        return null;
    }

    @Override
    public Collection<CarOwner> findAll() {
        Collection<CarOwner> carAds = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from CarOwners");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                carAds.add(new CarOwner(set.getLong("id"),
                        set.getString("name"),
                        set.getString("nationality"),
                        set.getInt("phoneNumber"),
                        set.getString("personalEmail")));
            }
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        return carAds;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("delete from CarOwners where ownerID = ?");
            prep.setLong(1, id);
            prep.executeUpdate();
            LOG.info("CarOwner with id: " + id + " successfully deleted.");
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
    }

    @Override
    public void update(Long id, CarOwner carowner) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("update CarOwners "
                            + "set ownerName = ?, ownerNationality = ?, ownerPhoneNumber = ?, ownerPersonalEmail = ? "
                            + "where ownerID = ?");
            prep.setString(1, carowner.getName());
            prep.setString(2, carowner.getNationality());
            prep.setInt(3, carowner.getPhoneNumber());
            prep.setString(4, carowner.getPersonalEmail());
            prep.setLong(7, carowner.getId());
            prep.executeUpdate();
            LOG.info("CarOwner with id: " + id + " successfully updated.");
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
    }

    @Override
    public Collection<CarOwner> findByName(String name) {
        Collection<CarOwner> carAds = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement prep = connection
                    .prepareStatement("select * from CarOwners where ownerID = ?");
            prep.setString(1, name);
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                carAds.add(new CarOwner(set.getLong("id"),
                        set.getString("name"),
                        set.getString("nationality"),
                        set.getInt("phoneNumber"),
                        set.getString("personalEmail")));
            }
        } catch (SQLException e) {
            LOG.error("Hiba: {}", e.toString());
        }
        return carAds;
    }

}
