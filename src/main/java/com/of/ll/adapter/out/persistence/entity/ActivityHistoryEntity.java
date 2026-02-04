package com.of.ll.adapter.out.persistence.entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "activity_history")
public class ActivityHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "client_id", nullable = false)
    String clientId;

    @Column(name = "generated_at", nullable = false)
    Instant generatedAt;

    @ElementCollection
    @CollectionTable(name = "activity_history_titles", joinColumns = @JoinColumn(name = "activity_history_id"))
    @Column(name = "activity_titles", nullable = false)
    List<String> activityTitles;

    @Column(name = "fallback_used", nullable = false)
    boolean fallbackUsed;

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public void setGeneratedAt(final Instant generatedAt) {
        this.generatedAt = generatedAt;
    }

    public void setActivityTitles(final List<String> activityTitles) {
        this.activityTitles = activityTitles;
    }

    public void setFallbackUsed(final boolean fallbackUsed) {
        this.fallbackUsed = fallbackUsed;
    }

    public Long getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public List<String> getActivityTitles() {
        return activityTitles;
    }

    public boolean isFallbackUsed() {
        return fallbackUsed;
    }
}
