# Ordnerstruktur

Hier ist die Ordnerstruktur des Projekts dokumentiert. Möglicherweise sind
noch nicht alle Ordner tatsächlich im Repository enthalten, tut dies dann,
wenn ihr sie braucht.

---

### 🖿 docs - Dokumentation

  - 🖿 **general** - Dokumentationen zu allgemeinen Dingen (z.B. Git, Markdown, OOP)
  - 🖿 **project** - Dokumentationen zu einzelnen Bestandteilen des Projektes

---

### 🖿 src - Spiel-Code, Test-Code, Bilder, Musik

  - 🖿 **main** - Dateien für das Projekt selbst
    - $\color{#548af7}\text{🖿}$ **java** - Java-Code
      - 📦 **czg** - Haupt-Code-Ordner
        - 📦 **objects** - Alles rund um Spielobjekte
        - 📦 **scenes** - Alles rund um Szenen
        - 📦 **util** - Hilfreiche Funktionen, die überall im Projekt gebraucht werden können
    - $\color{#f2c55c}\text{🖿}$ **resources** - Bilder, Musik...
      - 🖿 **assets** - Haupt-Ordner
          - 🖿 **backgrounds** - Hintergründe für Szenen
          - 🖿 **characters** - Sprites für Charaktere
            - 🖿 **player** - Bestandteile der Spielfigur
          - 🖿 **items** - Sprites für Items
          - 🖿 **sound** - Musik & Soundeffekte
  - 🖿 **test** - Separater Code zum Testen von Programmteilen
    - $\color{#57965c}\text{🖿}$ **java** - Java-Code
      - 📦 **czg** - Haupt-Test-Code-Ordner
        - 📦 **scenes** - Tests für Szenen
    - $\color{#f2c55c}\text{🖿}$ **resources** - Eventuelle Dateien, die für Tests benötigt werden

---

