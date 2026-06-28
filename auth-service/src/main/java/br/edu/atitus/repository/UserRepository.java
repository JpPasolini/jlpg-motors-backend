package br.edu.atitus.repository;

import br.edu.atitus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Método customizado para buscar um usuário pelo username na autenticação
    Optional<User> findByUsername(String username);
}