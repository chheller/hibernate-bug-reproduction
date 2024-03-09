package org.charleshh.hibernatebugrepro;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SimpleRepository extends CrudRepository<SimpleEntity, String> {
    @Query("SELECT se FROM SimpleEntity se WHERE id in :ids")
    List<SimpleEntity> findAllEntitiesById(List<String> ids);
}
