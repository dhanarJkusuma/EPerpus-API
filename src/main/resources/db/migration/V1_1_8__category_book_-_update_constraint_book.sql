ALTER TABLE category_book
DROP CONSTRAINT fk_category_book_idx_book,
ADD CONSTRAINT fk_category_book_idx_book
   FOREIGN KEY (book_id)
   REFERENCES book(id)
   ON DELETE CASCADE;