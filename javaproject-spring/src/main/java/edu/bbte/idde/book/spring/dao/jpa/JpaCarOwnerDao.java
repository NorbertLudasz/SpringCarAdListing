package edu.bbte.idde.book.spring.dao.jpa;

import edu.bbte.idde.book.spring.dao.CarOwnerDao;
import edu.bbte.idde.book.spring.model.CarOwner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface JpaCarOwnerDao extends CarOwnerDao, JpaRepository<CarOwner, Long> {
}
