package edu.bbte.idde.book.spring.dao;

import edu.bbte.idde.book.spring.model.CarAd;

import java.util.Collection;

public interface CarAdDao extends Dao<CarAd> {
    Collection<CarAd> findByYearOfMake(Integer yearOfMake);

    //@Transactional
    //void deleteCarAd(Long id);
}

