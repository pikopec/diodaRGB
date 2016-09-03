
Aplikacja obs³uguj¹ca diodê RGB
===================================



Hardware
------------

Do wykonania projektu u¿yty zosta³ mikroprocesor MSP430G2553 i modu³ Bluetooth BTM222.
Dioda RGB jest sterowana za pomoc¹ tranzystorów pnp. Napiêcie zasilania wynosi 3,3V.
Do zasilania uk³adu z akumulatora samochodowego zosta³a zastosowana przetwornica step-down 12V -> 3,3V.


Po³¹czenia Mikroprocesora
--------------

- VCC do 3.3V
- GND do GND
- P1.1 do TX bluetootha
- P1.6 do bazy tranzystora steruj¹cego R+ diody przez rezystor 1kOhm
- P2.4 do bazy tranzystora steruj¹cego G+ diody przez rezystor 1kOhm
- P2.1 do bazy tranzystora steruj¹cego B+ diody przez rezystor 1kOhm

Dodatkowo

- VCC Bluetootha do 3,3V
- GND Bluetootha do GND

Tranzystory

- Emiter do 3,3V
- Kolektor do odpowiedniej diody (do +) przez rezystor 22Ohm
- Wszystkie (-) diod do masy



Obs³uga
--------------

- W³¹czyæ zasilanie uk³adu
- Uruchomiæ aplikacjê
- Je¿eli modu³ bluetooth jest wy³¹czony akceptowaæ zapytanie o aktywacjê
- Z listy dostêpnych urz¹dzeñ wybraæ modu³ 
- Suwakami ustawiæ ¿¹dany kolor