package org.lms.entity;

import java.util.Map;

public interface MappableEntity {
    Map<String, Object> toMap(UserRole role);
}