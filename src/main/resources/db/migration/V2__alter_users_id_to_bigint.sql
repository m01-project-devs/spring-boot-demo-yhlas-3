-- Align users.id with JPA Long (BIGINT)
ALTER TABLE users
    ALTER COLUMN id TYPE BIGINT;