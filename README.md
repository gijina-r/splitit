
# SplitIt Expense Sharing Application

SplitIt is a **Spring Boot WebFlux application** for managing shared expenses among users and groups.  
It supports **user registration, group management, expense tracking, and settlement calculation**.  
OAuth2 (Google login) is integrated to authenticate users.
![img.png](img.png)
---
---
config:
layout: dagre
---
erDiagram
USER ||..|{ GROUP_USER : "part of"
USER{
int id
string         username
string email
string password_hash
timestamp created_at
timestamp updated_at
timestamp update_user_modtime

    }
    GROUP_USER{
       int id
       string name
       string description
    string created_by
       timestamp created_at
        timestamp updated_at
        timestamp update_group_modtime
    }

    USER ||--|{ EXPENSES : places
    EXPENSES{
        int id
        int  group_id
        int payer_id
        double amount
        string description
        date date
        timestamp created_at
        timestamp updated_at
        int category_id
        timestamp update_expense_modtime
    }

    USER }|--|{ EXPENSE_SPLITS : "liable for"
    EXPENSE_SPLITS{
        int id
        int expense_id
        int user_id
        int group_id
        timestamp created_at
        timestamp updated_at
    }
     GROUP_USER }|--|{ EXPENSE_SPLITS : "liable for"
    GROUP_USER ||--|{ EXPENSES : "part of"

## 🧱 Database Setup (MySQL)

Create a database and run the following SQL scripts:

```sql
CREATE DATABASE splitit_db;

USE splitit_db;

-- Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);

-- group_user Table
CREATE TABLE group_user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_by INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_group_user_created_by ON group_user(created_by);

-- User_Group Table (for many-to-many relationship between users and group_user)
CREATE TABLE user_group (
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    group_id INTEGER REFERENCES group_user(id) ON DELETE CASCADE,
    role VARCHAR(20) DEFAULT 'MEMBER',
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, group_id)
);

CREATE INDEX idx_user_group_user_id ON user_group(user_id);
CREATE INDEX idx_user_group_group_id ON user_group(group_id);

-- Expenses Table
CREATE TABLE expenses (
    id SERIAL PRIMARY KEY,
    group_id INTEGER REFERENCES group_user(id) ON DELETE CASCADE,
    payer_id INTEGER REFERENCES users(id),
    amount DECIMAL(10, 2) NOT NULL,
    description TEXT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_expenses_group_id ON expenses(group_id);
CREATE INDEX idx_expenses_payer_id ON expenses(payer_id);

-- Expense_Splits Table
CREATE TABLE expense_splits (
    id SERIAL PRIMARY KEY,
    expense_id INTEGER REFERENCES expenses(id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(id),
    amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_expense_splits_expense_id ON expense_splits(expense_id);
CREATE INDEX idx_expense_splits_user_id ON expense_splits(user_id);
DROP TABLE settlements;  
-- Settlements Table
CREATE TABLE settlements (
    id SERIAL PRIMARY KEY,
    payer_id INTEGER REFERENCES users(id),
    payee_id INTEGER REFERENCES users(id),
    expense_id INTEGER REFERENCES expenses(id),
    amount DECIMAL(10, 2) NOT NULL,
    group_id INTEGER REFERENCES group_user(id),
    settled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_settlements_payer_id ON settlements(payer_id);
CREATE INDEX idx_settlements_payee_id ON settlements(payee_id);
CREATE INDEX idx_settlements_group_id ON settlements(group_id);

-- Categories Table
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add category_id to Expenses Table
ALTER TABLE expenses
ADD COLUMN category_id INTEGER REFERENCES categories(id);

CREATE INDEX idx_expenses_category_id ON expenses(category_id);

ALTER TABLE categories
MODIFY COLUMN update_category_modtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE settlements
MODIFY COLUMN update_settlement_modtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE expense_splits
MODIFY COLUMN update_expense_split_modtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE expenses
MODIFY COLUMN update_expense_modtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE group_user
MODIFY COLUMN update_group_modtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE users
MODIFY COLUMN update_user_modtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
COMMIT;

```

### Sample Data

```sql
-- USERS
INSERT INTO users (username, email, password) VALUES
('Gijina', 'gijina@example.com', 'pass123'),
('Anu', 'anu@example.com', 'pass123'),
('Rahul', 'rahul@example.com', 'pass123');

-- GROUPS
INSERT INTO groups (name) VALUES ('Dubai Trip');

-- GROUP MEMBERS
INSERT INTO group_users (group_id, user_id) VALUES
(1,1),(1,2),(1,3);

-- EXPENSES
INSERT INTO expenses (description, amount, paid_by, group_id) VALUES
('Dinner', 150, 1, 1),
('Cab Ride', 90, 2, 1),
('Hotel Room', 300, 3, 1);

-- EXPENSE SPLIT
INSERT INTO expense_split (expense_id, user_id) VALUES
(1,1),(1,2),(1,3),
(2,1),(2,2),(2,3),
(3,1),(3,2),(3,3);
```

... (rest of README omitted for brevity, include the full text from previous step)
