package com.example.turistguidedel3.Service;


import com.example.turistguidedel3.Model.Tag;
import com.example.turistguidedel3.Repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    } // Konstruktør til at initialisere TouristRepository-objektet

    public List<TouristAttraction> getAllTouristAttractions() {
        return touristRepository.getAllTouristAttractions();
    } // Henter alle turistattraktioner fra TouristRepository


    public TouristAttraction findTouristAttractionByName(String name) {
        TouristAttraction touristAttraction = touristRepository.findTouristAttractionByName(name);
        return touristAttraction;
    } // Henter en attraktion baseret på navnet fra TouristRepository

    public void addAttraction(TouristAttraction attraction) {
        touristRepository.addAttraction(attraction);
    }
    // Tilføjer en ny attraktion til TouristRepository

    public void updateTouristAttraction(String name, TouristAttraction updateTouristAttraction) {
        TouristAttraction updated = touristRepository.updateTouristAttraction(name, updateTouristAttraction);
        if (updated == null) {
            throw new IllegalArgumentException("Attraktionen med navnet '" + name + "' blev ikke fundet.");
        } // Opdaterer en eksisterende attraktion i TouristRepository
    }

    public TouristAttraction deleteTouristAttraction(String name) {
        return touristRepository.deleteTouristAttraction(name);
    } // Sletter en attraktion fra TouristRepository baseret på navnet

    public List<Tag> getTags(String name) {
        return touristRepository.getTags(name);
         // Henter tags for en specifik attraktion baseret på navnet
    }

    public List<String> getCities() {
        return touristRepository.allCities();
    }
    // Henter en liste over alle byer fra TouristRepository

}



