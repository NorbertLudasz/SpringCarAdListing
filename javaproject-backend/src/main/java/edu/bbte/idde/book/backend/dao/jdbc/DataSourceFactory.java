package edu.bbte.idde.book.backend.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.book.backend.config.Config;
import edu.bbte.idde.book.backend.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DataSourceFactory {
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceFactory.class);
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                //e.printStackTrace();
                LOG.info("driver baj van");
            }
            Config config = ConfigFactory.getConfig();
            HikariConfig hikariConfig = new HikariConfig();

            hikariConfig.setJdbcUrl("jdbc:mysql://" + config.getJdbcUrl()
                    + "/" + config.getJdbcDatabase() + "?useSSL=false");
            hikariConfig.setUsername(config.getJdbcUser());
            hikariConfig.setPassword(config.getJdbcPassword());

            hikariConfig.setMaximumPoolSize(config.getJdbcPoolSize());

            dataSource = new HikariDataSource(hikariConfig);
        }
        return dataSource;
    }

}
