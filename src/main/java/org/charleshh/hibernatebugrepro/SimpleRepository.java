package org.charleshh.hibernatebugrepro;

import org.springframework.data.repository.CrudRepository;


public interface SimpleRepository extends CrudRepository<SimpleEntity, String> {
}
