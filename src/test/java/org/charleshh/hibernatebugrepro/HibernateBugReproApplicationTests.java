package org.charleshh.hibernatebugrepro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class HibernateBugReproApplicationTests {
	@Autowired
	SimpleService simpleService;

	@Test
	void shouldUpdateAfterDelete() throws Exception {
		SimpleEntity initialEntity = new SimpleEntity("TEST", 1, "foo");
		simpleService.createSimpleEntity(initialEntity);
		SimpleEntity newEntity = new SimpleEntity("TEST", 1, "bar");
		simpleService.updateByDeleteSimpleEntity(List.of(newEntity));
		List<SimpleEntity> result = simpleService.getSimpleEntities(List.of("test", "TEST"));
		Assertions.assertNotNull(result.getFirst());
		Assertions.assertEquals(result.getFirst().getData(), "bar");
	}

	@Test
	void shouldUpdate() throws Exception {
		SimpleEntity initialEntity = new SimpleEntity("TEST", 1, "foo");
		simpleService.createSimpleEntity(initialEntity);
		SimpleEntity newEntity = new SimpleEntity("TEST", 1, "bar");
		simpleService.updateSimpleEntity(List.of(newEntity));
		List<SimpleEntity> result = simpleService.getSimpleEntities(List.of("test", "TEST"));
		Assertions.assertNotNull(result.getFirst());
		Assertions.assertEquals(result.getFirst().getData(), "bar");
	}
	@Test
	void shouldUpdateAfterDeleteButDoesNot() throws Exception {
		SimpleEntity initialEntity = new SimpleEntity("TEST", 1, "foo");
		simpleService.createSimpleEntity(initialEntity);
		// Changing id to be identical, beside casing (assuming the database is case insensitive too)
		SimpleEntity newEntity = new SimpleEntity("test", 1, "bar");
		simpleService.updateByDeleteSimpleEntity(List.of(newEntity));
		List<SimpleEntity> result = simpleService.getSimpleEntities(List.of("test", "TEST"));
		// This shouldn't be null, but it is. The previous row does exist, but is deleted by the service but not saved after
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	@Disabled("Not currently reproducing")
	void shouldUpdateButThrows() {
		SimpleEntity initialEntity = new SimpleEntity("TEST", 1, "foo");
		simpleService.createSimpleEntity(initialEntity);
		// Changing id to be identical, beside casing (assuming the database is case insensitive too)
		SimpleEntity newEntity = new SimpleEntity("test", 1, "bar");

		// This throws "identifier of an instance of was altered from SimpleEntityPK(id="TEST" ...) to SimpleEntityPK=(id="test" ...)
		Assertions.assertThrows(Exception.class, () -> simpleService.updateSimpleEntity(List.of(newEntity)));
	}
}
