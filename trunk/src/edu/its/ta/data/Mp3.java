/**
 * 
 */
package edu.its.ta.data;

/**
 * @author satria
 *
 */
public class Mp3 {

    private String title = "";
    private String genre = "";
    private String artist = "";
    private String year = "";
    private long played = 0;
    private String path = "";
    private long rsId = 0;

    public Mp3() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getPlayed() {
        return played;
    }

    public void setPlayed(long played) {
        this.played = played;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getRsId() {
        return rsId;
    }

    public void setRsId(long rsId) {
        this.rsId = rsId;
    }
    
    public void parseMp3Data() {
        
    }
}
