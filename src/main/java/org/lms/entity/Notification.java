package org.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.lms.entity.User.AppUser;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRead = false;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AppUser user;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("title", getTitle());
        map.put("content", getContent());
        map.put("isRead", getIsRead());
        map.put("userId", getUser().getId());
        return map;
    }
}
