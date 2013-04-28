"Swagger" - a DSL for specifying tournament schemes.
====================================================

Git-conventions
---------------
Branch to separate feature-branch while developing. One branch per feature. Keep the master-branch in a working state at all times. Should we ever need to present our product the master branch should contain only completed features.

How to compile PlayerParser
---------------------------
In the bnfc/Play folder, first run "make" and then run "javac -cp .:$CLASSPATH:../../impl Play/PlayerParser.java"
