package edu.bbte.idde.book.spring.dao.jpa;

import edu.bbte.idde.book.spring.dao.CarAdDao;
import edu.bbte.idde.book.spring.model.CarAd;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@Profile("jpa")
public interface JpaCarAdDao extends CarAdDao, JpaRepository<CarAd, Long> {

    @Modifying
    @Override
    @Query("select b from CarAd b where b.yearOfMake = ?1")
    Collection<CarAd> findByYearOfMake(Integer yearOfMake);

    @Modifying
    @Query("select b from CarAd b where b.carOwner.ownerName = ?1")
    Collection<CarAd> findByOwner(String owner);

}

