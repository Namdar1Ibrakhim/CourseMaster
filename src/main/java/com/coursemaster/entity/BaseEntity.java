package com.coursemaster.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    private Boolean isActive;
}
