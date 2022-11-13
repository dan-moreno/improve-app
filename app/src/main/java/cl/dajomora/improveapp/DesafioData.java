package cl.dajomora.improveapp;

public class DesafioData {
    String title;
    String description;
    String details;
    int imgId;

    DesafioData(String title, String description, int imgId, String details) {
        this.title = title;
        this.description = description;
        this.imgId = imgId;
        this.details = details;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
