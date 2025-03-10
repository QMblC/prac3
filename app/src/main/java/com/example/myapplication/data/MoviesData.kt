package com.example.myapplication.data

import com.example.myapplication.model.Movie
import com.example.myapplication.model.Person
import com.example.myapplication.model.Rating

object MoviesData {
    public val movies = listOf(
        Movie(
            id = 1,
            name = "Oppenheimer",
            rating = Rating(8.4, 632078),
            plot = "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.",
            premierYear = 2023,
            posterImageURL = "https://m.media-amazon.com/images/M/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_.jpg",
            genres = listOf("Biography", "Drama", "History"),
            countries = listOf("United States", "United Kingdom"),
            people = listOf(
                Person(
                    photoURL = "https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/90a98ddf-5d0e-4dbc-be99-79e9220704c1/1920x",
                    name = "Cillian Murphy",
                    characters = listOf("J. Robert Oppenheimer")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BMTUxNDY4MTMzM15BMl5BanBnXkFtZTcwMjg5NzM2Ng@@._V1_.jpg",
                    name = "Emily Blunt",
                    characters = listOf("Kitty Oppenheimer")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BNzg1MTUyNDYxOF5BMl5BanBnXkFtZTgwNTQ4MTE2MjE@._V1_.jpg",
                    name = "Robert Downey Jr.",
                    characters = listOf("Lewis Strauss")
                )
            )
        ),
        Movie(
            id = 2,
            name = "The Dark Knight",
            rating = Rating(9.0, 2838037),
            plot = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
            premierYear = 2008,
            posterImageURL = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg",
            genres = listOf("Action", "Crime", "Drama", "Thriller"),
            countries = listOf("United Kingdom", "United States"),
            people = listOf(
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BMTkxMzk4MjQ4MF5BMl5BanBnXkFtZTcwMzExODQxOA@@._V1_.jpg",
                    name = "Christian Bale",
                    characters = listOf("Bruce Wayne", "Batman")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BMTI2NTY0NzA4MF5BMl5BanBnXkFtZTYwMjE1MDE0._V1_.jpg",
                    name = "Heath Ledger",
                    characters = listOf("Joker")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BMTc4MTAyNzMzNF5BMl5BanBnXkFtZTcwMzQ5MzQzMg@@._V1_.jpg",
                    name = "Aaron Eckhart",
                    characters = listOf("Harvey Dent")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BMjAwNzIwNTQ4Ml5BMl5BanBnXkFtZTYwMzE1MTUz._V1_.jpg",
                    name = "Michael Caine",
                    characters = listOf("Alfred")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BMTM1MjY3NzA4NF5BMl5BanBnXkFtZTcwNzE4MjMyMw@@._V1_.jpg",
                    name = "Maggie Gyllenhaal",
                    characters = listOf("Rachel")
                )
            )
        ),
        Movie(
            id = 3,
            name = "Spider-Man: No Way Home",
            rating = Rating(8.2, 862607),
            plot = "With Spider-Man's identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear, forcing Peter to discover what it truly means to be Spider-Man.",
            premierYear = 2021,
            posterImageURL = "https://m.media-amazon.com/images/M/MV5BZWMyYzFjYTYtNTRjYi00OGExLWE2YzgtOGRmYjAxZTU3NzBiXkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_.jpg",
            genres = listOf("Action", "Adventure", "Fantasy", "Sci-Fi"),
            countries = listOf("United States"),
            people = listOf(
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BNzZiNTEyNTItYjNhMS00YjI2LWIwMWQtZmYwYTRlNjMyZTJjXkEyXkFqcGdeQXVyMTExNzQzMDE0._V1_.jpg",
                    name = "Tom Holland",
                    characters = listOf("Peter Parker", "Spider-Man")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BMjAxZTk4NDAtYjI3Mi00OTk3LTg0NDEtNWFlNzE5NDM5MWM1XkEyXkFqcGdeQXVyOTI3MjYwOQ@@._V1_.jpg",
                    name = "Zendaya",
                    characters = listOf("MJ")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BMjE0MDkzMDQwOF5BMl5BanBnXkFtZTgwOTE1Mjg1MzE@._V1_.jpg",
                    name = "Benedict Cumberbatch",
                    characters = listOf("Doctor Strange")
                ),
                Person(
                    photoURL = "https://m.media-amazon.com/images/M/MV5BNjcwNzg4MjktNDNlMC00M2U1LWJmMjgtZTVkMmI4MDI2MTVmXkEyXkFqcGdeQXVyMjI4MDI0NTM@._V1_.jpg",
                    name = "Jon Favreau",
                    characters = listOf("Happy Hogan")
                )
            )
        )


    )
}