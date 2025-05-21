DROP TABLE IF EXISTS MPA CASCADE;
CREATE TABLE MPA (
                     id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                     name varchar(255) NOT NULL
);

DROP TABLE IF EXISTS Users CASCADE;
CREATE TABLE Users (
                       id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name varchar(255) NOT NULL,
                       birthday date NOT NULL,
                       email varchar(255) NOT NULL,
                       login varchar(255) NOT NULL
);

DROP TABLE IF EXISTS Friend CASCADE;
CREATE TABLE Friend (
                        id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        sideOne BIGINT REFERENCES Users(id) NOT NULL,
                        sideTwo BIGINT REFERENCES Users(id) NOT NULL,
                        isConfirm boolean  DEFAULT false NOT NULL
);

DROP TABLE IF EXISTS Film CASCADE;
CREATE TABLE Film (
                      id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      name varchar(255) NOT NULL,
                      description varchar(255),
                      releaseDate date,
                      duration int,
                      mpaID int REFERENCES MPA(id)
);

DROP TABLE IF EXISTS Likes CASCADE;
CREATE TABLE Likes (
                       id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       userID BIGINT REFERENCES Users(id),
                       filmID BIGINT REFERENCES Film(id)
);

DROP TABLE IF EXISTS Genres CASCADE;
CREATE TABLE Genres (
                       id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name varchar(255) NOT NULL
);

DROP TABLE IF EXISTS FilmGenre CASCADE;
CREATE TABLE FilmGenre (
                           id  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           filmID BIGINT REFERENCES Film(id),
                           genreID int REFERENCES Genres(id)
);

