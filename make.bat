@echo off

REM JAVA root package: net.sf.netmemo
REM set LIBS=libs/ganymed-ssh2-build251beta1.jar;libs/tools.jar
set LIBS=lib/jnetparse.jar
set BASE=src\net\sf\netmemo
set GUI=%BASE%\gui
set VENDORS=vendors
set CISCO=%VENDORS%\cisco
set DEST=. 
REM set ACTIONS=%GUI%\AboutAction.java 
set ACTIONS=%GUI%\ExitAction.java %GUI%\CheckForUpdateAction.java
set CLASSES=%ACTIONS%
set CLASSES= %CLASSES% %BASE%\%CISCO%\ConfigRegister.java
REM set CLASSES=%CLASSES% %BASE%\Launcher.java %GUI%\%CISCO%\ConfigRegisterPanel.java %GUI%\NetMemo.java
set CLASSES=%CLASSES% %BASE%\Launcher.java %GUI%\ConfigRegisterPanel.java %GUI%\NetMemo.java

set EXCLUDE=update-libs.bat
set INCLUDE=net images config lib LICENSE.txt

@echo on

javac -Xlint:unchecked -Xlint:deprecation -d %DEST% -classpath %LIBS% %CLASSES%
@echo off
move lib\%EXCLUDE% .
@echo on
jar cfm netmemo.jar manifest.mf %INCLUDE%
REM jar i netmemo.jar
move %EXCLUDE% lib\

@echo off
set DEST=
set ACTIONS=
set CLASSES=
set EXCLUDE=
set INCLUDE=
set LIBS=
set BASE=
set GUI=
set VENDORS=
set CISCO=

@echo on