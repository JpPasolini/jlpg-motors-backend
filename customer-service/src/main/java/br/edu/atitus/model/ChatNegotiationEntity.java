package br.edu.atitus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_negotiations")
public class ChatNegotiationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // ID da solicitação / ID do Chat

    @Column(name = "user_id", nullable = false)
    private UUID userId; // ID do Usuário que iniciou a negociação

    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId; // ID do Carro negociado

    @Column(name = "status", nullable = false)
    private String status; // Começa como "OPEN" (Aberto) e muda para "CLOSED" (Encerrado)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public ChatNegotiationEntity() {}

    public ChatNegotiationEntity(UUID userId, UUID vehicleId) {
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.status = "OPEN";
        this.createdAt = LocalDateTime.now();
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public UUID getVehicleId() { return vehicleId; }
    public void setVehicleId(UUID vehicleId) { this.vehicleId = vehicleId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}