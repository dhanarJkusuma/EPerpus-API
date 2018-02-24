CREATE TABLE authentication_user (
  id SERIAL,
  username character varying(50) NOT NULL,
  password character varying(100) NOT NULL,
  status character varying(20) NOT NULL DEFAULT USER,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX authentication_user_idx_username ON authentication_user (username);

INSERT INTO authentication_user (username, password, status)
VALUES ('veinAdmin', '$2a$10$oD2gBWNlyzZthhaxnVBN2u2qBtm6C.BNxYLd2QNb6rSnqhvWvPViy', 'ADMIN');

CREATE TABLE authentication_token (
  id SERIAL,

  auth_id BIGINT NOT NULL,
  token char(40) NOT NULL,
  expired_at TIMESTAMP WITH TIME ZONE NOT NULL,

  created_on TIMESTAMP WITH TIME ZONE NOT NULL,
  last_accessed_on TIMESTAMP WITH TIME ZONE NOT NULL,

  PRIMARY KEY (id),
  CONSTRAINT fk_authentication_token_auth_idx
              foreign key (auth_id)
              references authentication_user (id)
);

CREATE TABLE user_member (
    id SERIAL,
    public_id character varying(20) NOT NULL,
    auth_id BIGINT NOT NULL,
    name character varying(50) NOT NULL,
    gender character varying(10) NOT NULL,
    phone character varying(15) NOT NULL,
    address text NOT NULL,
    email character varying(50),
    company character varying(50),
    employee_no character varying(60),
    work_unit character varying(50),
    created_on TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_user_member_auth_idx
                  foreign key (auth_id)
                  references authentication_user (id)
);

CREATE TABLE category(
    id SERIAL,
    code character varying(30) NOT NULL,
    name character varying(50) NOT NULL,
    created_on TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE book(
    id SERIAL,
    code character varying(20) NOT NULL,
    title character varying(100) NOT NULL,
    author character varying(50),
    editor character varying(50),
    publisher character varying(50),
    year character varying(4),
    created_on TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE category_book (
    id SERIAL,
    category_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_category_book_idx_category
                      foreign key (category_id)
                      references category (id),
    CONSTRAINT fk_category_book_idx_book
                          foreign key (book_id)
                          references book (id)
);

CREATE TABLE order_transaction (
    id SERIAL,
    public_id character varying(15) NOT NULL,
    borrow_date TIMESTAMP WITH TIME ZONE NOT NULL,
    return_date TIMESTAMP WITH TIME ZONE,
    member_id BIGINT NOT NULL,


    PRIMARY KEY (id),
    CONSTRAINT fk_order_transaction_member_idx
                             foreign key (member_id)
                             references user_member (id)
);

CREATE TABLE order_transaction_item(
    id SERIAL,
    order_transaction_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_order_transaction_item_trx_idx
                                 foreign key (order_transaction_id)
                                 references order_transaction (id)
);

CREATE TABLE activity_log(
    id SERIAL,
    message TEXT NOT NULL,
    created_on TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (id)
);
