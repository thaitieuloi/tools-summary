@echo off
REM Database Migration Script for Windows
REM Usage: migrate.bat [profile]

setlocal

set PROFILE=%1
if "%PROFILE%"=="" set PROFILE=default

echo ============================================================
echo Running Database Migration
echo Profile: %PROFILE%
echo ============================================================

cd /d "%~dp0..\.."

if "%PROFILE%"=="default" (
    call gradlew.bat :migrated-db:migrate
) else (
    call gradlew.bat :migrated-db:migrate -Pprofile=%PROFILE%
)

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ============================================================
    echo Migration FAILED!
    echo ============================================================
    exit /b 1
)

echo.
echo ============================================================
echo Migration completed successfully!
echo ============================================================

endlocal
