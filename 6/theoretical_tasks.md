Aufgabe 1
==

1a
--

Äquivalenzklassen:


	für zeichen: min_char-1, max_char+1, beliebiges Zeichen
	für alphabet: null, gültiges Array
	
	Da in Java min_char-1 = max_char und max_char+1 = min_char
	können nur gültige Zeichen für den Parameter zeichen übergeben
	werden.
	
	Testfälle:
	1) zeichen: gültiges Zeichen 'a', alphabet: gültiges Array "abc"
	2) zeichen: gültiges Zeichen 'a', alphabet: null
	
Grenzwertanalyse:

	für zeichen: max_char (65535), min_char (0), min_char+1 (1), max_char-1 (65534)
	für alphabet: null, "", "x", "xxx..." (size Integer.MAX_VALUE-5), "xxx..." (size Integer.MAX_VALUE-4),"xxx..." (size Integer.MAX_VALUE-6)
	
	Testfälle für zeichen:
	1) zeichen: '\uffff', alphabet: "abc"
	2) zeichen: '\0', alphabet: "abc"
	3) zeichen: '\u0001', alphabet: "abc"
	4) zeichen: '\ufffe', alphabet: "abc"
	
	Testfälle für alphabet:
	5) zeichen: 'a', alphabet: null
	6) zeichen: 'a', alphabet: ""
	7) zeichen: 'a', alphabet: "x"
	8) zeichen: 'a', alphabet: "x" * size Integer.MAX_VALUE-5
	9) zeichen: 'a', alphabet: "x" * size Integer.MAX_VALUE-4
	10) zeichen: 'a', alphabet:"x" * size Integer.MAX_VALUE-6
	
Äquivalenzklassen + Grenzwertanalyse:

	Testfälle:
	1) zeichen: '\uffff', alphabet: "abc"
	2) zeichen: '\0', alphabet: "abc"
	3) zeichen: '\u0001', alphabet: "abc"
	4) zeichen: '\ufffe', alphabet: "abc"
	5) zeichen: 'a', alphabet: null
	6) zeichen: 'a', alphabet: ""
	7) zeichen: 'a', alphabet: "x"
	8) zeichen: 'a', alphabet: "x" * size Integer.MAX_VALUE-5
	9) zeichen: 'a', alphabet: "x" * size Integer.MAX_VALUE-4
	10) zeichen: 'a', alphabet:"x" * size Integer.MAX_VALUE-6
	11) zeichen: gültiges Zeichen 'a', alphabet: gültiges Array "abc"
	
1b
--

Durch die Äquivalenzklassen und Grenzwertanalyse bekommt man viel mehr Testfälle als bei den du-Ketten.
Für die du-Ketten reicht ein Tesfall. Allerdings bekommt man mit der Äquivalenzklassen und Grenzwertanalyse
keine 100%ige Pfadabdeckung, da diese zum Beispiel nicht auf die eigentlichen Werte der Zeichen im Array
eingehen.

	
	

Aufgabe 2
==

Die Anforderungen haben wir im Excel-File Anforderungen.xlsx erfasst, die Risiken sind entsprechend eingeschätzt worden.

Aufgabe 3
==

Die einzelnen Testfälle der **Musterlösung** haben wir in der Datei Testfaelle.xlsx beschrieben, dokumentiert und die Ergebnisse ausgewertet.

Die Zuordnungen zu den Anforderungen befinden sich in der entsprechenden Spalte im Dokument.

Den Testausführungsaufwand haben wir ganz simpel nach Lines Of Code in der Implementierung des Testfalls bemessen.

Nach dem Gespräch mit dem Testmanager, können wir nun nur noch vier von neun Testfälle testfälle implementieren. Die Spalte  "Test Implementieren (nach Absprache mit Manager)?" gibt Aufschluss darüber, welche der neun Testfälle noch implementiert werden. Die Entscheidung basiert auf der Zuweisung zur Anforderung und deren Risiko/Priorität.
