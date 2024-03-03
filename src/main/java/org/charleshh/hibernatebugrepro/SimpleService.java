package org.charleshh.hibernatebugrepro;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleService {
    private final SimpleRepository simpleRepository;

    Optional<SimpleEntity> getSimpleEntity(String id) {
        return simpleRepository.findById(id);
    }
    @Transactional
    void createSimpleEntity(SimpleEntity entity) {
        simpleRepository.save(entity);
    }
    @Transactional
    void updateByDeleteSimpleEntity(SimpleEntity entity) throws Exception {
        Optional<SimpleEntity> foundEntity = simpleRepository.findById(entity.getId());
        if (foundEntity.isPresent()) {
            simpleRepository.delete(foundEntity.get());
            simpleRepository.save(entity);
        } else {
            throw new Exception("Entity not found");
        }
    }

    @Transactional
    void updateSimpleEntity(SimpleEntity entity) throws Exception {
        Optional<SimpleEntity> foundEntity = simpleRepository.findById(entity.getId());
        if (foundEntity.isPresent()) {
            simpleRepository.save(entity);
        } else {
            throw new Exception("Entity not found");
        }
    }
}
