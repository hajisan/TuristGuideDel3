package com.example.turistguidedel3.Controller;

import com.example.turistguidedel3.Model.Attraction;
import com.example.turistguidedel3.Model.City;
import com.example.turistguidedel3.Model.Tag;
import com.example.turistguidedel3.Service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/attractions")
    public String getAttractions(Model model) {
        model.addAttribute("attractions", touristService.getAllTouristAttractions());
        return "attractionList";
    }

    @GetMapping("/attractions/{name}")
    public String getAttractionByName(Model model, @PathVariable String name) {
        Attraction attraction = touristService.findTouristAttractionByName(name);
        model.addAttribute("attraction", attraction);
        return "attractionDetail";
    }

    @GetMapping("/attractions/add")
    public String addAttraction(Model model) {
        model.addAttribute("attraction", new Attraction(0, "", new City(0, ""), ""));
        model.addAttribute("tags", touristService.getTags());
        return "newAttraction";
    }

    @PostMapping("/attractions/save")
    public String saveAttraction(@ModelAttribute Attraction attraction) {
        System.out.println("Modtager attraction: " + attraction);
        touristService.addAttraction(attraction);
        return "redirect:/attractions";
    }

    @GetMapping("/attractions/{name}/edit")
    public String editAttraction(Model model, @PathVariable String name) {
        Attraction attraction = touristService.findTouristAttractionByName(name);
        if (attraction == null) {
            throw new IllegalArgumentException("Attraktionen findes ikke");
        }
        model.addAttribute("attraction", attraction);
        model.addAttribute("cities", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        return "updateAttraction";
    }

    @GetMapping("/attractions/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Attraction attraction = touristService.getAttractionById(id);
        List<City> cities = touristService.getCities();
        List<Tag> tags = touristService.getTags();

        model.addAttribute("attraction", attraction);
        model.addAttribute("cities", cities);
        model.addAttribute("tags", tags);

        return "updateAttraction";
    }

    @PostMapping("/attractions/update")
    public String updateAttraction(@ModelAttribute Attraction attraction,
                                   @RequestParam(value = "tags", required = false) List<Integer> tagIds) {
        System.out.println("===== DEBUGGING DATA =====");
        System.out.println("Modtager attraction: " + attraction);
        System.out.println("Modtager tags fra formular: " + tagIds);

        if (tagIds == null) {
            System.out.println("Ingen tags valgt!");
        } else {
            // Konverter liste af IDs til en liste af faktiske Tag-objekter
            List<Tag> selectedTags = tagIds.stream()
                    .map(id -> new Tag(id, null)) // Brug null for navnet, da vi kun har ID'et
                    .toList();

            attraction.setTags(selectedTags);
        }

        // Kald service til at opdatere attraktionen
        touristService.updateAttraction(attraction);

        return "redirect:/attractions";
    }

//    @PostMapping("/attractions/update")
//    public String updateAttraction(@ModelAttribute Attraction attraction,
//                                   @RequestParam(value = "tags", required = false) List<Integer> tagIds) {
//        System.out.println("Modtager attraction: " + attraction);
//        System.out.println("Modtager tags: " + tagIds);
//
//        // Opdater attraktionens oplysninger
//        touristService.updateAttraction(attraction);
//
//        // Opdater tags, hvis de er valgt
//        if (tagIds != null) {
//            List<Tag> tags = touristService.getTagsByIds(tagIds); // Hent Tag-objekter fra ID'er
//            attraction.setTags(tags);
//            touristService.updateAttractionTags(attraction.getId(), tags);
//        }
//
//        return "redirect:/attractions";
//    }

//    @PostMapping("/attractions/update")
//    public String updateAttraction(@ModelAttribute Attraction attraction) {
//        System.out.println("Modtager attraction: " + attraction);
//        touristService.updateAttraction(attraction);
//        return "redirect:/attractions";
//    }

    @PostMapping("/attractions/delete/{id}")
    public String deleteAttraction(@PathVariable int id) {
        touristService.deleteTouristAttraction(id);
        return "redirect:/attractions";
    }

    @GetMapping("/attractions/{name}/tags")
    public String getTags(Model model, @PathVariable String name) {
        model.addAttribute("tags", touristService.getTags(name));
        return "tags";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}