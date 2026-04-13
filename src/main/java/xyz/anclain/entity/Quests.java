package xyz.anclain.entity;

import jakarta.persistence.*;
import lombok.Data;
import xyz.anclain.utils.ListConverter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "quests")
public class Quests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String author;

    @Column(name = "is_shown", nullable = false)
    private Boolean isShown = true; // 默认值为 true

    @Convert(converter = ListConverter.class)
    @Column(columnDefinition = "json")
    private List<Double> priority;

    @Column(name = "create_time")
    private LocalDateTime createTime;



}
