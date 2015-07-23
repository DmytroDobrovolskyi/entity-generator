@ECHO OFF

call ant -f app.build.xml save-entities -Dcommand.line.args="%*"
