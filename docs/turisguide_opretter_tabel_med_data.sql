-- Opretter databasen, hvis den ikke allerede findes
CREATE DATABASE IF NOT EXISTS touristguide;
USE touristguide;

-- Database for turistattraktioner

-- Opretter de nødvendige tabeller til at gemme data om byer, attraktioner og tags

-- Tabelstruktur for 'City', som gemmer information om byer
CREATE TABLE City(
	ID INT NOT NULL AUTO_INCREMENT, -- Unikt ID for hver by, autoincrement starter fra 100
	Name VARCHAR(100) UNIQUE, -- Byens navn, skal være unikt
    PRIMARY KEY (ID) -- Definerer ID som primærnøgle
) AUTO_INCREMENT = 100;

-- Indsæt data i City tabel
INSERT IGNORE INTO City (Name) VALUES
('København'), 
('Aarhus'), 
('Odense'), 
('Fredericia'), 
('Ballerup'), 
('Viborg'), 
('Køge'), 
('Holstebro'), 
('Aalborg'), 
('Esbjerg'), 
('Hørsholm'), 
('Helsingør'), 
('Silkeborg'), 
('Næstved'), 
('Randers'), 
('Kolding'), 
('Horsens'), 
('Vejle'), 
('Roskilde'), 
('Herning'),
('Billund'),
('Ribe'),
('Nimtofte'),
('Blokhus'),
('Kværndrup'),
('Møn');

-- Tabelstruktur for 'Attraction', som gemmer information om turistattraktioner
CREATE TABLE Attraction(
	ID INT NOT NULL AUTO_INCREMENT, -- Unikt ID for hver attraktion, autoincrement starter fra 1000
    Name VARCHAR(100) UNIQUE, -- Attraktionens navn, skal være unikt
    City_ID INT NOT NULL, -- Refererer til ID på den by, hvor attraktionen er placeret
    Description TEXT, -- Beskrivelse af attraktionen, tillader længere tekst
    PRIMARY KEY (ID), -- Definerer ID som primærnøgle
    FOREIGN KEY (City_ID) REFERENCES City(ID) -- Opretter en relation til City-tabellen
) AUTO_INCREMENT = 1000;

-- Indsæt data i Attraction tabel
INSERT IGNORE INTO Attraction (Name, Description, City_ID) VALUES
('Tivoli', 'En af verdens ældste forlystelsesparker med smukke haver, forlystelser og restauranter.', (SELECT ID FROM City WHERE Name = 'København')),
('Den Lille Havfrue', 'Den ikoniske statue inspireret af H.C. Andersens eventyr, placeret ved Langelinie.', (SELECT ID FROM City WHERE Name = 'København')),
('Legoland', 'En populær familiepark fyldt med forlystelser og imponerende LEGO-modeller.', (SELECT ID FROM City WHERE Name = 'Billund')),
('Nyhavn', 'Et charmerende havneområde med farverige bygninger, restauranter og en maritim stemning.', (SELECT ID FROM City WHERE Name = 'København')),
('Kronborg Slot', 'Det berømte slot fra Shakespeares Hamlet, med en rig historie og imponerende arkitektur.', (SELECT ID FROM City WHERE Name = 'Helsingør')),
('Rundetårn', 'Et historisk tårn med en unik spiralrampe og en fantastisk udsigt over København.', (SELECT ID FROM City WHERE Name = 'København')),
('Nationalmuseet', 'Danmarks største museum for kulturhistorie med udstillinger fra oldtid til nutid.', (SELECT ID FROM City WHERE Name = 'København')),
('Aros Aarhus Kunstmuseum', 'Moderne kunstmuseum kendt for den ikoniske regnbuepanorama på taget.', (SELECT ID FROM City WHERE Name = 'Aarhus')),
('Den Gamle By', 'Et levende frilandsmuseum, der viser dansk byliv fra forskellige tidsperioder.', (SELECT ID FROM City WHERE Name = 'Aarhus')),
('Ribe Vikingecenter', 'En autentisk rekonstruktion af en vikingeby med aktiviteter og levende historieformidling.', (SELECT ID FROM City WHERE Name = 'Ribe')),
('Lalandia', 'En populær ferie- og vandlandresort med aktiviteter for hele familien.', (SELECT ID FROM City WHERE Name = 'Billund')),
('Djurs Sommerland', 'En af Danmarks største forlystelsesparker med rutsjebaner og vandland.', (SELECT ID FROM City WHERE Name = 'Nimtofte')),
('Fårup Sommerland', 'En stor forlystelsespark i Nordjylland med forlystelser og naturaktiviteter.', (SELECT ID FROM City WHERE Name = 'Blokhus')),
('Egeskov Slot', 'Et smukt renæssanceslot med store haver og en imponerende veteranbil- og motorcykelsamling.', (SELECT ID FROM City WHERE Name = 'Kværndrup')),
('Møns Klint', 'En af Danmarks mest spektakulære naturoplevelser med høje kridtklinter ud mod Østersøen.', (SELECT ID FROM City WHERE Name = 'Møn'));

-- Tabelstruktur for 'Tag', som gemmer information om tags/kategorier for attraktioner
CREATE TABLE Tag(
	ID INT NOT NULL AUTO_INCREMENT, -- Unikt ID for hvert tag, autoincrement starter fra 1
	Name VARCHAR(50) NOT NULL UNIQUE, -- Tag-navnet, skal være unikt og ikke null
	PRIMARY KEY (ID) -- Definerer ID som primærnøgle
) AUTO_INCREMENT = 1;

-- Indsæt data i Tags tabel
INSERT IGNORE INTO Tag (Name) VALUES 
('Kunst'),
('Museum'),
('Handikapvenligt'),
('Børnevenligt'),
('Natur'),
('Gratis');

-- Tabelstruktur for 'Attraction_Tag', som håndterer mange-til-mange-relationen mellem Attraction og Tag
CREATE TABLE Attraction_Tag( 
	Tag_ID INT NOT NULL, -- ID for tagget, som kobles til en attraktion
	Attraction_ID INT NOT NULL, -- ID for attraktionen, som kobles til et tag
	PRIMARY KEY (Tag_ID, Attraction_ID), -- Primærnøgle består af begge ID'er, så samme par ikke kan opstå flere gange
	FOREIGN KEY (Tag_ID) REFERENCES Tag(ID) ON DELETE CASCADE, -- Hvis et tag slettes, fjernes også dets koblinger til attraktioner
	FOREIGN KEY (Attraction_ID) REFERENCES Attraction(ID) ON DELETE CASCADE -- Hvis en attraktion slettes, fjernes også dens koblinger til tags
);

-- Indsæt relationer mellem attraktioner og tags
INSERT INTO Attraction_Tag (Tag_ID, Attraction_ID)
SELECT (SELECT ID FROM Tag WHERE Name = 'Børnevenligt'), (SELECT ID FROM Attraction WHERE Name = 'Tivoli')
UNION SELECT (SELECT ID FROM Tag WHERE Name = 'Handikapvenligt'), (SELECT ID FROM Attraction WHERE Name = 'Tivoli')
UNION SELECT (SELECT ID FROM Tag WHERE Name = 'Gratis'), (SELECT ID FROM Attraction WHERE Name = 'Den Lille Havfrue')
UNION SELECT (SELECT ID FROM Tag WHERE Name = 'Handikapvenligt'), (SELECT ID FROM Attraction WHERE Name = 'Den Lille Havfrue')
UNION SELECT (SELECT ID FROM Tag WHERE Name = 'Museum'), (SELECT ID FROM Attraction WHERE Name = 'Kronborg Slot')
UNION SELECT (SELECT ID FROM Tag WHERE Name = 'Kunst'), (SELECT ID FROM Attraction WHERE Name = 'Kronborg Slot')
UNION SELECT (SELECT ID FROM Tag WHERE Name = 'Handikapvenligt'), (SELECT ID FROM Attraction WHERE Name = 'Kronborg Slot')
UNION SELECT (SELECT ID FROM Tag WHERE Name = 'Natur'), (SELECT ID FROM Attraction WHERE Name = 'Møns Klint')
UNION SELECT (SELECT ID FROM Tag WHERE Name = 'Gratis'), (SELECT ID FROM Attraction WHERE Name = 'Møns Klint');