package org.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class AnsweredQuestion {
    @Id
    long id ;
}
