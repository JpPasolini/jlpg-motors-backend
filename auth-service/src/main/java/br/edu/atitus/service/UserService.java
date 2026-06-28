package br.edu.atitus.service;

import br.edu.atitus.model.User;
import br.edu.atitus.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Regra para registrar um novo usuário
    public User register(User user) throws Exception {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new Exception("O username é obrigatório!");
        }

        // Verifica se o username já está em uso
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new Exception("Este username já está cadastrado!");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new Exception("A senha deve ter pelo menos 6 caracteres!");
        }

        // Em produção aqui aplicaríamos BCrypt para codificar a senha
        return userRepository.save(user);
    }

    // Regra para simular o Login
    public User login(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("Usuário não encontrado!"));

        if (!user.getPassword().equals(password)) {
            throw new Exception("Senha incorreta!");
        }

        return user;
    }
}