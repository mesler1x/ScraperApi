CREATE TABLE IF NOT EXISTS site
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name       BIGINT NOT NULL,
    url        TEXT   NOT NULL,
    created_at BIGINT DEFAULT (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)
);

CREATE TABLE IF NOT EXISTS publication
(
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title   TEXT,
    text    TEXT NOT NULL,
    url     TEXT,
    author  TEXT,
    site_id UUID,
    created_at BIGINT DEFAULT (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000),
        FOREIGN KEY (site_id) REFERENCES site(id)
);

CREATE TABLE IF NOT EXISTS summary
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    publication_id UUID NOT NULL,
    summary        TEXT NOT NULL,
    created_at BIGINT DEFAULT (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000),
        FOREIGN KEY (publication_id) REFERENCES publication(id)
);