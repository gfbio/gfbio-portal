    @echo off
   for /f "tokens=1-4 delims=/ " %%i in ("%date%") do (
     set dow=%%i
     set month=%%j
     set day=%%k
     set year=%%l
   )
   set datestr=%month%_%day%_%year%
   echo datestr is %datestr%

   set BACKUP_FILE=C:\Users\GFBio\gfbio_portal_backup\gfbio_portaldb_%datestr%.backup
   echo backup file name is %BACKUP_FILE%
   SET PGPASSWORD=gfbio
   echo on
   "C:\Program Files\PostgreSQL\9.3\bin\pg_dump.exe" -i -h localhost -p 2222 -U postgres -F c -b -v -f %BACKUP_FILE% liferay