
Crisp - Less Insane Stupid Parentheses

Betekenissen van '()'

Block:
------

(
	()
	()
)

Syntax: list zonder literal als eerste waarde
Semantiek: wanneer een block wordt aangetroffen tussen regels te executen code, dan worden alle regels binnen dit block uitgevoerd.
Crisp: '{}'

Aanroep:
--------

(asdf x y)

Syntax: list met literal als eerste waarde, komt voor in regels te te executen code.
Semantiek: voer de aanroep uit
Crisp: asdf (x y)
Ambigu: asdf kan ook een gewone variabele zijn. In dat geval lezen we hier 2 variabelen (de 2e is een lijst) ipv een aanroep.
Oplossing: zie 'lijst'


Lijst:
------

(1 2 3)

Syntax: lijst met willekeurige waarden welke op het moment niet geevauleerd wordt.
Crisp: [1 2 3]


Komma's en puntkomma's
======================

In principe zijn komma's nog steeds niet noodzakelijk. Dit omdat er geen verwarring bestaat tussen infix operations en lijstwaarden. [1 + 1] schrijven we immers als +(1 1) .
Maar zelfs met behoud van de prefixnotatie leest +(1,1) mogelijk wel vertrouwder. In dit geval kunnen we eenvoudigweg bij het lezen van een lijst of aanroep (mogelijk) komma's verwachten.

Een andere onnoodzakelijke toevoeging is de puntkomma aan het eind van een statement. In Lisp is dit commentaar tot het einde van de regel. Het kan dus wel per abuis worden toegevoegd, maar niet om twee statements op een regel te scheiden: a; b; -- 'b;' is hier al commentaar.

Met een andere commentaar-syntax zouden we puntkomma's kunnen inzetten. Optioneel, als een soort van whitespace, of obligatoir.
Echter, puntkomma's voegen nog minder toe dan komma's. (Komma's helpen tenminste nog om lijstobjecten te tellen.)


Hello, world! in Crisp:
=======================

define (hello [who]
	println (+("Hello, " who "!"))
)

hello ("world")


Discussie
---------

In bovenstaande zijn twee zaken bovenal anders dan verwacht.
- De parameters voor 'hello' staan in de definitie in blokhaken. Dit is omdat het een lijst betreft.
- De functie lijkt geen body ('{}') te hebben. Dit is omdat in Lisp doorgaans 'alles wat nog volgt' als body wordt opgenomen. Hier zijn dus wel haakjes wegbezuinigd!
  Daar hoeven we niet per se aan mee te doen.


'hello_closures', zonder komma's:

define (greeting "Hello, ")
define (resolvewho {"me"})

define (hello [who]
	println (+ (greeting who "!"))
)

{
	define (resolvewho {"who?"})
	hello (resolvewho)
}
hello (resolvewho)

