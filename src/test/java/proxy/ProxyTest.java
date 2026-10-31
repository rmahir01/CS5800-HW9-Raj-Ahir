//CS5800 - HW9 - PROXY
//RAJ AHIR - 019330454

package proxy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyTest {

    @Test
    void testSongConstructorAndGetters() {
        Song song = new Song(1, "Kesariya", "Arijit Singh", "Brahmastra", 268);
        assertEquals(1, song.getSongID());
        assertEquals("Kesariya", song.getTitle());
        assertEquals("Arijit Singh", song.getArtist());
        assertEquals("Brahmastra", song.getAlbum());
        assertEquals(268, song.getDuration());
    }

    @Test
    void testSongToString() {
        Song song = new Song(1, "Kesariya", "Arijit Singh", "Brahmastra", 268);
        assertNotNull(song.toString());
        assertTrue(song.toString().contains("Kesariya"));
    }

    @Test
    void testRealServiceSearchById() {
        RealSongService service = new RealSongService();
        Song result = service.searchById(1);
        assertNotNull(result);
        assertEquals("Kesariya", result.getTitle());
    }

    @Test
    void testRealServiceSearchByTitle() {
        RealSongService service = new RealSongService();
        List<Song> results = service.searchByTitle("Kesariya");
        assertEquals(1, results.size());
        assertEquals("Kesariya", results.get(0).getTitle());
    }

    @Test
    void testRealServiceSearchByAlbum() {
        RealSongService service = new RealSongService();
        List<Song> results = service.searchByAlbum("Brahmastra");
        assertEquals(2, results.size());
        assertEquals("Brahmastra", results.get(0).getAlbum());
    }

    private static class FakeSongService implements SongService {
        private int idCalls = 0;
        private int titleCalls = 0;
        private int albumCalls = 0;
        private final Song song = new Song(1, "Kesariya", "Arijit Singh", "Brahmastra", 268);

        @Override
        public Song searchById(Integer songID) {
            idCalls++;
            if (song.getSongID().equals(songID)) {
                return song;
            }
            return null;
        }

        @Override
        public List<Song> searchByTitle(String title) {
            titleCalls++;
            List<Song> results = new ArrayList<>();
            if (song.getTitle().equalsIgnoreCase(title)) {
                results.add(song);
            }
            return results;
        }

        @Override
        public List<Song> searchByAlbum(String album) {
            albumCalls++;
            List<Song> results = new ArrayList<>();
            if (song.getAlbum().equalsIgnoreCase(album)) {
                results.add(song);
            }
            return results;
        }
    }

    @Test
    void testProxySearchByIdAndCache() {
        FakeSongService fakeServer = new FakeSongService();
        SongServiceProxy proxy = new SongServiceProxy(fakeServer);
        Song firstResult = proxy.searchById(1);
        Song secondResult = proxy.searchById(1);
        assertNotNull(firstResult);
        assertEquals("Kesariya", firstResult.getTitle());
        assertSame(firstResult, secondResult);
        assertEquals(1, fakeServer.idCalls);
    }

    @Test
    void testProxySearchByTitleAndCache() {
        FakeSongService fakeServer = new FakeSongService();
        SongServiceProxy proxy = new SongServiceProxy(fakeServer);
        List<Song> firstResult = proxy.searchByTitle("Kesariya");
        List<Song> secondResult = proxy.searchByTitle("Kesariya");
        assertEquals(1, firstResult.size());
        assertEquals("Kesariya", firstResult.get(0).getTitle());
        assertSame(firstResult, secondResult);
        assertEquals(1, fakeServer.titleCalls);
    }

    @Test
    void testProxySearchByAlbumAndCache() {
        FakeSongService fakeServer = new FakeSongService();
        SongServiceProxy proxy = new SongServiceProxy(fakeServer);
        List<Song> firstResult = proxy.searchByAlbum("Brahmastra");
        List<Song> secondResult = proxy.searchByAlbum("Brahmastra");
        assertEquals(1, firstResult.size());
        assertEquals("Brahmastra", firstResult.get(0).getAlbum());
        assertSame(firstResult, secondResult);
        assertEquals(1, fakeServer.albumCalls);
    }

    @Test
    void testProxyRejectsNullService() {
        assertThrows(IllegalArgumentException.class,
                () -> new SongServiceProxy(null));
    }

    @Test
    void testSearchByIdRejectsNull() {
        SongServiceProxy proxy = new SongServiceProxy(new FakeSongService());
        assertThrows(IllegalArgumentException.class,
                () -> proxy.searchById(null));
    }

    @Test
    void testSearchByTitleRejectsBlankTitle() {
        SongServiceProxy proxy = new SongServiceProxy(new FakeSongService());
        assertThrows(IllegalArgumentException.class,
                () -> proxy.searchByTitle(" "));
    }

    @Test
    void testSearchByAlbumRejectsBlankAlbum() {
        SongServiceProxy proxy = new SongServiceProxy(new FakeSongService());
        assertThrows(IllegalArgumentException.class,
                () -> proxy.searchByAlbum(" "));
    }
}
