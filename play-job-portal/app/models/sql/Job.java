package models.sql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Getter
@Setter
public class Job {
    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "details", nullable = false, columnDefinition = "TEXT")
    private String details;

    @Column(name = "salary", nullable = false, columnDefinition = "DEFAULT 0")
    private Integer salary;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "status", nullable = false, columnDefinition = "DEFAULT 0")
    private Integer status;

    @Column(name = "user_id")
    private Long userId;

    @PrePersist
    private void setDefaultValue() {
        status = 0;

        if (salary == null) salary = 0;
    }

}
