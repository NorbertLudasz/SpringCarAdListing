package edu.bbte.idde.book.spring.dao;

import edu.bbte.idde.book.spring.model.BaseEntity;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

public interface Dao<T extends BaseEntity> {
    T saveAndFlush(T entity);//uj

    //void create(T entity);//nincs erik szemijeben

    Optional<T> findById(Long id);

    Collection<T> findAll();

    void delete(T entity);

    @Transactional
    void deleteById(Long id);

    //void update(Long id, T entity);//nincs erik szemijeben
}
