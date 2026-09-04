package com.c2.lc.lib.db;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdBy", "createdAt", "lastUpdatedBy", "lastUpdatedAt"},
        allowGetters = true,
        allowSetters = true
)
public abstract  class BaseEntity {

    @Column(name = "c_created_by")
    private Long createdBy;

    @Column(name = "t_created_at")
    private LocalDateTime createdAt;

    @Column(name = "c_last_updated_by")
    private Long lastUpdatedBy;

    @Column(name = "t_last_updated_at")
    private LocalDateTime lastUpdatedAt;
}
