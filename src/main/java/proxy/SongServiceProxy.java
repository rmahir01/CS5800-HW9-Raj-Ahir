//CS5800 - HW9 - PROXY
//RAJ AHIR - 019330454

package proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongServiceProxy implements SongService {

    private final SongService realService;
    private final Map<Integer, Song> idCache = new HashMap<>();
    private final Map<String, List<Song>> titleCache = new HashMap<>();
    private final Map<String, List<Song>> albumCache = new HashMap<>();

    public SongServiceProxy(SongService realService) {
        if (realService == null) {
            throw new IllegalArgumentException("Song service cannot be null");
        }
        this.realService = realService;
    }

    @Override
    public Song searchById(Integer songID) {
        if (songID == null) {
            throw new IllegalArgumentException("Song ID cannot be null");
        }

        if (idCache.containsKey(songID)) {
            System.out.println("[PROXY] Returning song from ID cache");
            return idCache.get(songID);
        }

        System.out.println("[PROXY] ID cache miss");
        Song song = realService.searchById(songID);

        if (song != null) {
            idCache.put(songID, song);
        }

        return song;
    }

    @Override
    public List<Song> searchByTitle(String title) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        String key = title.toLowerCase();

        if (titleCache.containsKey(key)) {
            System.out.println("[PROXY] Returning results from title cache");
            return titleCache.get(key);
        }

        System.out.println("[PROXY] Title cache miss");

        List<Song> results = List.copyOf(
                realService.searchByTitle(title)
        );

        titleCache.put(key, results);
        return results;
    }

    @Override
    public List<Song> searchByAlbum(String album) {

        if (album == null || album.isBlank()) {
            throw new IllegalArgumentException("Album cannot be empty");
        }

        String key = album.toLowerCase();

        if (albumCache.containsKey(key)) {
            System.out.println("[PROXY] Returning results from album cache");
            return albumCache.get(key);
        }

        System.out.println("[PROXY] Album cache miss");

        List<Song> results = List.copyOf(
                realService.searchByAlbum(album)
        );

        albumCache.put(key, results);
        return results;
    }
}