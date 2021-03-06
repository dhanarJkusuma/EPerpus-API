package id.veintechnology.apps.library.id.veintechnology.apps.api.book;

import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.BookDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CategoryDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CreateBookPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.UpdateBookPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookMapper {
    public static Book toBookDao(CreateBookPayload payload){
        return Book.Builder.newBuilder()
                .title(payload.getTitle())
                .author(payload.getAuthor())
                .code(payload.getCode())
                .year(payload.getYear())
                .stock(payload.getTotalStock())
                .totalStock(payload.getTotalStock())
                .editor(payload.getEditor())
                .publisher(payload.getPublisher())
                .build();
    }

    public static Book toBookDaoCreate(CreateBookPayload payload){
        // populate categoryCode to category
        List<Category> categories = payload.getCategoryCode().stream().map(Category::createByCode).collect(Collectors.toList());
        return Book.Builder.newBuilder()
                .title(payload.getTitle())
                .author(payload.getAuthor())
                .code(payload.getCode())
                .year(payload.getYear())
                .stock(payload.getTotalStock())
                .totalStock(payload.getTotalStock())
                .editor(payload.getEditor())
                .publisher(payload.getPublisher())
                .build();
    }

    public static Book toBookDao(Book book, UpdateBookPayload payload){
        book.setTitle(payload.getTitle());
        book.setAuthor(payload.getAuthor());
        book.setYear(payload.getYear());
        book.setTotalStock(payload.getTotalStock());
        book.setEditor(payload.getEditor());
        book.setPublisher(payload.getPublisher());
        return book;
    }

    public static BookDto toBookDto(Book book){
        Set<CategoryDto> categoryDtoSet = book.getCategories().stream().map(CategoryMapper::mapToCategoryDto).collect(Collectors.toSet());
        return BookDto.Builder.newBuilder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .code(book.getCode())
                .editor(book.getEditor())
                .publisher(book.getPublisher())
                .year(book.getYear())
                .stock(book.getStock())
                .totalStock(book.getTotalStock())
                .coverImage(book.getCoverImage())
                .category(categoryDtoSet)
                .createdOn(book.getCreatedOn())
                .build();
    }
}
