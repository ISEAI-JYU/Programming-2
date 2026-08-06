Assume there is a class `Song` that represents a single musical track.
A song has a name, a genre, and a duration in seconds:

```java,ignore
class Song {
    String name;
    String genre;
    int durationInSeconds;
}
```

Add the necessary visibility modifiers, a constructor, the required getter methods, and an appropriate implementation of the `toString()` method.

Create a function `createPlaylist(songs, genre, numberOfSongs)` that returns at most the number of songs specified by the `numberOfSongs` parameter, where the song genre matches the given `genre` parameter. The songs must be sorted by duration from shortest to longest.

You can use the following sample list to test your code:

<details closed><summary>Example list of songs</summary>

```java,ignore
List<Song> songList = List.of(
    new Song("Bohemian Rhapsody", "Rock", 354),
    new Song("Levitating", "Pop", 203),
    new Song("Sandstorm", "Electronic", 225),
    new Song("Paranoid", "Metal", 168),
    new Song("Toxic", "Pop", 198),
    new Song("Master of Puppets", "Metal", 515),
    new Song("Cha Cha Cha", "Pop", 175),
    new Song("Hotel California", "Rock", 390),
    new Song("Stay", "Pop", 141),
    new Song("Enter Sandman", "Metal", 331),
    new Song("Bad Romance", "Pop", 295),
    new Song("Midnight City", "Electronic", 243),
    new Song("Billie Jean", "Pop", 294),
    new Song("Hard Rock Hallelujah", "Metal", 247),
    new Song("Thriller", "Pop", 357),
    new Song("As It Was", "Pop", 167),
    new Song("Paint It, Black", "Rock", 202),
    new Song("Hollywood Hills", "Rock", 210),
    new Song("Get Lucky", "Electronic", 369),
    new Song("Shake It Off", "Pop", 219),
    new Song("Ace of Spades", "Metal", 169),
    new Song("Rolling in the Deep", "Pop", 228),
    new Song("Sweet Child O' Mine", "Rock", 356),
    new Song("Borderline", "Pop", 210),
    new Song("Back in Black", "Rock", 255),
    new Song("Shape of You", "Pop", 233),
    new Song("Fear of the Dark", "Metal", 438),
    new Song("Blinding Lights", "Pop", 200),
    new Song("Stairway to Heaven", "Rock", 482),
    new Song("Uptown Funk", "Pop", 269),
    new Song("Smells Like Teen Spirit", "Rock", 301),
    new Song("Short Pop Song", "Pop", 120)
);
```

</details>

**Do not use loops.** Implement `createPlaylist` using streams.

<details closed><summary>Hint</summary>

You may need at least the following Stream methods:

- `filter()`: filters elements
- `sorted()`: sorts elements
- `limit()`: limits the number of elements
- `toList()`: collects the elements into a list

</details>