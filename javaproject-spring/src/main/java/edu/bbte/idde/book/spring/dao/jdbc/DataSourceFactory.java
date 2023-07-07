package edu.bbte.idde.book.spring.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("jdbc")
public class DataSourceFactory {
    @Value("${jdbc.user:root}")
    private String jdbcUser;
    @Value("${jdbc.passwd:root}")
    private String passwd;
    @Value("${jdbc.poolSize:1000}")
    private Integer connectionNumber;
    @Value("jdbc:mysql://localhost:3306/CarAdsDB?useSSL=false")
    private String fullURL;

    @Bean
    public DataSource getDataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(fullURL);
        hikariConfig.setUsername(jdbcUser);
        hikariConfig.setPassword(passwd);

        hikariConfig.setMaximumPoolSize(connectionNumber);

        return new HikariDataSource(hikariConfig);
    }

}
