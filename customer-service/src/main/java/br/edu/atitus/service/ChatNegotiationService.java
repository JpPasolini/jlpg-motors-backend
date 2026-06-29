package br.edu.atitus.service;

import br.edu.atitus.model.ChatNegotiationEntity;
import br.edu.atitus.repository.ChatNegotiationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatNegotiationService {

    private final ChatNegotiationRepository chatRepository;

    public ChatNegotiationService(ChatNegotiationRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    // 1. Criar solicitação de negociação
    public ChatNegotiationEntity startNegotiation(UUID userId, UUID vehicleId) {
        // Verifica se o usuário já tem uma negociação ATIVA para este carro específico
        Optional<ChatNegotiationEntity> existing = chatRepository.findByUserIdAndVehicleIdAndStatus(userId, vehicleId, "OPEN");

        if (existing.isPresent()) {
            return existing.get(); // Retorna o chat existente se já estiver aberto
        }

        // Se não tiver, cria um novo
        ChatNegotiationEntity newChat = new ChatNegotiationEntity(userId, vehicleId);
        return chatRepository.save(newChat);
    }

    // 2. Encerrar uma negociação (muda status para CLOSED)
    public ChatNegotiationEntity closeNegotiation(UUID chatId) {
        ChatNegotiationEntity chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Solicitação de negociação não encontrada!"));

        chat.setStatus("CLOSED");
        return chatRepository.save(chat);
    }

    // 3. Listar o histórico do usuário logado
    public List<ChatNegotiationEntity> getMyChats(UUID userId) {
        return chatRepository.findByUserId(userId);
    }

    // 4. Listar todas as negociações do sistema (para os ADMs verem e responderem)
    public List<ChatNegotiationEntity> getAllChatsForAdmin() {
        return chatRepository.findAll();
    }
}