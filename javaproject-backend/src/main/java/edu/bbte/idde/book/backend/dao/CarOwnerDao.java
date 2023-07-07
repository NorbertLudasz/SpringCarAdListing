package edu.bbte.idde.book.backend.dao;

import edu.bbte.idde.book.backend.model.CarOwner;

import java.util.Collection;

public interface CarOwnerDao extends Dao<CarOwner> {
    //Collection<CarAd> findByYearOfMake(Integer yearOfMake);
    Collection<CarOwner> findByName(String name);
}
