package edu.bbte.idde.book.backend.dao;

public interface IDaoFactory {
    CarAdDao getCarAdDao();

    CarOwnerDao getCarOwnerDao();
}

