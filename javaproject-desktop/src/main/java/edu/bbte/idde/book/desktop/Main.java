package edu.bbte.idde.book.desktop;

import edu.bbte.idde.book.backend.ExampleLibrary;
import edu.bbte.idde.book.backend.dao.CarAdDao;
import edu.bbte.idde.book.backend.dao.DaoFactory;
import edu.bbte.idde.book.backend.model.CarAd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ExampleLibrary exampleLibrary = new ExampleLibrary();
        LOG.debug("Debug test" + exampleLibrary.getName());

        //IDaoFactory daoFactory = IDaoFactory.getInstance();

        CarAdDao dao = DaoFactory.getInstance().getCarAdDao();
        dao.create(new CarAd(1L, "modelmain", "manumain", 1, "jo", 1999, 1L));
        //dao.create(new CarAd(234L, "modelmain2","manumain2",111,"joooo",1999,1L));
        //dao.create(new CarAd(345L, "modelmain3","manumain3",1111,"jo",2003,1L));

        //dao.create(new Horse(6l, 4, "11billioforint"));

        //LOG.info("Auto hirdetesek:");
        for (CarAd carAd : dao.findAll()) {
            LOG.info(carAd.toString());
        }

        LOG.info(dao.findById(2L).toString());

        CarAd updatedcar = new CarAd(2L, "updatedmdoel", "updatedmanu", 1234567, "legjobb", 1337, 1L);
        dao.update(2L, updatedcar);

        LOG.info(dao.findById(2L).toString());

        dao.delete(2L);
        for (CarAd carAd : dao.findAll()) {
            LOG.info(carAd.toString());
        }

        //LOG.error(dao.findById(1L).toString());
    }

}
