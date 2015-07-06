@ECHO OFF

call ant -f ../build.xml run-app -Dcommand.line.args="%*"
