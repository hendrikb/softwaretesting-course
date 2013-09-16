Aufgabe 1) 
a) Bestimmen Sie konkrete Testfälle zur vollständigen Anweisungsüberdeckung und erläutern Sie, warum eine vollständige Anweisungsüberdeckung erreicht wird.

Zur vollständigen Anweisungsüberdeckung müssen alle Anweisungen mindestens einmal ausgeführt werden.
Die beiden Definitionen werden immer ausgeführt, brauchen also keinen besonderen Test.
Für die 3 if-elseif-else-Zweige muss je eine bestimmte Bedingung gelten.
Für den ersten Zweig: (alphabet[mitte] < zeichen)
Für den zweiten: (alphabet[mitte] > zeichen)
Und für den dritten alphabet[mitte] == zeichen
Das letzte return wird nur ausgeführt, wenn niemals alphabet[mitte] == zeichen

Eingabe: z.B. ('e', {'a', 'b', 'c', 'd', 'e', 'f', 'g'}) => (4)
-> 1. while wird ausgeführt
-> 2. alphabet[mitte] = 'd' => {'d' < 'e'} der erste Zweig wird ausgeführt
-> 3. alphabet[mitte] = 'f' => {'f' > 'e'} der zweite Zweig wird ausgeführt
-> 4. alphabet[mitte] = 'e' => {'e' == 'e'} der dritte Zweig wird ausgeführt
-> es fehlt nur noch das return -1

Eingabe: z.B. ('e', {}) => (-1)
-> 1. while wird nicht ausgeführt
-> 2. return -1

Damit wurden alle 10 Anweisungen wenigstens einmal ausgeführt.

b) Bestimmen Sie konkrete Testfälle zur vollständigen Zweigüberdeckung und erläutern Sie, warum eine vollständige Zweigüberdeckung erreicht wird.

Es gibt 3 Entscheidungen und damit 6 Entscheidungsergebnisse:
   1 erstes <= zweites
   2 alphabet[mitte] < zeichen
   3 alphabet[mitte] > zeichen

Um alle diese Entscheidungsergebnisse herbeizuführen sind folgende Testfälle denkbar:
(siehe oben (^^;))
Der erste Testfall führt zu:
    1 positiv (check)
    2 positiv (check)
    1 positiv
    2 negativ (check)
    3 positiv (check)
    1 positiv
    2 negativ
    3 negativ (check)

also 5 Entscheidungsergebnissen.

Der zweite Testfall führt dann zu:
    1 negativ (check)

also dem 6. Entscheidungsergebnis.

Dies entspricht einer Entscheidungsübedeckung von 100%.

c) Bestimmen Sie alle du-Ketten in suche.
1 def(zeichen, alphabet)
2 def(erstes)
3 def(letztes)
4 p-use(erstes, letztes)
5 def(mitte), c-use(erstes, letztes)
6 p-use(alphabet, mitte, zeichen)
7 def(erstes), c-use(mitte)
8 p-use(alphabet, mitte, zeichen)
9 def(letztes), c-use(mitte)
11 c-use(mitte)

Ketten
Nr.	Zeile Kette
1	1 def(zeichen): p-use(zeichen) 6,8
2	1 def(alphabet): p-use(alphabet) 6,8
3	2 def(erstes): p-use(erstes) 4, ( c-use(erstes) 5 )
4	3 def(letztes): p-use(letztes) 4, ( c-use(letztes) 5 )
5	5 def(mitte): p-use(mitte) 6, ( c-use(mitte) 7 )
6	7 def(erstes): p-use(erstes) 4, ...
7	9 def(letztes): p-use(letztes) 4, ...

d) Bestimmen Sie konkrete Testfälle, so dass jede du-Kette mindestens einmal durchlaufen wird ("du-Ketten-Überdeckung") und erläutern Sie, warum eine vollständige du-KettenÜberdeckung erreicht wird.

TODO: test cases
TODO: explanation (cover all chains)




Aufgabe 2)
a) Der Filter wählt alle Einträge aus, die einen Mann repräsentieren, der 18 Jahre oder älter ist und eine Telefonnummer zugewiesen hat.
M...Geschlecht ist Mann
A...Alter
T...wurde Telefonnummer zugewiesen

+-----+------+-----+-----------+
|  M  | A>=18|  T  |   M AND   |
|     |      |     | A>=18 AND |
|     |      |     |     T     |
+-----+------+-----+-----------+
|  0  |  0   |  0  |     0     |
+-----+------+-----+-----------+
|  0  |  0   |  1  |     0     |
+-----+------+-----+-----------+
|  0  |  1   |  0  |     0     |
+-----+------+-----+-----------+
|  0  |  1   |  1  |     0     |
+-----+------+-----+-----------+
|  1  |  0   |  0  |     0     |
+-----+------+-----+-----------+
|  1  |  0   |  1  |     0     |
+-----+------+-----+-----------+
|  1  |  1   |  0  |     0     |
+-----+------+-----+-----------+
|  1  |  1   |  1  |     1     |
+-----+------+-----+-----------+

TODO: mark everything

b) Der Filter wählt die Einträge aller Frauen aus, deren Nachname entweder "Duck" oder "Maus" ist und die über 30 Jahre alt sind.
TODO: solve

c) Der Filter wählt alle Einträge aus, deren Nachnamen mit "D" beginnen und die entweder weiblich sind oder männlich und zudem außerhalb der Altersgruppe 18-65 (also jünger als 18 Jahre bzw. älter als 65 Jahre) sind.
TODO: solve

d) Was passiert bei einer Implementierung der konkreten Testfälle mit einer Programmiersprache, die eine abkürzende Auswertung von booleschen Operatoren („Lazy Evaluation“) vornimmt?
   Diskutieren Sie die Folgen für die Testbarkeit der Implementierung anhand der Testfälle aus 2a) und 2c).
TODO: solve