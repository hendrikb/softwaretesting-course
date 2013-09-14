Aufgabe 1b
==

> Ermitteln Sie die zyklomatische Zahl der Methode

Es gibt 11 Kanten und 9 Knoten. Daher ist die zyklomatische Zahl v(G) = e - v + 2 = 11 - 9 + 2 = 4.

Aufgabe 1c
==

	public static int binSearch(int[] a, int k) {			// d(a,k)
		int ug = 0, og = a.length-1, m = 0, pos = NOTFOUND;	// d(ug, og, m, pos), r(a)
		while (ug <= og && pos == NOTFOUND) {			// r(ug, og, pos)
			m = (ug + og) / 2;				// d(m), r(ug, og)
			if (a[m] == k)					// r(a, m, k)
				pos = m;				// d(pos), r(m)
			else 
				if (a[m] < k)				// r(a, m, k)
					ug = m + 1;			// d(ug), r(m)
				else  
					og = m - 1;			// d(og), r(m)
		} 
		return pos;						// r(pos)
	} 
	
Anomalien: 
	
1. dd-Anomalie von m in "m = (ug + og) / 2": Vorherige Definition "m = 0"

Aufgabe 2
==

Zu den 10 Regeln:

1. Regel 1 ist umgesetzt, es wird nur die Klasse AddressBookControllerTest getestet. Abhängigkeiten zu anderen Klassen werden mit Mockups aufgelöst. Die Regel ist sinnvoll, da man durch Testen möglichst kleiner Einheiten Fehler schnell lokalisieren kann zum Beispiel.
2. Regel 2 ist auch umgesetzt. Aussagekräftige Namen für Methoden zu Verwenden ist immer sinnvoll.
3. Es war die Aufgabe nur die add()-Methode zu testen. Diese ist nichttrivial. Alles zu testen macht auch Sinn. Ansonsten kann man auf die Idee kommen, Code, von dem man denkt der ist sowieso richtig, nicht zu testen.
4. Es werden alle Codepfade getestet. Der Test aller möglichen Eingabewerte ist nicht möglich.
5. Wir haben nur gültige Strings, leere Strings und null-Strings getestet. Zum Testen der anderen Möglichkeiten müsste man einfach noch mehr Tests schreiben.
6. Diese Möglichkeit haben wir nicht benutzt. Sie wäre einfach zu implementieren für Strings. Mit dieser Regel erhöht man zwar die Testabdeckung, wenn aber ein Test fehlschlägt, dann weiß man eventuell nicht bei welchen Eingabewerten, der Test fehlschlägt.
7. Diese Regel haben wir nicht befolgt. Wir testen zwar ein Feature pro Testmethode. Aber wir versuchen so viele Werte die wir kennen wie möglich mit asserts zu testen.
8. Regel 8 haben wir so umgesetzt. Dies dient vor allem der Lesbarkeit von Tests.
9. Wir haben Negativtests. Solche Tests sind genauso wichtig wie Positivtests. Erstens kann man damit überprüfen ob nicht bei falschen Eingabewerten unerwarteterweise etwas richtigs bei rauskommt. Zweitens muss man überprüfen ob falsche Eingaben nicht das gesamte System zerstören.
10. ???