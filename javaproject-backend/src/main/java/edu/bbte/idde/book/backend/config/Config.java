package edu.bbte.idde.book.backend.config;

import lombok.Data;

@Data
public class Config {
    private String daoType;
    private String jdbcUser;
    private String jdbcPassword;
    private String jdbcDatabase;
    private String jdbcUrl;
    private int jdbcPoolSize;
    private boolean logQueries;
    private boolean logUpdates;
}

