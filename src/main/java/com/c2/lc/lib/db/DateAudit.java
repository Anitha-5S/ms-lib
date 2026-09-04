package com.c2.lc.lib.db;

import com.c2.lc.lib.base.BaseSuper;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class DateAudit extends BaseSuper {

    public DateAudit(Long userId, LocalDateTime time) {
        this.nCreatedBy = userId;
        this.tCreatedAt = time;
        this.nLastUpdatedBy = userId;
        this.tLastUpdatedAt = time;
    }

    @Expose(serialize = false, deserialize = false)
    @Column(name = "n_created_by", updatable = false)
    private Long nCreatedBy;

    @Expose(serialize = false, deserialize = false)
    @Column(name = "t_created_at", updatable = false)
    private LocalDateTime tCreatedAt;

    @Expose(serialize = false, deserialize = false)
    @Column(name = "n_last_updated_by")
    private Long nLastUpdatedBy;

    @Expose(serialize = false, deserialize = false)
    @Column(name = "t_last_updated_at")
    private LocalDateTime tLastUpdatedAt;

    public void setLastUpdated(Long userId, LocalDateTime time) {
        this.nLastUpdatedBy = userId;
        this.tLastUpdatedAt = time;
    }

    public void setIdTime(Long userId, LocalDateTime time) {
        this.nCreatedBy = userId;
        this.tCreatedAt = time;
        this.nLastUpdatedBy = userId;
        this.tLastUpdatedAt = time;
    }
}
