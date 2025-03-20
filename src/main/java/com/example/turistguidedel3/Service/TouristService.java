package com.example.turistguidedel3.Service;

import com.example.turistguidedel3.Model.Attraction;
import com.example.turistguidedel3.Model.City;
import com.example.turistguidedel3.Model.Tag;
import com.example.turistguidedel3.Repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    // Henter alle turistattraktioner
    public List<Attraction> getAllTouristAttractions() {
        return touristRepository.getAllAttractions();
    }

    // Henter en attraktion baseret på navn
    public Attraction findTouristAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    }

    // Tilføjer en ny attraktion
    public void addAttraction(Attraction attraction) {
        touristRepository.addAttraction(attraction);
    }

    // Opdaterer en attraktion
    public void updateTouristAttraction(Attraction updateTouristAttraction) {
        int updated = touristRepository.updateAttraction(updateTouristAttraction);
        if (updated == 0) {
            throw new IllegalArgumentException("Attraktionen med ID '" + updateTouristAttraction.getId() + "' blev ikke fundet.");
        }
    }

    // Sletter en attraktion
    public void deleteTouristAttraction(int id) {
        touristRepository.deleteAttraction(id);
    }

    // Henter alle tags
    public List<Tag> getTags() {
        return touristRepository.getAllTags();
    }

    // Henter tags for en specifik attraktion baseret på navn
    public List<Tag> getTags(String name) {
        Attraction attraction = touristRepository.getAttractionByName(name);
        if (attraction != null) {
            return attraction.getTags();
        }
        throw new IllegalArgumentException("Attraktionen med navn '" + name + "' blev ikke fundet.");
    }

    // Henter alle byer
    public List<City> getCities() {
        return touristRepository.getAllCities();
    }
}