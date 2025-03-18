package com.example.turistguidedel3.Service;


import com.example.turistguidedel3.Model.Attraction;
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

    public List<Attraction> getAllTouristAttractions() {
        return touristRepository.getAllAttractions();
    } // Henter alle turistattraktioner fra TouristRepository


    public Attraction findTouristAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    } // Henter en attraktion baseret på navnet fra TouristRepository

    public void addAttraction(Attraction attraction) {
        touristRepository.addAttraction(attraction);
    }
    // Tilføjer en ny attraktion til TouristRepository

    public void updateTouristAttraction(Attraction updateTouristAttraction) {
        int updated = touristRepository.updateAttraction(updateTouristAttraction);
        if (updated == 0) {
            throw new IllegalArgumentException("Attraktionen med ID '" + updateTouristAttraction.getId() + "' blev ikke fundet.");
        } // Opdaterer en eksisterende attraktion i TouristRepository
    }

    public Attraction deleteTouristAttraction(int id) {
        return touristRepository.deleteAttraction(id);
    } // Sletter en attraktion fra TouristRepository baseret på navnet

    public List<Tag> getTags(String name) {
        return touristRepository.getAllTags(name);
         // Henter tags for en specifik attraktion baseret på navnet
    }

    public List<String> getCities() {
        return touristRepository.getAllCities();
    }
    // Henter en liste over alle byer fra TouristRepository

}



