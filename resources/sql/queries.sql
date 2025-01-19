-- Find all children of a specific item
SELECT c.*
FROM collection c
JOIN collection_relationships cr ON c.id = cr.child_id
WHERE cr.parent_id = ?;

-- Find the parent of a specific item
SELECT c.*
FROM collection c
JOIN collection_relationships cr ON c.id = cr.parent_id
WHERE cr.child_id = ?;
