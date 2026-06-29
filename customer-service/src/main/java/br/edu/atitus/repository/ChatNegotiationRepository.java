package br.edu.atitus.repository;

import br.edu.atitus.model.ChatNegotiationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatNegotiationRepository extends JpaRepository<ChatNegotiationEntity, UUID> {

    // Lista todas as solicitações de um usuário específico (histórico dele)
    List<ChatNegotiationEntity> findByUserId(UUID userId);

    // Busca se já existe uma negociação ATIVA (OPEN) daquele usuário para aquele carro
    Optional<ChatNegotiationEntity> findByUserIdAndVehicleIdAndStatus(UUID userId, UUID vehicleId, String status);
}