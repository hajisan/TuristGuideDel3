package com.example.turistguidedel3;

import com.example.turistguidedel3.Controller.TouristController;
import com.example.turistguidedel3.Service.TouristService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TouristService touristService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private TouristController touristController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetAttractionByName() throws Exception {
        // Arrange
        String name = "Tivoli";
        TouristAttraction attraction = new TouristAttraction(name, "Forlystelsespark midt i København centrum");
        when(touristService.findTouristAttractionByName(name)).thenReturn(attraction);

        mockMvc.perform(get("/attractions/{name}", name))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))  // Tjekker at "index.html" returneres
                .andExpect(model().attributeExists("attraction"))  // Tjekker at model-objektet er til stede
                .andExpect(model().attribute("attraction", attraction));  // Tjekker at attraktionen er i model
    }
}