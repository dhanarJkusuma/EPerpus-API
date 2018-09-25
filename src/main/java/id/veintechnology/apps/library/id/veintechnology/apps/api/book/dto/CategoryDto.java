package id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CategoryDto {

    private String code;

    @NotNull(message = "category cannot be null.")
    private String category;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdOn;

    public CategoryDto() {
    }

    public CategoryDto(String code, String category, Date createdOn) {
        this.code = code;
        this.category = category;
        this.createdOn = createdOn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
