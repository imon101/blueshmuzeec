/**
 * 
 */
package edu.its.ta.data;

/**
 * @author satria
 *
 */
public class Userinfo {

    public static final String RS_NAME = "userinfo";

    private String nickname = "";
    private String age = "";
    private String preferenceGenre = "";
    private String preferenceArtist = "";
    private String preferenceYear = "";

    public Userinfo() {

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPreferenceGenre() {
        return preferenceGenre;
    }

    public void setPreferenceGenre(String preferenceGenre) {
        this.preferenceGenre = preferenceGenre;
    }

    public String getPreferenceArtist() {
        return preferenceArtist;
    }

    public void setPreferenceArtist(String preferenceArtist) {
        this.preferenceArtist = preferenceArtist;
    }

    public String getPreferenceYear() {
        return preferenceYear;
    }

    public void setPreferenceYear(String preferenceYear) {
        this.preferenceYear = preferenceYear;
    }

    public void parseUserinfoData(String data) {
        String keyvalue = "";
        data = data.substring(1);
        data = data.substring(0, data.length() - 1);

        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        nickname = keyvalue;

        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        age = keyvalue;

        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        preferenceGenre = keyvalue;

        data = data.substring(data.indexOf(",") + 1);
        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        preferenceArtist = keyvalue;

        keyvalue = data.substring(0, data.indexOf(","));
        keyvalue = keyvalue.substring(data.indexOf(":") + 1);
        preferenceYear = keyvalue;
    }
}
