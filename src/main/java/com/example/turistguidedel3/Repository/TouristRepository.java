package com.example.turistguidedel3.Repository;

import com.example.turistguidedel3.Model.Attraction;
import com.example.turistguidedel3.Model.City;
import com.example.turistguidedel3.Model.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TouristRepository {
    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Henter alle byer
    public List<City> getAllCities() {
        String sql = "SELECT * FROM City";
        return jdbcTemplate.query(sql, new CityRowMapper());
    }

    // Henter alle attraktioner med City-navn
    public List<Attraction> getAllAttractions() {
        String sql = "SELECT a.ID, a.Name, a.Description, c.ID as City_ID, c.Name as City_Name " +
                "FROM Attraction a JOIN City c ON a.City_ID = c.ID";
        return jdbcTemplate.query(sql, new AttractionRowMapper());
    }

    // Henter alle tags
    public List<Tag> getAllTags() {
        String sql = "SELECT * FROM Tag";
        return jdbcTemplate.query(sql, new TagRowMapper());
    }

    // Henter en attraktion baseret på navnet, returnerer null hvis ingen fundet
    public Attraction getAttractionByName(String name) {
        String sql = "SELECT a.ID, a.Name, a.Description, c.ID as City_ID, c.Name as City_Name " +
                "FROM Attraction a JOIN City c ON a.City_ID = c.ID WHERE a.Name = ?";
        List<Attraction> attractions = jdbcTemplate.query(sql, new AttractionRowMapper(), name);
        return attractions.isEmpty() ? null : attractions.get(0);
    }

    // Tilføjer en ny attraktion
    public int addAttraction(Attraction attraction) {
        String sql = "INSERT INTO Attraction (Name, City_ID, Description) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, attraction.getName(), attraction.getCity().getId(), attraction.getDescription());
    }

    // Opdaterer en eksisterende attraktion
    public int updateAttraction(Attraction attraction) {
        String sql = "UPDATE Attraction SET Name = ?, City_ID = ?, Description = ? WHERE ID = ?";
        return jdbcTemplate.update(sql, attraction.getName(), attraction.getCity().getId(), attraction.getDescription(), attraction.getId());
    }

    // Sletter en attraktion og smider en fejl, hvis ID ikke findes
    public void deleteAttraction(int id) {
        String sql = "DELETE FROM Attraction WHERE ID = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("No attraction found with ID: " + id);
        }
    }

    // RowMapper til City
    private static class CityRowMapper implements RowMapper<City> {
        @Override
        public City mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new City(rs.getInt("ID"), rs.getString("Name"));
        }
    }

    // RowMapper til Attraction (henter både City_ID og City_Name)
    private static class AttractionRowMapper implements RowMapper<Attraction> {
        @Override
        public Attraction mapRow(ResultSet rs, int rowNum) throws SQLException {
            City city = new City(rs.getInt("City_ID"), rs.getString("City_Name"));
            return new Attraction(rs.getInt("ID"), rs.getString("Name"), city, rs.getString("Description"));
        }
    }

    // RowMapper til Tag
    private static class TagRowMapper implements RowMapper<Tag> {
        @Override
        public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Tag(rs.getInt("ID"), rs.getString("Name"));
        }
    }
}