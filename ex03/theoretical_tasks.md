Aufgabe 1a
==

Der Kontrollflussgraph ist in einer eigenständigen PDF-Datei in diesem Verzeichnis zu finden.

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

1. Regel 1 ist umgesetzt, es wird nur die Klasse AddressBookControllerTest getestet. Abh�ngigkeiten zu anderen Klassen werden mit Mockups aufgel�st. Die Regel ist sinnvoll, da man durch Testen m�glichst kleiner Einheiten Fehler schnell lokalisieren kann zum Beispiel.
2. Regel 2 ist auch umgesetzt. Aussagekr�ftige Namen f�r Methoden zu verwenden ist immer sinnvoll.
3. Es war die Aufgabe nur die add()-Methode zu testen. Diese ist nichttrivial. Alles zu testen ergibt auch Sinn. Ansonsten kann man auf die Idee kommen, Code, von dem man denkt, der ist sowieso richtig, nicht zu testen.
4. Es werden alle Codepfade getestet. Der Test aller m�glichen Eingabewerte ist nicht m�glich.
5. Wir haben nur g�ltige Strings, leere Strings und null-Strings getestet. Zum Testen der anderen M�glichkeiten m�sste man einfach noch mehr Tests schreiben.
6. Diese M�glichkeit haben wir nicht benutzt. Sie w�re einfach zu implementieren f�r Strings. Mit dieser Regel erh�ht man zwar die Testabdeckung, wenn aber ein Test fehlschl�gt, dann wei� man eventuell nicht, bei welchen Eingabewerten der Test fehlschl�gt.
7. Diese Regel haben wir nicht befolgt. Wir testen zwar ein Feature pro Testmethode. Aber wir versuchen so viele Werte die wir kennen wie m�glich mit asserts zu testen.
8. Regel 8 haben wir so umgesetzt. Dies dient vor allem der Lesbarkeit von Tests.
9. Wir haben Negativtests. Solche Tests sind genauso wichtig wie Positivtests. Erstens kann man damit �berpr�fen, ob nicht bei falschen Eingabewerten unerwarteterweise etwas richtiges herauskommt. Zweitens muss man �berpr�fen, ob falsche Eingaben nicht das gesamte System zerst�ren.
10. Wir haben darauf geachtet, dass eine Testmethode nur einen Testfall enth�lt, sodass der Ausfall eines Testfalls die Ausf�hrung anderer nicht beeinflusst. Die Regel ist damit sinnvoll, erscheint uns jedoch irref�hrend formuliert, da JUnit uns mit unserer "nat�rlichen" Vorgehensweise darin unterst�tzt hat. Wir mussten also keine zus�tzlichen Ma�nahmen ergreifen, um diese Regel zu erf�llen.
