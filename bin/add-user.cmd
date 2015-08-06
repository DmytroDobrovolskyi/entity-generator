@ECHO OFF

call ant -f app.build.xml add-user -Dcommand.line.args="%*"
