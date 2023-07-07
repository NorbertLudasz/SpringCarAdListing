package edu.bbte.idde.book.backend.dao;

import edu.bbte.idde.book.backend.config.Config;
import edu.bbte.idde.book.backend.config.ConfigFactory;
import edu.bbte.idde.book.backend.dao.jdbc.JdbcDaoFactory;
import edu.bbte.idde.book.backend.dao.memory.MemDaoFactory;

public abstract class DaoFactory {
    private static IDaoFactory instance;

    public static synchronized IDaoFactory getInstance() {
        if (instance == null) {
            Config config = ConfigFactory.getConfig();

            if ("jdbc".equals(config.getDaoType())) {
                instance = new JdbcDaoFactory();
            } else {
                instance = new MemDaoFactory();
            }
        }
        return instance;
    }
}

