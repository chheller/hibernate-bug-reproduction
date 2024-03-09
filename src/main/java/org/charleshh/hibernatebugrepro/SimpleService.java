package org.charleshh.hibernatebugrepro;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleService {
    private final SimpleRepository simpleRepository;

    List<SimpleEntity> getSimpleEntities(String id) {
        return simpleRepository.findAllEntitiesById(List.of(id));
    }

    List<SimpleEntity> getSimpleEntities(List<String> ids) {
        return simpleRepository.findAllEntitiesById(ids);
    }
    @Transactional
    void createSimpleEntity(SimpleEntity entity) {
        simpleRepository.save(entity);
    }
    @Transactional
    void updateByDeleteSimpleEntity(List<SimpleEntity> entities) throws Exception {
        SimpleEntity firstEntity = entities.getFirst();
        List<SimpleEntity> foundEntities = simpleRepository.findByIdAndOtherId(firstEntity.getId(), firstEntity.getOtherId());
        if (!foundEntities.isEmpty()) {
            simpleRepository.deleteAll(foundEntities);
            simpleRepository.saveAll(entities);
        } else {
            throw new Exception("Entity not found");
        }
    }

    @Transactional
    void updateSimpleEntity(List<SimpleEntity> entities) throws Exception {
        SimpleEntity firstEntity = entities.getFirst();
        List<SimpleEntity> foundEntities = simpleRepository.findByIdAndOtherId(firstEntity.getId(), firstEntity.getOtherId());
        if (!foundEntities.isEmpty()) {
            simpleRepository.saveAll(entities);
        } else {
            throw new Exception("Entity not found");
        }
    }
}
