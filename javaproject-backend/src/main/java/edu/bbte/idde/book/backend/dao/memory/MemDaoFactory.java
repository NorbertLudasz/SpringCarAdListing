package edu.bbte.idde.book.backend.dao.memory;

import edu.bbte.idde.book.backend.dao.CarAdDao;
import edu.bbte.idde.book.backend.dao.CarOwnerDao;
import edu.bbte.idde.book.backend.dao.IDaoFactory;

public class MemDaoFactory implements IDaoFactory {
    private static CarAdDao dao;
    private static CarOwnerDao ownerdao;

    @Override
    public synchronized CarAdDao getCarAdDao() {
        if (dao == null) {
            dao = new MemCarAdDao();
        }
        return dao;
    }

    @Override
    public synchronized CarOwnerDao getCarOwnerDao() {
        if (ownerdao == null) {
            ownerdao = new MemCarOwnerDao();
        }
        return ownerdao;
    }
}
