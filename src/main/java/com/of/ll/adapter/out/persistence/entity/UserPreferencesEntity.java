package com.of.ll.adapter.out.persistence.entity;

import java.time.Instant;

import com.of.ll.domain.model.PreferredStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_preferences")
public class UserPreferencesEntity {

    @Id
    @Column(name = "client_id", nullable = false)
    String clientId;

    @Enumerated
    @Column(name = "preferred_style", nullable = false)
    PreferredStyle preferredStyle;

    @Column(name = "updated_at", nullable = false)
    Instant updatedAt;

    public String clientId() {
        return clientId;
    }

    public PreferredStyle preferredStyle() {
        return preferredStyle;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public void setPreferredStyle(final PreferredStyle preferredStyle) {
        this.preferredStyle = preferredStyle;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
