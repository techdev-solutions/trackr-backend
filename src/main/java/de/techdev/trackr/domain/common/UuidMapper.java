package de.techdev.trackr.domain.common;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Moritz Schulze
 */
public class UuidMapper {

    public static final Pattern UUID_PATTERN = Pattern.compile("([a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12})");

    @Autowired
    private DataSource dataSource;

    public String extractUUUIDfromString(String input) {
        Matcher matcher = UuidMapper.UUID_PATTERN.matcher(input);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public Long getIdFromUUID(String uuid) {
        Long id = null;
        try {
            PreparedStatement getIdStatement = dataSource.getConnection().prepareStatement("SELECT id FROM uuid_mapping WHERE uuid = ?");
            getIdStatement.setString(1, uuid);
            ResultSet resultSet = getIdStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            resultSet.close();
            getIdStatement.close();
        } catch (SQLException e) {
            // just return null
        }
        return id;
    }

    public void deleteUUID(String uuid) {
        try {
            PreparedStatement deleteStatement = dataSource.getConnection().prepareStatement("DELETE FROM uuid_mapping WHERE uuid = ?");
            deleteStatement.setString(1, uuid);
            deleteStatement.execute();
            deleteStatement.close();
        } catch (SQLException e) {
            // nothing to do
        }
    }

    public void deleteUUID(Long id) {
        try {
            PreparedStatement deleteStatement = dataSource.getConnection().prepareStatement("DELETE FROM uuid_mapping WHERE id = ?");
            deleteStatement.setLong(1, id);
            deleteStatement.execute();
            deleteStatement.close();
        } catch (SQLException e) {
            // nothing to do
        }
    }

    public UUID createUUID(Long id) {
        UUID uuid = UUID.randomUUID();
        try {
            PreparedStatement insertStatement = dataSource.getConnection().prepareStatement("INSERT INTO uuid_mapping (id, uuid) VALUES (?, ?)");
            insertStatement.setLong(1, id);
            insertStatement.setString(2, uuid.toString());
            insertStatement.execute();
        } catch (SQLException e) {
            return null;
        }
        return uuid;
    }
}
