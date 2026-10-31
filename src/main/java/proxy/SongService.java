//CS5800 - HW9 - PROXY
//RAJ AHIR - 019330454

package proxy;

import java.util.List;

public interface SongService {
    Song searchById(Integer songID);
    List<Song> searchByTitle(String title);
    List<Song> searchByAlbum(String album);
}
