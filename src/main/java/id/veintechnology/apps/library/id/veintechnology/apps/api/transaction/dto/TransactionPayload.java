package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

public class TransactionPayload {

    @NotNull
    private ZonedDateTime borrowedTime;

    @NotNull
    private List<TransactionPayloadBook> bookCodes;

    public TransactionPayload() {
    }

    public ZonedDateTime getBorrowedTime() {
        return borrowedTime;
    }

    public void setBorrowedTime(ZonedDateTime borrowedTime) {
        this.borrowedTime = borrowedTime;
    }

    public List<TransactionPayloadBook> getBookCodes() {
        return bookCodes;
    }

    public void setBookCodes(List<TransactionPayloadBook> bookCodes) {
        this.bookCodes = bookCodes;
    }
}
