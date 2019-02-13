/*
 *	Info:			This is just an incremental update from v13.0 to v14.0!
 *	
 *	Description:	There is just one modification between v13.0 and v14.0.
 *					This document should be used manually, so that you are able
 *					to use the rollback (if something goes wrong) or commit (if
 *					the update was successful).
 *
 *	How To:			Just copy & paste the statements into the query window of 
 *					PostgreSQL. Afterwards use commit (to persist the changes) or
 *					role them back (by using rollback transaction).
 */
 
BEGIN TRANSACTION;  
-- ROLLBACK TRANSACTION;

ALTER TABLE gfbio_datamanagementplan
   ALTER COLUMN name TYPE character varying(255);
   
-- COMMIT;