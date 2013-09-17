Aufgabe 1) 
a) Bestimmen Sie konkrete Testfälle zur vollständigen Anweisungsüberdeckung und erläutern Sie, warum eine vollständige Anweisungsüberdeckung erreicht wird.

Zur vollständigen Anweisungsüberdeckung müssen alle Anweisungen mindestens einmal ausgeführt werden.
Die beiden Definitionen werden immer ausgeführt, brauchen also keinen besonderen Test.
Für die 3 if-elseif-else-Zweige muss je eine bestimmte Bedingung gelten.
Für den ersten Zweig: (alphabet[mitte] < zeichen)
Für den zweiten: (alphabet[mitte] > zeichen)
Und für den dritten alphabet[mitte] == zeichen
Das letzte return wird nur ausgeführt, wenn niemals alphabet[mitte] == zeichen.

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

Dies entspricht einer Entscheidungsüberdeckung von 100%.

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
// Comment: Hier fehlen ein paar Fälle:
Ketten: [Variable, def-Zeile, use-Zeile]
[erstes,2,5]
[erstes,2,6]
[erstes,8,5]
[erstes,8,6]

[zeichen,1,7]
[zeichen,1,9]

[alphabet,1,3]
[alphabet,1,7]
[alphabet,1,9]

[letztes,3,5]
[letztes,3,6]
[letztes,10,5]
[letztes,10,6]

[mitte,6,7]
[mitte,6,8]
[mitte,6,9]
[mitte,6,10]
[mitte,6,12]

d) Bestimmen Sie konkrete Testfälle, so dass jede du-Kette mindestens einmal durchlaufen wird ("du-Ketten-Überdeckung") und erläutern Sie, warum eine vollständige du-Kettenüberdeckung erreicht wird.

TODO: test cases
Der erste Testfall deckt alles ab
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

Lösung nr.2:
P(x) = x.gender==Male && x.age>=18 && x.contact instanceof PhoneNumber

+----------------+-----------+--------------+-------+------------+
| x.gender==Male | x.age>=18 | x.contact... | P(x)  | verwenden? |
+----------------+-----------+--------------+-------+------------+
| True           | True      | False        | False | Ja         |
| True           | True      | True         | True  | Ja         |
| True           | False     | False        | False | Ja         |
| True           | False     | True         | False | Ja         |
| False          | True      | False        | False | Ja         |
| False          | True      | True         | False | Ja         |
| False          | False     | False        | False | Ja         |
| False          | False     | True         | False | Ja         |
+----------------+-----------+--------------+-------+------------+

b) Der Filter wählt die Einträge aller Frauen aus, deren Nachname entweder "Duck" oder "Maus" ist und die über 30 Jahre alt sind.
P(x) = x.gender==Female && (x.surName=="Duck" || x.surName=="Maus") && x.age>30

+------------------+-------------------+-------------------+----------+-------+------------+
| x.gender==Female | x.surName=="Duck" | x.surName=="Maus" | x.age>30 | P(x)  | verwenden? |
+------------------+-------------------+-------------------+----------+-------+------------+
| True             | True              | True              | True     | True  | Nein       |
| True             | True              | True              | False    | False | Ja         |
| True             | True              | False             | True     | True  | Ja         |
| True             | True              | False             | False    | False | Ja         |
| True             | False             | True              | True     | True  | Ja         |
| True             | False             | True              | False    | False | Ja         |
| True             | False             | False             | True     | False | Ja         |
| True             | False             | False             | False    | False | Nein       |
| False            | True              | True              | True     | False | Ja         |
| False            | True              | True              | False    | False | Nein       |
| False            | True              | False             | True     | False | Ja         |
| False            | True              | False             | False    | False | Nein       |
| False            | False             | True              | True     | False | Ja         |
| False            | False             | True              | False    | False | Nein       |
| False            | False             | False             | True     | False | Nein       |
| False            | False             | False             | False    | False | Nein       |
+------------------+-------------------+-------------------+----------+-------+------------+

c) Der Filter wählt alle Einträge aus, deren Nachnamen mit "D" beginnen und die entweder weiblich sind oder männlich und zudem außerhalb der Altersgruppe 18-65 (also jünger als 18 Jahre bzw. älter als 65 Jahre) sind.
P(x) = x.surName[0]=='D' && (x.gender==Female || x.gender==Male && (x.age<18 || x.age>65))

+-------------------+------------------+----------------+----------+----------+-------+------------+
| x.surName[0]=='D' | x.gender==Female | x.gender==Male | x.age<18 | x.age>65 | P(x)  | verwenden? |
+-------------------+------------------+----------------+----------+----------+-------+------------+
| True              | True             | False          | True     | False    | True  | Nein       |
| True              | True             | False          | False    | True     | True  | Nein       |
| True              | True             | False          | False    | False    | True  | Nein       |
| True              | False            | True           | True     | False    | True  | Ja         |
| True              | False            | True           | False    | True     | True  | Ja         |
| True              | False            | True           | False    | False    | False | Nein       |
| False             | True             | False          | True     | False    | False | Nein       |
| False             | True             | False          | False    | True     | False | Nein       |
| False             | True             | False          | False    | False    | False | Ja         |
| False             | False            | True           | True     | False    | False | Nein       |
| False             | False            | True           | False    | True     | False | Nein       |
| False             | False            | True           | False    | False    | False | Nein       |
+-------------------+------------------+----------------+----------+----------+-------+------------+

d) Was passiert bei einer Implementierung der konkreten Testfälle mit einer Programmiersprache, die eine abkürzende Auswertung von booleschen Operatoren („Lazy Evaluation“) vornimmt?
   Diskutieren Sie die Folgen für die Testbarkeit der Implementierung anhand der Testfälle aus 2a) und 2c).

   Bei Lazy Evaluation wird nach dem ersten False in einer Konjunktion oder nach dem ersten True in
   einer Disjunktion abgebrochen. In einigen Fällen können deswegen zum Beispiel nicht alle
   Möglichkeiten getestet werden.

   Bei 2a wird sowieso alles getestet. Von daher kann man nicht noch weitere Testfälle hinzuziehen.

   Für 2c muss noch ein Testfall hinzugenommen werden, denn bei dem 3 Testfall, bei dem  (x.surName[0]=='D') == False
   und (x.gender==Female) == True, wird nach dem ersten atomaren Ausdruck abgebrochen, die Auswertung des Geschlechts
   wird übersprungen. Es fehlt dann noch ein Testfall bei dem der Nachname mit D anfängt und wo die Person weiblich ist.
