@echo off 
    setlocal enableextensions disabledelayedexpansion
	
    set "search=//gfbio-db1.inf-bb.uni-jena.de/gfbioDev"
    set "replace=//gfbio-db1.inf-bb.uni-jena.de/gfbioGA4"
	
	for %%f in (*.xml) do (
		echo %%f
		
		for /f "delims=" %%i in ('type "%%f" ^& break ^> "%%f" ') do (
			set "line=%%i"
			setlocal enabledelayedexpansion
			set "line=!line:%search%=%replace%!"
			>>"%%f" echo(!line!
			endlocal
		)
	)