package id.veintechnology.apps.library.id.veintechnology.apps.api.transaction;

import id.veintechnology.apps.library.id.veintechnology.apps.api.transaction.dto.*;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionMapper {

    public static TransactionBookDto toTransactionBookDto(OrderTransactionItem item){
        return TransactionBookDto.Builder.newBuilder()
                .code(item.getBookCode())
                .title(item.getBookTitle())
                .author(item.getBookAuthor())
                .editor(item.getBookEditor())
                .publisher(item.getBookPublisher())
                .year(item.getBookYear())
                .quantity(item.getQuantity())
                .categories(item.getBookCategories())
                .build();
    }

    public static TransactionMemberDto toTransactionMemberDto(Member member){
        return TransactionMemberDto.Builder.newBuilder()
                .publicId(member.getPublicId())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .address(member.getAddress())
                .company(member.getCompany())
                .build();

    }

    public static TransactionDto toTransactionDto(OrderTransaction transaction){
        List<TransactionBookDto> bookDtos = new ArrayList<>();
        transaction.getItems().forEach(item -> bookDtos.add(toTransactionBookDto(item)));
        TransactionDto.Builder builder = TransactionDto.Builder.newBuilder();
        if(transaction.getReturnDate() != null){
            builder.returnDate(parseDate(transaction.getReturnDate()));
        }
        return builder.borrowDate(parseDate(transaction.getBorrowDate()))
                .createdOn(parseDate(transaction.getCreatedOn()))
                .member(toTransactionMemberDto(transaction.getMember()))
                .publicId(transaction.getPublicId())
                .books(bookDtos)
                .isApproved(transaction.getIsApproved())
                .build();
    }

    private static ZonedDateTime parseDate(Date date){
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static OrderTransaction toOrderTransaction(TransactionPayload payload, Member member, List<Book> books){
        Date borrowDate = Date.from(payload.getBorrowedTime().toInstant());
        OrderTransaction transaction =  OrderTransaction.Builder.newBuilder()
                .borrowDate(borrowDate)
                .member(member)
                .isApproved(false)
                .build();
        Map<String, Integer> stocks = new HashMap<>();
        payload.getBookCodes().stream().forEach(s -> {
            stocks.put(s.getBookCode(), s.getQuantity());
        });
        List<OrderTransactionItem> items = books.stream().map(b -> newTransactionFromBook(b, stocks.get(b.getCode()),transaction)) .collect(Collectors.toList());
        transaction.setItems(items);
        return transaction;
    }

    private static OrderTransactionItem newTransactionFromBook(Book book, int quantity, OrderTransaction transaction){
        List<String> categories = book.getCategories().stream().map(Category::getName).collect(Collectors.toList());
        return OrderTransactionItem.Builder
                .newBuilder()
                .bookId(book.getId())
                .bookCode(book.getCode())
                .bookTitle(book.getTitle())
                .bookAuthor(book.getAuthor())
                .bookEditor(book.getEditor())
                .bookPublisher(book.getPublisher())
                .bookYear(book.getYear())
                .bookCategories(String.join(",", categories))
                .quantity(quantity)
                .transaction(transaction)
                .build();
    }

}
