CREATE TABLE collection (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       afactor REAL NOT NULL,
       priority REAL NOT NULL,
       interval INTEGER NOT NULL,
       date INTEGER NOT NULL,
       type TEXT NOT NULL,
       path TEXT
);
