package org.lms;

import org.lms.entity.MappableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    @Autowired
    AuthorizationManager authorizationManager;

    public Map<String, Object> map(MappableEntity entity) {
        return entity.toMap(authorizationManager.getCurrentUser().getRoles());
    }
    public List<Map<String, Object>> map(List<MappableEntity> entities) {
        return entities.stream().map(this::map).collect(Collectors.toList());
    }
}