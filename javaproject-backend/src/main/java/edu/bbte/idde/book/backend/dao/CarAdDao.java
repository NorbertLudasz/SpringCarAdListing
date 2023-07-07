package edu.bbte.idde.book.backend.dao;

import edu.bbte.idde.book.backend.model.CarAd;

import java.util.Collection;

public interface CarAdDao extends Dao<CarAd> {
    //void update(Long id, String model, String manufacturer, Integer price,
    // String quality, Integer yearOfMake, Long ownerID);

    Collection<CarAd> findByYearOfMake(Integer yearOfMake);

    Integer getLoggedUpdatesDb();

    Integer getLoggedQueriesDb();
}
