--// First migration.
-- Migration SQL that makes the change goes here.
CREATE SEQUENCE STRUTS2_SEQUENCE
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;


--//@UNDO
-- SQL to undo the change goes here.
DROP SEQUENCE STRUTS2_SEQUENCE;

