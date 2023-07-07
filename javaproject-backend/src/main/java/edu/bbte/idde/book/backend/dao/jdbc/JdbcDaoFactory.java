package edu.bbte.idde.book.backend.dao.jdbc;

import edu.bbte.idde.book.backend.dao.CarAdDao;
import edu.bbte.idde.book.backend.dao.CarOwnerDao;
import edu.bbte.idde.book.backend.dao.IDaoFactory;

public class JdbcDaoFactory implements IDaoFactory {
    private static JdbcCarAdDao dao;
    private static JdbcCarOwnerDao ownerdao;

    @Override
    public synchronized CarAdDao getCarAdDao() {
        if (dao == null) {
            dao = new JdbcCarAdDao();
        }
        return dao;
    }

    @Override
    public synchronized CarOwnerDao getCarOwnerDao() {
        if (ownerdao == null) {
            ownerdao = new JdbcCarOwnerDao();
        }
        return ownerdao;
    }
}
