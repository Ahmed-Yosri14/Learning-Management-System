package org.lms.entity.Assessment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.Question;
import org.lms.entity.UserRole;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
public class Assignment extends Assessment {
    public Map<String,Object> toMap(UserRole role){
        Map<String, Object> response = new HashMap<>();
        response.put("question title", this.getTitle());
        response.put("duration", this.getDuration());
        response.put("start Date", this.getStartDate());
        return response;
    }
}