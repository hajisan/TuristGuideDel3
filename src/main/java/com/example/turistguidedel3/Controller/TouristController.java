package com.example.turistguidedel3.Controller;


import com.example.turistguidedel3.Model.Attraction;
import com.example.turistguidedel3.Model.Tag;
import com.example.turistguidedel3.Service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.yaml.snakeyaml.tokens.Token.ID.Tag;

@Controller
public class TouristController {

    private final TouristService touristService; // final instans-variabel med et TouristService-objekt

    // Konstruktør til at initialisere turistservice-objektet
    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    /*
    @GetMapping fortæller Spring Boot, at denne metode skal kaldes,
    når en bruger besøger hjemmesidens forside ("localhost:8080/" eller en tilsvarende URL).
    Nedenstående metoder håndterer en GET-anmodning til den relevante URL og returnerer en HTML-side.

    @PostMapping fortæller Spring Boot, at denne metode skal håndtere en HTTP POST-anmodning til den angivne URL.
    POST-anmodninger bruges typisk til at sende data til serveren, f.eks. når en bruger indsender en formular.
    Nedenstående metoder håndterer en POST-anmodning til den relevante URL, behandler de modtagne data og returnerer
    enten en HTML-side eller omdirigerer brugeren til en anden side.
    */

    @GetMapping("/")
    public String home() {
        return "index"; // Returnerer index.html
    }

    @GetMapping("/attractions")
    public String getAttractions(Model model) { // Model-objektet bruges til at sende data fra Java-koden til HTML-siden
        /* Henter en liste over turistattraktioner fra touristService og
           tilføjer den til model under navnet "attractions", så dataene kan bruges i HTML-siden. */
        model.addAttribute("attractions", touristService.getAllTouristAttractions());
        return "attractionList"; // Returnerer navnet på HTML-siden, der skal vises (attractionList.html)
    }

    @GetMapping("/attractions/{name}")
    public String getAttractionByName(Model model, @PathVariable String name) { // @PathVariable henter værdien fra URL'en
        // Finder attraktion baseret på "name"
        Attraction touristAttraction = touristService.findTouristAttractionByName(name); // tilføj metode til at hente navn fra en attraktion
        // Tilføjer attraktion til model-objektet, så den kan bruges i HTML-siden
        model.addAttribute("attraction", touristAttraction);
        return "index"; // Returnerer index.html - her ville man normalt oprette en separat HTML-side for attraktionen
    }

    // Metode for at tilføje en ny attraktion
    @GetMapping("/attractions/add")
    public String addAttraction(Model model) {
        String name = "Tivoli"; // Navnet på attraktionen
        model.addAttribute("attraction", new Attraction(name, "Forlystelsespark midt i København centrum")); // Opretter en tom attraktion
        model.addAttribute("tags", Tag.values()); // Henter tags-værdier fra Tags-enum
        return "newAttraction"; // Returnerer newAttraction.html
    }

    // Metode til at opdatere en attraktion
    @PostMapping("/attractions/update")
    public String updateAttraction(
            @RequestParam("name") int id, // Henter navnet på attraktionen fra formularen
            @ModelAttribute Attraction attraction) { // Binder formularens data til et TouristAttraction-objekt
        attraction.setId(id);
        touristService.updateTouristAttraction(attraction); // Opdaterer attraktionen i systemet
        return "redirect:/attractions"; // Omdirigerer brugeren tilbage til listen over attraktioner
    }

    // Metode til at slette en attraktion
    @PostMapping("/attractions/delete/{name}")
    public String deleteAttraction(Model model, @PathVariable String name) { // @PathVariable henter attraktionens navn fra URL'en
        // Kalder service-laget for at slette attraktionen med det givne navn
        Attraction deletedAttraction = touristService.deleteTouristAttraction(name);
        model.addAttribute("attraction", deletedAttraction); // Lagrer den slettede attraktion i model (hvis nødvendig)
        return "redirect:/attractions"; // Omdirigerer brugeren tilbage til listen over attraktioner
    }

    // Metode til at redigere en eksisterende attraktion
    @GetMapping("/attractions/{name}/edit")
    public String editAttraction(Model model, @PathVariable String name) { // Finder attraktionen baseret på navnet
        Attraction touristAttraction = touristService.findTouristAttractionByName(name);
        if (touristAttraction == null) { // Hvis attraktionen ikke findes, kastes en fejl
            throw new IllegalArgumentException("Ugyldigt attraktion");
        }
        // Tilføjer attraktionen til model-objektet, så HTML-siden kan redigere den
        model.addAttribute("attraction", touristAttraction);
        model.addAttribute("cities", touristService.getCities()); // Henter tilgængelige byer
        model.addAttribute("tags", Tag.values()); // Henter tilgængelige tags
        return "updateAttraction"; // Returnerer updateAttraction.html
    }

    // Metode til at gemme en ny attraktion
    @PostMapping("/attractions/save")
    public String saveAttraction(@ModelAttribute Attraction attraction,
                                 @RequestParam(required = false) List<Tag> tags) { // Henter valgte tags fra formularen
        if (tags != null) {
            attraction.setTag(tag); // Hvis tags er valgt, tildeles de til attraktionen
        } else {
            attraction.setTag(new ArrayList<>()); // Hvis ingen tags er valgt, tildeles en tom liste
        }
        touristService.addAttraction(attraction); // Gemmer attraktionen via service-laget
        return "redirect:/attractions"; // Omdirigerer brugeren til listen over attraktioner
    }

    // Metode til at hente tags for en specifik attraktion
    @GetMapping("/attractions/{name}/tags")
    public String getTags(Model model, @PathVariable String name) { // Finder attraktionens tags baseret på navn
        List<Tag> tags = touristService.getTags(name); // Henter tags fra service-laget
        model.addAttribute("tags", tags); // Tilføjer tags til model, så de kan bruges i HTML-siden
        return "tags"; // Returnerer tags.html
    }

    // Metode til at vise "Om os"-siden
    @GetMapping("/about")
    public String about() {
        return "about"; // Returnerer about.html
    }
}