_Temporary disclaimer: Note that this was originally hosted (and still is) at [Bitbucket](http://bitbucket.org/aerivan/fopl-project). Hopefully all branches were up-to-date locally on my machine but I have not tested this (will do though)._

"Swagger" - a DSL for specifying tournament schemes.
====================================================


Requirements
------------

The following program is most easily built using GNU Make and expects a Java 7
environment.

Please make sure that the java and javac commands are available on the PATH.


How to compile
--------------

Execute "make" in the project folder.


How to start the schema editor/viewer
-------------------------------------

Execute "make run" in the project or "src" folder.

How to start the text application
---------------------------------

Execute "make runtextapp [SOURCE=source_file]" in the "src" folder.

Presentation
------------

The slides from the presentation can be found under the "Downloads" tab

at the repository's website.


Project directory overview
--------------------------

The project is divided into several folders, the purpose of which are explained
below:

- *bnfc:* Houses the grammars and bnfc-generated files for the Swag and Play
languages.

- *example:* Holds a number of example source files and early syntax propositions
for the Swag and Play languages. Not all, but most, files are valid and parse-able.

- *lib:* Contains external libraries such as Cup and JLex. For simplicity the
libraries were included as files in the git repository.

- *src:* Houses the main implementation code for data structures, parsing and
business logic.

- *test:* Contains unit-tests for key parts of the application.

The packages located in the source directory are fairly self-explanatory but
here is an overview:

- *model:* All classes concerned with modelling the tournament.

- *parse:* Classes concerned with parsing of the tournament.

- *sets:* Set modifiers.

- *util:* Helper stuff.

- *viewer:* Hosts the code for the tournament scheme viewer.

- *bwlag:* bwlag contains the model and parse subpackages that fulfil the same
functionality of the previous similarly named packages. It also contains the
application subpackage, which contains TextApplication.java

The Swag language
-----------------

### State of the language implementation

Variables are not supported. Only Bracket-type tournaments are supported.

### Keywords supported

+ `group_by`: How many players that should meet in each faceoff. An integer or
the keyword all is accepted. Default is 2.

+ `advance`: How many players that should advance from each faceoff. An integer
or the keyword all is accepted. Default is 1.

+ `play_until`: Keep playing until we have this number of players left. An integer
or the keyword all is accepted. Default is 1.

+ `player_source`: String representing path to file containing players.

+ `reset_levels`: Each time a player advances in a bracket, its level attribute
is increased. When an incoming player already has an attribute set, he/she is
placed at the corresponding level in the bracket. This is used to implement
double elimination-style tournaments, for example. If one does not want this
behaviour, `reset_levels` should be used. This is a parameterless keyword.

+ `send_to`: Send player (one of the top `play_until` number of players) to some
subtournament. Accepts a set modifier and a string representing the subtournament.
Note that several send_to statements are allowed and all of them will be executed.
It is the user's responsibility to make sure they do not overlap (i.e. tries to
send a player to more than one subtournament) - that would cause undefined
behaviour.

+ `send_to_per_node`: Send players from each faceoff to a subtournament. This
is like `send_to` but applied to each faceoff. Note that the advancing players
(as specified by `advance`) may not be sent anywhere using this statement.
Several statements are allowed, with the same restrictions as for `send_to`.
Accepts a set_modifier and a string representing the subtournament.

+ `send_losers_to`: Special case of send_to_per_node. Will send the losers of
each node (the bottom `group_by-advance` players) to a subtournament. Only one
statement allowed. Takes a string representing the subtournament as parameter.

### Set modifiers

top: `top integer set1`

bottom: `bottom integer set1`

union: `[set1,set2,...]`

set difference: `set1 \ set2`

intersection: `set1 & set2`

intersection complement: `set1 | set2`

attribute existence: `set1.attribute`

attribute value: `set.attribute operator number` where operator is one of
<, <=, >, >=, ==, !=

In conjunction with set modifiers, the reserved word `all` represents the entire
set in the context. For example `bottom 2 all` used with a `send_to_per_node`
statement will take the bottom two players of each node.

Note that the order of subtournaments in the source file is significant. If
subtournament A sends players to subtournament B, A must be declared before B
in the source file. This is because the compiler will initialize all subtournaments
before returning the tournament data structure in order to establish edges in the
tournament graph.

Additionally, the generated tournament data structure preserves the order of
declaration of subtournaments, which may be used by applications.

The Play language
-----------------

The play language is simply a list of players coupled with their attributes,
delimited by newlines. The player entries are written in the following form:

<player name> = <attribute>, <attribute>, … , <attribute>

An attribute can either be written as:

<attribute name>

or

<attribute name>:<value>

where <value> can be any (integer or decimal) number, or a string encased in
double quotes.

About brackets
--------------

If brackets are not well-formed, the behaviour is unspecified.

Brackets are well-formed if:

- All advancing (as specified by `advance`) players of a node are sent to exactly
one node.

- `group_by`, `advance` and `play_until` have sensible values. A non-sensible
value would be `advance > group_by`, for example.

- All players entering a bracket must have a level attribute that is not “outside”
of the bracket.

If the number of players entering the bracket at any level is not divisible by
`group_by`, the "dangling" players will be placed in the bracket but with fewer
competitors in their faceoff(s). It might even be the case that they advance some
number of rounds without ever being in danger of elimination.


Bwlag
-----

The Bwlag language is an extension of the general Swag language. It features
bowling-specific stuff such as ranking of players and more specific printing of
players, and also a new subtournament type: `open_play`. An `open_play`-style
tournament is a bracket with only one faceoff, where all advances. It suports the
`send_to` and `play_until` statements, with the same semantics as for brackets.

### Text viewer

There is a text application allowing you to run tournaments and interactively
enter results. The progress of the tournament (meaning the advancement of players)
will be continuously updated and displayed.

The Text viewer is a good example of how an end application interacts with the
framework.

Note two things:

- Bracket tournaments have an extra node after the faceoffs, that gathers all
the `play_until` winners to allow ordering of them and sending them to different
subtournaments.

- Because of the Observer/Observable pattern, things might appear to happen out
of turn. In particular, the update from the extra bracket node will be received
before the update of the last regular faceoff node.


Tournament scheme viewer
------------------------

Within the project we’ve built a basic tool for developing tournament schemes.
Currently, it allows you to enter Swag source code, parse it and display a
representation of the generated data structure. The display uses vector graphics.
The work to implement visual representations of all possible tournaments is at
a very early stage and currently only supports basic single bracket-tournaments.
