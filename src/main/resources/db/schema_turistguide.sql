-- Opretter databasen, hvis den ikke allerede findes
CREATE DATABASE IF NOT EXISTS touristguide;
USE touristguide;

-- Tabelstruktur for 'City', som gemmer information om byer
CREATE TABLE City(
    ID INT NOT NULL AUTO_INCREMENT, -- Unikt ID for hver by, autoincrement starter fra 100
    Name VARCHAR(100) UNIQUE, -- Byens navn, skal være unikt
    PRIMARY KEY (ID) -- Definerer ID som primærnøgle
) AUTO_INCREMENT = 100;

-- Tabelstruktur for 'Attraction', som gemmer information om turistattraktioner
CREATE TABLE Attraction(
    ID INT NOT NULL AUTO_INCREMENT, -- Unikt ID for hver attraktion, autoincrement starter fra 1000
    Name VARCHAR(100) UNIQUE, -- Attraktionens navn, skal være unikt
    City_ID INT NOT NULL, -- Refererer til ID på den by, hvor attraktionen er placeret
    Description TEXT, -- Beskrivelse af attraktionen
    PRIMARY KEY (ID), -- Definerer ID som primærnøgle
    FOREIGN KEY (City_ID) REFERENCES City(ID) -- Relation til City-tabellen
) AUTO_INCREMENT = 1000;

-- Tabelstruktur for 'Tag', som gemmer information om tags/kategorier for attraktioner
CREATE TABLE Tag(
    ID INT NOT NULL AUTO_INCREMENT, -- Unikt ID for hvert tag
    Name VARCHAR(50) NOT NULL UNIQUE, -- Tag-navnet, skal være unikt
    PRIMARY KEY (ID) -- Definerer ID som primærnøgle
) AUTO_INCREMENT = 1;

-- Tabelstruktur for 'Attraction_Tag', som håndterer mange-til-mange-relationen mellem Attraction og Tag
CREATE TABLE Attraction_Tag(
    Tag_ID INT NOT NULL, -- ID for tagget, som kobles til en attraktion
    Attraction_ID INT NOT NULL, -- ID for attraktionen, som kobles til et tag
    PRIMARY KEY (Tag_ID, Attraction_ID), -- Primærnøgle består af begge ID'er
    FOREIGN KEY (Tag_ID) REFERENCES Tag(ID) ON DELETE CASCADE, -- Hvis et tag slettes, fjernes også dets koblinger til attraktioner
    FOREIGN KEY (Attraction_ID) REFERENCES Attraction(ID) ON DELETE CASCADE -- Hvis en attraktion slettes, fjernes også dens koblinger til tags
);