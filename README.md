
Aplikacja obs�uguj�ca diod� RGB
===================================



Hardware
------------

Do wykonania projektu u�yty zosta� mikroprocesor MSP430G2553 i modu� Bluetooth BTM222.
Dioda RGB jest sterowana za pomoc� tranzystor�w pnp. Napi�cie zasilania wynosi 3,3V.
Do zasilania uk�adu z akumulatora samochodowego zosta�a zastosowana przetwornica step-down 12V -> 3,3V.


Po��czenia Mikroprocesora
--------------

- VCC do 3.3V
- GND do GND
- P1.1 do TX bluetootha
- P1.6 do bazy tranzystora steruj�cego R+ diody przez rezystor 1kOhm
- P2.4 do bazy tranzystora steruj�cego G+ diody przez rezystor 1kOhm
- P2.1 do bazy tranzystora steruj�cego B+ diody przez rezystor 1kOhm

Dodatkowo

- VCC Bluetootha do 3,3V
- GND Bluetootha do GND

Tranzystory

- Emiter do 3,3V
- Kolektor do odpowiedniej diody (do +) przez rezystor 22Ohm
- Wszystkie (-) diod do masy



Obs�uga
--------------

- W��czy� zasilanie uk�adu
- Uruchomi� aplikacj�
- Je�eli modu� bluetooth jest wy��czony akceptowa� zapytanie o aktywacj�
- Z listy dost�pnych urz�dze� wybra� modu� 
- Suwakami ustawi� ��dany kolor