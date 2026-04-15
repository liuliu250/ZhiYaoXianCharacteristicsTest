package xyz.anclain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "test_results")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double dim1;
    private Double dim2;
    private Double dim3;
    private Double dim4;
    private Double dim5;

    private String matchedProfile;
    private Double matchedScore;

    private LocalDateTime createdAt = LocalDateTime.now();

    public void setVector(double[] vector) {
        this.dim1 = vector[0];
        this.dim2 = vector[1];
        this.dim3 = vector[2];
        this.dim4 = vector[3];
        this.dim5 = vector[4];
    }

}
