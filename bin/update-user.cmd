@ECHO OFF

call ant -f app.build.xml update-user -Dcommand.line.args="%*"
