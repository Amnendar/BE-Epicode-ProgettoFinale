# BE-Epicode-ProgettoFinale
## Contenuti
* [Introduzione](#introduzione)
* [Tecnologie](#tecnologie)
* [Sicurezza](#sicurezza)
* [Spiegazione](#spiegazione)
* [Link Utili](#link)
* [Testing](#test)

## Introduzione
Questo progetto consiste in un'applicazione (a scopo didattico, comprensiva solo di lato Back-End) per la gestione di un sistema di clienti per un'azienda di fornitura energetica, collegata ad un DataBase SQL. 

Abbiamo anche aggiunto un sistema di autenticazione basato su token JWT. Bisogna essere autenticati per accedere ai metodi. 
NOTA. Il sistema già presenta alcune entità nel database per eventuali test, oltre che a 2 utenti di default (user e admin).
	
## Tecnologie
Il progetto è stato creato usando:
* Java + Spring Boot
* PostegreSQL
* Spring Security + Token JWT
* JUnit
* Maven 
* Git (GitHub)
* Comprensivo di Swagger e OpenApi per la documentazione 

## Sicurezza
Come già accennato, per accedere al sistema gli utenti/user devono essere autenticati(è possibile effettuare una registrazione utente):

MODEL UTENTE PARAMETRIZZATO
```
{
  "userName": "Lorece",
  "password": "provapassword",
  "nome": "Lorenzo",
  "cognome": "De Ceglie",
  "mail": "strin@string.com",
  "roles": [
    "USER"
  ]
}
```

Anche gli utenti/user vengono salvati sul database, le password vengono criptate usando BCrypt Password Encoder.

Gli utenti sono divisi in Admin e User. Gli utenti con ruolo User possono soltanto accedere ai metodi di visualizzazione, i metodi di modifica/aggiorna/cancella sono disponibili soltanto per gli admin. Un utente può avere entrambi i ruoli contemporanemente.

	
## Spiegazione
L'applicazione viene utilizzata per la gestione delle seguenti entità:
* Clienti
* Fatture
* StatoFatture
* Indirizzi
* Comuni
* Province
* User(Autenticazione)

Le entità vengono salvate sul database con un valore numerico(Long) come chiave primaria.

Abbiamo a disposizione tutte le funzionalità di CRUD per tutte le entità(apparte Comuni e Province,che dispongono solo di metodi di visualizzazione in quanto vengono caricate da un file .CSV presente nel progetto),
più la possibilità di visualizzare o cercare Clienti e Fatture tramite determinati parametri

I clienti sono comprensivi di diversi dati:

ESEMPIO DI BODY JSON PER INSERIMENTO
```
{
   
  "ragioneSociale": "23 SAS",
  "partitaIva": "D32334343",
  "tipoCliente": "SAS",
  "email": "strin23g@string.it",
  "dataInserimento": "2022-03-16",
  "dataUltimoContatto": "2022-03-16",
  "fatturatoAnnuale": 200000,
  "pec": "string@pec.it",
  "telefono": "323233434",
  "emailContatto": "string@string.it",
  "nomeContatto": "Holly",
  "cognomeContatto": "String",
  "telefonoContatto": "33434534343",
  "sedeLegale": {
    "id": 1
    }
  ,
  "sedeOperativa": {
    "id": 4
    },
  "fatture":[]
}
```
Alla creazione di un cliente bisogna o passare entrambi gli indirizzi tramite gli ID di questi, o non passarne nessuno per poi aggiungerli coi metodi appositi in seguito(Per comodità sono stati aggiunti due metodi per modificare direttamente gli indirizzi singolarmente, invece del'update generale di cliente). 

Eventuali fatture possono essere aggiunte assieme al cliente durante la sua creazione(Anche durante il suo aggiornamento, dove verranno aggiunte a quelle già presenti).

ESEMPIO DI BODY JSON PER FATTURA
```
{
  "anno": 2022,
  "data": "2022-03-15",
  "importo": 3000,
  "stato": {
    "id": 5
  },
  "cliente": {
    "id": 4
  },
    "nfattura": 211
  }
```
Come si può vedere, la fattura deve necessariamente essere collegata ad un cliente (tramite la chiave primaria di quest'ultimo) per poter essere creata.

(NOTA. Se inserite nella creazione cliente, esse verrano SEMPRE collegate al cliente che verrà creato, nonostante l'eventuale Id passato nella fattura)

Lo Stato di una fattura è stato realizzato come un entità a sé stante, comprensiva solo di id e una stringa che indica il nome dello stato.
```
{
  "id" : 02
  "stato": "Da Inviare"
}
```
NOTA. Con i dati di default, abbiamo due stati salvati rispettivamente con Id 5(Pagata) e 6(Non Pagata). La scelta di crearla come entità lascia la possibilità di aggiornare, inserire o eliminare gli stati già esistenti. è stato aggiunto un metodo per aggiornare lo stato di una fattura direttamente, invece del classico update.

Rimangono gli indirizzi, come visto ogni cliente ne ha due (sede legale e sede operativa, che possono corrispondere al medesimo indirizzo)
```
{
  "via": "Via del Melograno",
  "civico": "2",
  "localita": "Bussoleno",
  "cap": "01344",
  "comune": {
    "id": 43
  }
}
```
Il comune viene passato tramite l'ID corrispondente. In caso di dubbi, ci sono metodi get(per Id e per Nome) per cercarli.

Comune e Provincie sono entità che vengono caricate direttamente da due file .CSV presenti. Sono comprensive di nome, sigle e collegate tra loro (ogni Provincia è collegata alla propria lista di Comuni, ogni Comune è collegato alla propria provincia).


## Link
Link di accesso alla collezione Postman comprensiva di tutte le chiamate ai controller(Per poter effettuare tutte le chiamate e provare i metodi):
https://www.getpostman.com/collections/449eb38720e1a4a216fd

NOTA. La collezione è anche inclusa fisicamente nel progetto, assieme ad un backup per un db di prova(nella cartella src/main/resources/risorse)

Esempio di link di accesso a Swagger(Per la documentazione):
http://localhost:8080/swagger-ui/index.html#/

## Test
Sono stati implementati un numero consistente di test effettuati con Junit e MockMvc, presenti nelle apposite classi nel package di test.
