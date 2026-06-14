@echo off
chcp 65001 > nul
if not exist out mkdir out
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ASCII sources.txt"
javac -encoding UTF-8 -d out @sources.txt
if errorlevel 1 (
    echo Compile failed.
    exit /b 1
)
java -cp out agentic.testing.framework.Main
