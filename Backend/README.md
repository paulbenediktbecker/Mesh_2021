# Backend service for e01
In diesem Ordner befindet sich der Quellcode für das Backend Systems unserer Plattform.

# Tech
Der Service wurde mithilfe des Java Spring Frameworks und Spring Boot entwickelt.
Aktuell bietet er (nur) die Möglichkeit Parkplätze anzulegen, zu ändern und zu löschen. Mehr Funktionen für die Zukunft sind geplant.
Die Daten sind mithilfe einer API abruf und veränderbar.

## Konzept
Die Applikation unterscheidet zwischen zwei Datenobjekten:
* `Addresses` (Addressen) und
* `Parkinglots` (Parkplätze)
 
Dabei gilt zu beachten, dass jedes Addresse mehrere Parkplätze haben kann. Aktuell ist die Anzahl auf 3 Parkplätze pro Addresse festgelegt. Dies kann allerdings bequem über eine Spring Einstellung gesetzt werden (`application.parking.parkinglots.maxnumber`).

## API
Sobald die Applikation gestartet ist kann die API unter `localhost:8080/parking/address` erreicht werden.
Sie biete aktuell 14 unterschiedliche Endpunkte um Addressen und Parkplätze zu erstellen, manipulieren, zu löschen oder abzurufen.
Eine Liste der Endpunkte erhält man, indem man die Applikation startet und die API-Definition unter `localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/` öffnet.

("localhost" kann dabei durch den aktuellen Host ersetzt werden)

# Die Applikation bauen und Starten
* Benötigt Java 11 oder höher.
Die Applikation kann mithilfe von Maven gebaut werden: `Maven clean package`. 
Im Anschluss kann die `SmartEnv-x.x.x.jar` vom `/target/` Ordner in ein beliebiges Verzeichnis geschoben werden.
Um die App zu Starten kann folgender Befehl genutzt werden `java -jar SmartEnv-x.x.x.jar`.

