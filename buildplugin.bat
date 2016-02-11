
@echo off

cls

SET cwd=%cd%

echo Building plugin...

smfc --task=generate_AndroidPluginZip --path=%cwd%\plugin --playerPath=%cwd%\app-release.apk

echo Plugin ready!