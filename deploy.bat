@echo off
set DIR="0 - deploy"
set ONE_JAR=one-jar-0.97.jar
set JAR=netmemo.jar
set MANIFEST=MANIFEST.MF
set LIST=config images META-INF net LICENSE.txt

rmdir /S /Q %DIR%
mkdir %DIR%
cd %DIR%

copy ..\one-jar\%MANIFEST% .
copy /b ..\one-jar\%ONE_JAR% .
jar xf %ONE_JAR%

rmdir /S /Q main lib
mkdir main lib images

copy /b ..\images\netmemo-u-015.png images
copy /b ..\lib\jnetparse.jar lib
copy /b ..\%JAR% main

cd main
jar xf %JAR%
rmdir /S /Q lib
del /S %JAR%
jar cfM %JAR% %LIST%
cd ..
jar cfm %JAR% %MANIFEST% images main lib com doc .version
copy /Y %JAR% ..
cd ..

set DIR=
set ONE_JAR=
set JAR=
set MANIFEST=
set LIST=
@echo on
