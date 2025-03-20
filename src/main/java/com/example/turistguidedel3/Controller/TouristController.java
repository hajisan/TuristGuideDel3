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
    public String saveAttraction(@ModelAttribute Attraction attraction,
                                 @RequestParam(required = false) List<Tag> tags) {
        if (tags != null) {
            attraction.setTags(tags);
        } else {
            attraction.setTags(List.of());
        }
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

    @PostMapping("/attractions/update")
    public String updateAttraction(@RequestParam("id") int id, @ModelAttribute Attraction attraction) {
        attraction.setId(id);
        touristService.updateTouristAttraction(attraction);
        return "redirect:/attractions";
    }

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