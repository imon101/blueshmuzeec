/**
 * 
 */
package edu.its.ta.data;

/**
 * @author satria
 *
 */
public class Playlist {

    public static final String RS_NAME = "playlist";

    private String title = "";
    private String album = "";
    private String artist = "";
    private String genre = "";
    private String year = "";
    private String path = "";
    private long duration = 0;
    
    public Playlist() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void parsePlaylistData(String data) {
        String keyvalue = "";
        data = data.substring(1);
        data = data.substring(0, data.length() - 1);

        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        title = keyvalue;
        
        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        artist = keyvalue;
        
        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        genre = keyvalue;
        
        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        year = keyvalue;
        
        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        album = keyvalue;
        
        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        duration = Long.parseLong(keyvalue);
        
        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0);
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        path = keyvalue;
    }
}
