    @echo off
   for /f "tokens=1-4 delims=/ " %%i in ("%date%") do (
     set dow=%%i
     set month=%%j
     set day=%%k
     set year=%%l
   )
   set datestr=%month%_%day%_%year%
"C:\Program Files (x86)\7-Zip\7z.exe" a -tzip "C:\Users\GFBio\gfbio_portal_backup\gfbio_portal_backup_%datestr%.zip" "C:\liferay-portal-6.2.0-ce-ga1\data\document_library"
"C:\Program Files (x86)\7-Zip\7z.exe" a -tzip "C:\Users\GFBio\gfbio_portal_backup\gfbio_portal_backup_%datestr%.zip" "C:\liferay-portal-6.2.0-ce-ga1\portal-ext.properties"

CALL "C:\Users\GFBio\gfbio_portal_backup\db_dump.bat"

"C:\Program Files (x86)\7-Zip\7z.exe" a -tzip "C:\Users\GFBio\gfbio_portal_backup\gfbio_portal_backup_%datestr%.zip" "C:\Users\GFBio\gfbio_portal_backup\gfbio_portaldb_%datestr%.backup"
del "C:\Users\GFBio\gfbio_portal_backup\gfbio_portaldb_%datestr%.backup"
