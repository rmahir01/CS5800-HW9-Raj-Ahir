//CS5800 - HW9 - PROXY
//RAJ AHIR - 019330454

package proxy;

public class ProxyDriver {

    public static void main(String[] args) {

        SongService realService = new RealSongService();

        SongService musicService =
                new SongServiceProxy(realService);

        System.out.println("=== SEARCH BY SONG ID ===");
        System.out.println(musicService.searchById(1));

        System.out.println("\n=== SEARCH SAME ID AGAIN ===");
        System.out.println(musicService.searchById(1));

        System.out.println("\n=== SEARCH BY TITLE ===");
        musicService.searchByTitle("Kesariya").forEach(System.out::println);

        System.out.println("\n=== SEARCH SAME TITLE AGAIN ===");
        musicService.searchByTitle("Kesariya").forEach(System.out::println);

        System.out.println("\n=== SEARCH BY ALBUM ===");
        musicService.searchByAlbum("Brahmastra").forEach(System.out::println);

        System.out.println("\n=== SEARCH SAME ALBUM AGAIN ===");
        musicService.searchByAlbum("Brahmastra").forEach(System.out::println);

        System.out.println("\n=== SEARCH ANOTHER SONG ===");
        System.out.println(musicService.searchById(5));

        System.out.println("\n=== SEARCH ANOTHER ALBUM ===");
        musicService.searchByAlbum("Single").forEach(System.out::println);
    }
}