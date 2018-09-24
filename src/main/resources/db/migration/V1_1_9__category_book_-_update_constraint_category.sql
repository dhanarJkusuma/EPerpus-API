ALTER TABLE category_book
DROP CONSTRAINT fk_category_book_idx_category,
ADD CONSTRAINT fk_category_book_idx_category
   FOREIGN KEY (category_id)
   REFERENCES category(id)
   ON DELETE CASCADE;