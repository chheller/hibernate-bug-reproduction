package org.charleshh.hibernatebugrepro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class HibernateBugReproApplicationTests {
	@Autowired
	SimpleService simpleService;

	@Test
	void shouldUpdateAfterDelete() throws Exception {
		SimpleEntity initialEntity = new SimpleEntity("TEST", "foo");
		simpleService.createSimpleEntity(initialEntity);
		SimpleEntity newEntity = new SimpleEntity("TEST", "bar");
		simpleService.updateByDeleteSimpleEntity(newEntity);
		Optional<SimpleEntity> result = simpleService.getSimpleEntity("TEST");
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(result.get().getData(), "bar");
	}

	@Test
	void shouldUpdate() throws Exception {
		SimpleEntity initialEntity = new SimpleEntity("TEST", "foo");
		simpleService.createSimpleEntity(initialEntity);
		SimpleEntity newEntity = new SimpleEntity("TEST", "bar");
		simpleService.updateSimpleEntity(newEntity);
		Optional<SimpleEntity> result = simpleService.getSimpleEntity("TEST");
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(result.get().getData(), "bar");
	}
	@Test
	void shouldUpdateAfterDeleteButDoesNot() throws Exception {
		SimpleEntity initialEntity = new SimpleEntity("TEST", "foo");
		simpleService.createSimpleEntity(initialEntity);
		// Changing id to be identical, beside casing (assuming the database is case insensitive too)
		SimpleEntity newEntity = new SimpleEntity("test", "bar");
		simpleService.updateByDeleteSimpleEntity(newEntity);
		Optional<SimpleEntity> result = simpleService.getSimpleEntity("TEST");
		// This shouldn't be null, but it is. The previous row does exist, but is deleted by the service but not saved after
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	void shouldUpdateButFails() throws Exception {
		SimpleEntity initialEntity = new SimpleEntity("TEST", "foo");
		simpleService.createSimpleEntity(initialEntity);
		SimpleEntity newEntity = new SimpleEntity("test", "bar");

		// This should not throw, but it does because the identifier changed
		Assertions.assertThrows(Exception.class, () -> simpleService.updateSimpleEntity(newEntity));
	}

}
