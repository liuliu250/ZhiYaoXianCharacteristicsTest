package xyz.anclain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "quests")
public class Quests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String descriptions;
    private String author;

    @Column(name = "create_time")
    private LocalDateTime createTime;

}
