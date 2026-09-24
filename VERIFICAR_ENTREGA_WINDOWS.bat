@echo off
setlocal
echo Verificando PréstamoLab CTMA...
call gradlew.bat testDebugUnitTest lintDebug assembleDebug
if errorlevel 1 (
  echo.
  echo ERROR: revisa el detalle anterior. No tomes capturas como PASS.
  exit /b 1
)
echo.
echo VERIFICACION COMPLETADA: unit tests, lint y APK debug.
echo APK: app\build\outputs\apk\debug\app-debug.apk
endlocal
