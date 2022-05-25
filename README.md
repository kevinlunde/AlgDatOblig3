# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Kevin Lunde, S349965, S349965@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så tok jeg utganspunkt i kompendiet og lå til refereanse til foreldre. vi itererer gjenom treet til vi finner en ledig plass
der verdien vi ønsker å legge inn kan passe. og legger den inn der. deretter legger vi til 1  i antall og returnerer. 


I oppgave 2 så brukte jeg en while løøkke til å iterere gjenom treet for å finne verdien vi var på utkikk etter. når vi finner verdien vi ser etter så har vi en teller som plusser på en, og går til høyre. den fortsetter å iterere til bunnen av treet og returnerer antall like.

i oppgave 3 så har vi en while løkke som itererer til venstre barn så fremt til at det ikke er null. dersom venstre barn er null går den til høyre barn. detter gjør den helt til den har 
funnet bladnoden helt til venstre og returnerer denne.
I nestepostorden så finner vi neste node i post orden ved å bruke det vi vet om iterering i post orden og skrive det ved if og while setninger, for å alltid finne den neste noden i post orden. 

I postorden så bruker vi de to hjelpemedtodene vi har lager: første postorden g neste postorden. vi kaller først første postorden for å finne første node, og deretter kaller vi bare neste postorden frem til vi har 
iterert oss gjennom treet og velger for hver gang å pushe det til oppgave.
vi lager så en metode som gjør dette recusivt der vi rett og slett bare har to if statments som kaller seg selv dersom høyre og venstr pekere ikke er null. og pusher da det hele til oppgave

i oppgave 5 så velger vi å bruke en arreydeque for å kjøre gjennom binærtreet og legge til hver av verdiene i et arrey, helt til vi har iterert gjenom hele treet. 
i deserialize så setter vi første verdien i arreyet som rot og bruker deretter legg inn metoden for å legge inn hver av verdiene på riktig plass i treet. 

i de siste tre oppgavene har mye av koden blitt hentet fra kompendiet, i fjern så har vi hentet kode fra kompendiet og lagt til foreldrepekere som ikke hører med ellers.

