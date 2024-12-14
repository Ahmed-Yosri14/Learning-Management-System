package org.lms.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends AppUser {

}
