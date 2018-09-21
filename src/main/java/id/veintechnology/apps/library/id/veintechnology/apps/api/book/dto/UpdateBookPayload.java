package id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class UpdateBookPayload {

    @NotNull
    private String title;

    private String author;

    private String editor;

    private String publisher;

    private int year;

    private int stock;

    private int totalStock;

    private Set<String> categoryCode;

    public UpdateBookPayload() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public Set<String> getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Set<String> categoryCode) {
        this.categoryCode = categoryCode;
    }
}
