ALTER TABLE order_transaction_item
DROP CONSTRAINT fk_order_transaction_item_trx_idx;

ALTER TABLE order_transaction_item
ADD book_code character varying(20),
ADD book_title character varying(100),
ADD book_author character varying(50),
ADD book_editor character varying(50),
ADD book_publisher character varying(50),
ADD book_year character varying(4),
ADD book_categories character varying(150);