package br.edu.atitus.service;

import br.edu.atitus.model.Vehicle;
import br.edu.atitus.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle save(Vehicle vehicle) throws Exception {
        // Validações de negócio completas
        if (vehicle.getBrand() == null || vehicle.getBrand().trim().isEmpty()) {
            throw new Exception("A marca do veículo é obrigatória!");
        }
        if (vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()) {
            throw new Exception("O modelo do veículo é obrigatório!");
        }
        if (vehicle.getYearModel() == null || vehicle.getYearModel() < 1900) {
            throw new Exception("O ano do veículo deve ser maior ou igual a 1900!");
        }
        if (vehicle.getPrice() == null || vehicle.getPrice().doubleValue() <= 0) {
            throw new Exception("O preço do veículo deve ser maior que zero!");
        }

        return vehicleRepository.save(vehicle);
    }
}