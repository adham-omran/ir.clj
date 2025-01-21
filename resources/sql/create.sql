CREATE TABLE collection (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       afactor REAL NOT NULL,
       priority REAL NOT NULL,
       interval INTEGER NOT NULL,
       date INTEGER NOT NULL,
       kind TEXT NOT NULL,
       content TEXT NOT NULL
);

-- When an item has elements created from it, they are children of it.

CREATE TABLE collection_relationships (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    parent_id INTEGER NOT NULL,
    child_id INTEGER NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES collection(id),
    FOREIGN KEY (child_id) REFERENCES collection(id),
    -- Prevent duplicates and circular references
    UNIQUE(parent_id, child_id),
    CHECK (parent_id != child_id)
);

-- It's recommended to create indexes for the foreign keys
CREATE INDEX idx_parent ON collection_relationships(parent_id);
CREATE INDEX idx_child ON collection_relationships(child_id);
