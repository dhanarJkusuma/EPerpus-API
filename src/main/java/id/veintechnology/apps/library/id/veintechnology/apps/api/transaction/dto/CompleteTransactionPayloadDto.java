package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class CompleteTransactionPayloadDto {
    @NotNull
    private ZonedDateTime returnDate;

    public CompleteTransactionPayloadDto() {
    }

    public CompleteTransactionPayloadDto(ZonedDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public ZonedDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(ZonedDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
