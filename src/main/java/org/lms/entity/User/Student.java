package org.lms.entity.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends AppUser {
}
