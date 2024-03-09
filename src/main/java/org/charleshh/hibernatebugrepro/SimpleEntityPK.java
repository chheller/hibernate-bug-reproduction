package org.charleshh.hibernatebugrepro;

import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleEntityPK implements Serializable  {
    @Id
    private String id;
    @Id
    private Integer otherId;
}
