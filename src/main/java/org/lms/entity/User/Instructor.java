package org.lms.entity.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends AppUser {

}