package de.charlestons_inn.lehrkraftnews;

/**
 * Created by steven on 16.04.15.
 */
public class LehrkraftnewsEntry {
    private String validityDate;
    private String source;
    private String message;

    public LehrkraftnewsEntry() {

    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
