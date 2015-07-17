@ECHO OFF

call ant -f ../build.xml save-entities -Dcommand.line.args="%*"
