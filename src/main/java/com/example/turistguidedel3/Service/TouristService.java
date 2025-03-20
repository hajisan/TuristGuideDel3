package com.example.turistguidedel3.Service;

import com.example.turistguidedel3.Model.Attraction;
import com.example.turistguidedel3.Model.City;
import com.example.turistguidedel3.Model.Tag;
import com.example.turistguidedel3.Repository.TouristRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository touristRepository;
    private final JdbcTemplate jdbcTemplate;

    public TouristService(TouristRepository touristRepository, JdbcTemplate jdbcTemplate) {
        this.touristRepository = touristRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Henter alle turistattraktioner
    public List<Attraction> getAllTouristAttractions() {
        return touristRepository.getAllAttractions();
    }

    public Attraction getAttractionById(int id) {
        return touristRepository.getAttractionById(id);
    }

    public void updateAttractionTags(int attractionId, List<Tag> tags) {
        touristRepository.deleteAttractionTags(attractionId);
        touristRepository.addAttractionTags(attractionId, tags);
    }

    public List<Tag> getTagsByIds(List<Integer> tagIds) {
        return touristRepository.getTagsByIds(tagIds);
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
    public void updateAttraction(Attraction attraction) {
      touristRepository.updateAttraction(attraction);
    }

    // Sletter en attraktion
    public void deleteTouristAttraction(int id) {
        touristRepository.deleteAttraction(id);
    }

    // Henter alle tags
    public List<Tag> getTags() {
        List<Tag> tags = touristRepository.getAllTags();
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