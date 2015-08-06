@ECHO OFF

call  ant -f app.build.xml delete-user -Dcommand.line.args="%*"
