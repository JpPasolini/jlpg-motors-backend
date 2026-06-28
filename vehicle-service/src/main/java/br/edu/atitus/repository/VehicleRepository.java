package br.edu.atitus.repository;

import br.edu.atitus.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    // CRUD completo já herdado automaticamente do JpaRepository
}