package com.dt.ducthuygreen.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import javax.persistence.*;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "is_deleted")
    protected Boolean deleted;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    protected Date createdDate;

    @Column(name = "created_by", updatable = false)
    protected String createdBy;

    @Column(name = "updated_date")
    @UpdateTimestamp
    protected Date updatedDate;

    @Column(name = "updated_by")
    protected String updatedBy;

    @PrePersist
    protected void beforePersist() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                createdBy = ((UserDetails)principal).getUsername();
            } else {
                createdBy = principal.toString();
            }

        } catch (Exception e) {
            createdBy = null;
        }

        deleted = false;
    }

    @PreUpdate
    protected void beforeUpdate() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                updatedBy = ((UserDetails)principal).getUsername();
            } else {
                updatedBy = principal.toString();
            }

        } catch (Exception e) {
            updatedBy = null;
        }
    }
}
