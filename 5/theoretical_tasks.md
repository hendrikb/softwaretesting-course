Aufgabe 1
==

Zustände:

  * First: ```currentItemIndex = 0 && isDone = false && itemCount > 0```
  * Valid: ```currentItemIndex > 0 && isDone = false && itemCount > 1```
  * End: ```isDone = true || itemCount = 0```

Das Zustandsdiagramm finden Sie im PDF: Zustandsdiagramm.pdf

Aufgabe 2
==

Siehe Übergangsbaum.pdf

Aufgabe 3
==

```
<initial> Iterator <end> ~Iterator <final>
<initial> Iterator <end> first <end>
<initial> Iterator <end> isDone <end>

<initial> Iterator <first> ~Iterator <final>
<initial> Iterator <first> isDone <first>
<initial> Iterator <first> currItem <first>
<initial> Iterator <first> first <first>

<initial> Iterator <first> next <valid> ~Iterator <final>
<initial> Iterator <first> next <valid> first <first>
<initial> Iterator <first> next <valid> next <valid>
<initial> Iterator <first> next <valid> currItem <valid>
<initial> Iterator <first> next <valid> isDone <valid>

<initial> Iterator <first> next <valid> next <end> isDone <valid>
<initial> Iterator <first> next <valid> next <end> ~Iterator <final>
<initial> Iterator <first> next <valid> next <end> first <first>
```

Aufgabe 4
==

Zu finden in der ```TestSorting.java```. Es gab Schwierigkeiten beim Ausführen
von Abbot unter Linux.

