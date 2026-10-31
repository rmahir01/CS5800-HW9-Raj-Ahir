//CS5800 - HW9 - PROXY
//RAJ AHIR - 019330454

package proxy;

import java.util.ArrayList;
import java.util.List;

public class RealSongService implements SongService {

    private final List<Song> songs = new ArrayList<>();

    public RealSongService() {
        songs.add(new Song(1, "Kesariya", "Arijit Singh",
                "Brahmastra", 268));

        songs.add(new Song(2, "Deva Deva", "Arijit Singh",
                "Brahmastra", 286));

        songs.add(new Song(3, "Tum Hi Ho", "Arijit Singh",
                "Aashiqui 2", 262));

        songs.add(new Song(4, "Chahun Main Ya Naa", "Palak Muchhal",
                "Aashiqui 2", 302));

        songs.add(new Song(5, "Apna Bana Le", "Arijit Singh",
                "Bhediya", 261));

        songs.add(new Song(6, "Tere Vaaste", "Varun Jain",
                "Zara Hatke Zara Bachke", 205));

        songs.add(new Song(7, "Chaleya", "Arijit Singh",
                "Jawan", 200));

        songs.add(new Song(8, "Heeriye", "Arijit Singh",
                "Single", 194));
    }

    private void simulateServerDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Server request was interrupted", e);
        }
    }

    @Override
    public Song searchById(Integer songID) {
        simulateServerDelay();
        System.out.println("[SERVER] Searching by ID: " + songID);

        for (Song song : songs) {
            if (song.getSongID().equals(songID)) {
                return song;
            }
        }
        return null;
    }

    @Override
    public List<Song> searchByTitle(String title) {
        simulateServerDelay();
        System.out.println("[SERVER] Searching by title: " + title);

        List<Song> results = new ArrayList<>();

        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                results.add(song);
            }
        }
        return results;
    }

    @Override
    public List<Song> searchByAlbum(String album) {
        simulateServerDelay();
        System.out.println("[SERVER] Searching by album: " + album);

        List<Song> results = new ArrayList<>();

        for (Song song : songs) {
            if (song.getAlbum().equalsIgnoreCase(album)) {
                results.add(song);
            }
        }
        return results;
    }
}
