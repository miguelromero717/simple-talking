CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL    NOT NULL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    nickname   TEXT      NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS messages
(
    id SERIAL NOT NULL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sender_id INT NOT NULL REFERENCES users (id),
    receiver_id INT NOT NULL REFERENCES users (id),
    payload TEXT NOT NULL,
    CONSTRAINT check_sender_recipient CHECK (sender_id <> receiver_id)
);
