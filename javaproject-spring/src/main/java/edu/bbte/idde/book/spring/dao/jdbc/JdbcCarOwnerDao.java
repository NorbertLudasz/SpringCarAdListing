package edu.bbte.idde.book.spring.dao.jdbc;

import edu.bbte.idde.book.spring.dao.CarOwnerDao;
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
public class JdbcCarOwnerDao implements CarOwnerDao {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcCarAdDao.class);
    @Autowired
    private DataSource dataSource;

    /*@PostConstruct
    private void init() {
        LOG.info("{} Initialized.", JdbcCarAdDao.class);
    }*/

    @Override
    public CarOwner saveAndFlush(CarOwner entity) {
        Long entityid = entity.getId();
        Optional<CarOwner> optional = findById(entityid);
        if (optional.isPresent()) {
            CarOwner carOwner = optional.get();
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement prep = connection
                        .prepareStatement("UPDATE CarOwners "
                                + "set ownerName = ?, ownerNationality = ?, ownerPhoneNumber = ?, "
                                + "ownerPersonalEmail = ?"
                                + "where ownerID = ?");
                prep.setString(1, entity.getOwnerName());
                prep.setString(2, entity.getOwnerNationality());
                prep.setInt(3, entity.getOwnerPhoneNumber());
                prep.setString(4, entity.getOwnerPersonalEmail());
                prep.setLong(5, entityid);
                prep.executeUpdate();
                LOG.info("CarOwner with id: " + entityid + " successfully updated.");
                return carOwner;
            } catch (SQLException e) {
                LOG.error("Hiba: {}", e.toString());
                return null;
            }
        }
        String query = "INSERT INTO CarOwners values (default, ?, ?, ?, ?)";
        try (PreparedStatement stmt = dataSource.getConnection()
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getOwnerName());
            stmt.setString(2, entity.getOwnerNationality());
            stmt.setInt(3, entity.getOwnerPhoneNumber());
            stmt.setString(4, entity.getOwnerPersonalEmail());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                entity.setId(keys.getLong(1));
            }
            return entity;
        } catch (SQLException e) {
            LOG.error("SQLException", e);
            return null;
        }
    }

    @Override
    public Optional<CarOwner> findById(Long id) {
        String query = "SELECT * FROM CarOwners WHERE ownerID = ?";
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {
            stmt.setLong(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                CarOwner carowner = new CarOwner();
                carowner.setId(result.getLong("ownerID"));
                carowner.setOwnerName(result.getString("ownerName"));
                carowner.setOwnerNationality(result.getString("ownerNationality"));
                carowner.setOwnerPhoneNumber(result.getInt("ownerPhoneNumber"));
                carowner.setOwnerPersonalEmail(result.getString("ownerPersonalEmail"));

                return Optional.of(carowner);
            }

            return Optional.empty();
        } catch (SQLException e) {
            LOG.error("SQLException", e);
            return Optional.empty();
        }

    }

    @Override
    public Collection<CarOwner> findAll() {
        String query = "SELECT * FROM CarOwners";
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {
            ResultSet result = stmt.executeQuery();

            Collection<CarOwner> carOwners = new LinkedList<>();

            while (result.next()) {
                CarOwner carOwner = new CarOwner();
                carOwner.setId(result.getLong("ownerID"));
                carOwner.setOwnerName(result.getString("ownerName"));
                carOwner.setOwnerNationality(result.getString("ownerNationality"));
                carOwner.setOwnerPhoneNumber(result.getInt("ownerPhoneNumber"));
                carOwner.setOwnerPersonalEmail(result.getString("ownerPersonalEmail"));
                carOwners.add(carOwner);
            }

            return carOwners;
        } catch (SQLException e) {
            LOG.error("SQLException", e);
            return null;
        }

    }

    @Override
    public void deleteById(Long id) {
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

    @Override //Long id volt parameter masiknal
    public void delete(CarOwner entity) {
        Long id = entity.getId();
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

}