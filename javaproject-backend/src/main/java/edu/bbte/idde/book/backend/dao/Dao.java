package edu.bbte.idde.book.backend.dao;

import edu.bbte.idde.book.backend.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    void create(T entity);

    T findById(Long id);

    Collection<T> findAll();

    void delete(Long id);

    void update(Long id, T entity);
}
