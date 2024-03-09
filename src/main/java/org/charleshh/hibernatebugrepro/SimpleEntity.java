package org.charleshh.hibernatebugrepro;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(SimpleEntity.class)
public class SimpleEntity {
    @Id
    private String id;
    @Id
    private Integer otherId;
    private String data;
}
