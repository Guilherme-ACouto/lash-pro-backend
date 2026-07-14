package com.lashmanager.clients;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = ClientsTestApplication.class)
@Transactional
@Rollback
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {
}
