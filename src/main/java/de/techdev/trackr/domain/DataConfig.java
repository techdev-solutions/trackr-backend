package de.techdev.trackr.domain;

import javax.sql.DataSource;

/**
 * @author Alexander Hanschke
 */
public interface DataConfig {
    DataSource dataSource();
}
