package de.techdev.trackr.domain.common;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UuidMapperTest {

    private UuidMapper uuidMapper;

    @Before
    public void setUp() throws Exception {
        uuidMapper = new UuidMapper();
    }

    @Test
    public void extractUuid() throws Exception {
        String uuid = uuidMapper.extractUUIDfromString("7f08028d-700e-47b4-bec0-b82192d0f1c0");
        assertThat(uuid, is("7f08028d-700e-47b4-bec0-b82192d0f1c0"));
    }

    @Test
    public void extractUuidWithPrefix() throws Exception {
        String uuid = uuidMapper.extractUUIDfromString("TEST 7f08028d-700e-47b4-bec0-b82192d0f1c0");
        assertThat(uuid, is("7f08028d-700e-47b4-bec0-b82192d0f1c0"));
    }

    @Test
    public void extractUuidWithPrefixAndSuffix() throws Exception {
        String uuid = uuidMapper.extractUUIDfromString("TEST 7f08028d-700e-47b4-bec0-b82192d0f1c0 TEST2");
        assertThat(uuid, is("7f08028d-700e-47b4-bec0-b82192d0f1c0"));
    }
}