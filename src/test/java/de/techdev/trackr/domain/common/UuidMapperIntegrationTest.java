package de.techdev.trackr.domain.common;

import de.techdev.test.TestConstants;
import de.techdev.test.TransactionalIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.echocat.jomon.testing.BaseMatchers.isNotNull;
import static org.echocat.jomon.testing.BaseMatchers.isNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Sql(TestConstants.CREATE_UUID_MAPPING_TABLE_SQL_FILE)
public class UuidMapperIntegrationTest extends TransactionalIntegrationTest {

    @Autowired
    private UuidMapper uuidMapper;

    @Test
    public void insertUuid() throws Exception {
        UUID uuid = uuidMapper.createUUID(1L);
        assertThat(uuid, isNotNull());
    }

    @Test
    public void findUuid() throws Exception {
        UUID uuid = uuidMapper.createUUID(1L);
        Long id = uuidMapper.getIdFromUUID(uuid.toString());
        assertThat(id, is(1L));
    }

    @Test
    public void deleteUuidByUuid() throws Exception {
        UUID uuid = uuidMapper.createUUID(1L);
        uuidMapper.deleteUUID(uuid.toString());
        Long id = uuidMapper.getIdFromUUID(uuid.toString());
        assertThat(id, isNull());
    }

    @Test
    public void deleteById() throws Exception {
        UUID uuid = uuidMapper.createUUID(1L);
        uuidMapper.deleteUUID(1L);
        Long id = uuidMapper.getIdFromUUID(uuid.toString());
        assertThat(id, isNull());
    }
}