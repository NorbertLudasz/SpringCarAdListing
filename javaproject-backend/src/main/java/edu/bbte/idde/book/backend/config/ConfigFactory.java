package edu.bbte.idde.book.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigFactory.class);
    private static Config instance;

    public static synchronized Config getConfig() {
        if (instance == null) {
            //InputStream inputStream = Config.class.getResourceAsStream(getName());
            InputStream inputStream = Config.class.getResourceAsStream("/application-dev.yaml");
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            try {
                instance = objectMapper.readValue(inputStream, Config.class);
            } catch (IOException e) {
                LOG.error(e.toString());
                instance = new Config();
                instance.setDaoType("mem");
            }
        }
        LOG.info(instance.getDaoType());
        return instance;
    }
    //daotipus (jdbc vagy mem) allithato az applicaation-dev.yaml elso soraban
}

