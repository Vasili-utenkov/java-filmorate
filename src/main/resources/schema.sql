CREATE TABLE IF NOT EXISTS MPA (
   id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   name varchar(255) NOT NULL
    );


CREATE TABLE IF NOT EXISTS Users (
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    birthday date NOT NULL,
    email varchar(255) NOT NULL,
    login varchar(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Friend (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sideOne int REFERENCES Users(id),
    sideTwo int REFERENCES Users(id),
    isConfirm boolean  DEFAULT false NOT NULL
);

CREATE TABLE IF NOT EXISTS Film (
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    releaseDate date NOT NULL,
    duration int NOT NULL,
    mpaID int REFERENCES MPA(id)
    );

CREATE TABLE IF NOT EXISTS Likes (
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    userID int REFERENCES Users(id),
    filmID int REFERENCES Film(id)
);

CREATE TABLE IF NOT EXISTS Genre (
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS FilmGenre (
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    filmID int REFERENCES Film(id),
    genreID int REFERENCES Genre(id)
);

