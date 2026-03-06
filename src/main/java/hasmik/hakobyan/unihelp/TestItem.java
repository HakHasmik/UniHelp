package hasmik.hakobyan.unihelp;

import java.io.Serializable;

public class TestItem implements Serializable {
    private String header;
    private String main_text;
    private int image, likedIcon;
    private boolean isLiked;
    private int id;

    public TestItem(int id,String main_text, String header, int image, int likedIcon) {
        this.id = id;
        this.main_text = main_text;
        this.header = header;
        this.image = image;
        this.likedIcon = likedIcon;
        this.isLiked = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikedIcon() {
        return likedIcon;
    }

    public void setLikedIcon(int likedIcon) {
        this.likedIcon = likedIcon;
    }

    public int getImage() {
        return image;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public String getMain_text() {
        return main_text;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setMain_text(String main_text) {
        this.main_text = main_text;
    }
}
