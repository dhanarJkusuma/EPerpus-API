package id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateCategoryPayload {

    @NotNull(message = "category cannot be null.")
    @NotEmpty(message = "category cannot be empty.")
    private String category;

    public CreateCategoryPayload() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
