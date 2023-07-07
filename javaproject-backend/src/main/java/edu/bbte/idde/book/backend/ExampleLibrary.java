package edu.bbte.idde.book.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleLibrary {

    public static final Logger LOG = LoggerFactory.getLogger(ExampleLibrary.class);

    public String getName() {
        LOG.info("mukodik a log");
        return "Szeminarium 532";
    }
}
