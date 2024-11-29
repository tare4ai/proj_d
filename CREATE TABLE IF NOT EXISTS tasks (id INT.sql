CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                time TEXT NOT NULL,
                active BOOLEAN NOT NULL);