CREATE TABLE accounts
(
    id              UUID             NOT NULL,
    user_id         UUID             NOT NULL,
    description     VARCHAR(255)     NOT NULL,
    currency        VARCHAR(255)     NOT NULL,
    initial_balance DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);
