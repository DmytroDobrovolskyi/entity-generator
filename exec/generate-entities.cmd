@ECHO OFF

call ant -f ../build.xml generate-entities -Dcommand.line.args="%*"
