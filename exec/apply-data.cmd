@ECHO OFF

call ant -f app.build.xml generate-entities -Dcommand.line.args="%*"
