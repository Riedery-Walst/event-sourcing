package ru.andreev.ufanet.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;
    private String eventType;
    private UUID entityId;
    private String eventData;
    @SerializedName("registeredDateTime")
    private LocalDateTime dateTime;

    @PrePersist
    public void prePersist() {
        this.dateTime = LocalDateTime.now();
    }

}
