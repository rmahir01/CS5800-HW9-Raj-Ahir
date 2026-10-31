//CS5800 - HW9 - PROXY
//RAJ AHIR - 019330454

package proxy;

public class Song {

    private final Integer songID;
    private final String title;
    private final String artist;
    private final String album;
    private final int duration;

    public Song(Integer songID, String title,
                String artist, String album, int duration) {

        if (songID == null || title == null || title.isBlank()
                || artist == null || artist.isBlank()
                || album == null || album.isBlank()
                || duration < 0) {
            throw new IllegalArgumentException("Invalid song information");
        }

        this.songID = songID;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public Integer getSongID() {
        return songID;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "ID: " + songID
                + " | Title: " + title
                + " | Artist: " + artist
                + " | Album: " + album
                + " | Duration: " + duration + " seconds";
    }
}