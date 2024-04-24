CREATE TABLE accounts
(
    id              UUID             NOT NULL,
    user_id         UUID             NOT NULL,
    description     VARCHAR(255)     NOT NULL,
    currency        VARCHAR(255)     NOT NULL,
    initial_balance DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE users
(
    id UUID NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);