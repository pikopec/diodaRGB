
Aplikacja obsługująca diodę RGB
===================================



Hardware
------------

Do wykonania projektu użyty został mikroprocesor MSP430G2553 i moduł Bluetooth BTM222.

Dioda RGB jest sterowana za pomocą tranzystorów pnp. Napięcie zasilania wynosi 3,3V.

Do zasilania układu z akumulatora samochodowego została zastosowana przetwornica step-down 12V -> 3,3V.


Połączenia Mikroprocesora
--------------

- VCC do 3.3V
- GND do GND
- P1.1 do TX bluetootha
- P1.6 do bazy tranzystora sterującego R+ diody przez rezystor 1kOhm
- P2.4 do bazy tranzystora sterującego G+ diody przez rezystor 1kOhm
- P2.1 do bazy tranzystora sterującego B+ diody przez rezystor 1kOhm

Dodatkowo

- VCC Bluetootha do 3,3V
- GND Bluetootha do GND

Tranzystory

- Emiter do 3,3V
- Kolektor do odpowiedniej diody (do +) przez rezystor 22Ohm
- Wszystkie (-) diod do masy



Obsługa
--------------

- Włączyć zasilanie układu
- Uruchomić aplikację
- Jeżeli moduł bluetooth jest wyłączony akceptować zapytanie o aktywację
- Z listy dostępnych urządzeń wybrać moduł 
- Suwakami ustawić żądany kolor



Kod
--------------

Kod do mikroprocesora został dołączony do reporzytorium w ./MSP430G2553_code/main.c 

Kod kompilować i wgrywać na procesor używając Code Composer Studio.