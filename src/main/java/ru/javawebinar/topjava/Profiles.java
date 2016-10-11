package ru.javawebinar.topjava;

import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepositoryImpl;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class Profiles {
    public static final String
            POSTGRES = "postgres",
            HSQLDB = "hsqldb",
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String ACTIVE_DB = POSTGRES;
}
