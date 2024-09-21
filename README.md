# Microservizi con Spring Boot

## Esigenze Funzionali:

Il progetto prevede la creazione di due microservizi distinti che devono comunicare tra loro per gestire prodotti e ordini.

Il primo microservizio sarà responsabile della gestione dei prodotti (ProductService), mentre il secondo si occuperà della gestione degli ordini (OrderService), che includeranno i prodotti definiti nel primo servizio.

## Tecnologie e Strumenti Richiesti:

- **Framework:** Spring Boot
- **Librerie:**
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-openfeign` (per la comunicazione tra microservizi con FeignClient)
- **Linguaggio:** Java 11 o superiore
- **Strumenti di Sviluppo:**
  - IDE: IntelliJ IDEA, Eclipse, o altro IDE Java preferito
  - Tool di Build: Maven o Gradle
  - Postman per il test dell'API
  - GIT per la gestione del codice sorgente
  - Docker (opzionale, per deploy dei microservizi in container)

## Obiettivi Specifici

1. **Configurazione del Progetto:**
   - Creare due progetti Spring Boot separati: **ProductService** e **OrderService**.
   - Includere le dipendenze necessarie per Spring Web, Spring Data JPA, e FeignClient.

2. **Implementazione di ProductService:**
   - Implementare le operazioni CRUD per la gestione dei prodotti, come nell'Esercizio 1.
   - Definire un controller REST che esponga endpoint per la creazione, lettura, aggiornamento e cancellazione dei prodotti.

3. **Implementazione di OrderService:**
   - Creare un'entità `Order` che includa i dettagli degli ordini, come ID, lista di prodotti e quantità.
   - Implementare CRUD per la gestione degli ordini.
   - Utilizzare **FeignClient** (o RestTemplate come alternativa) per permettere a OrderService di chiamare ProductService e ottenere i dettagli dei prodotti.

4. **Comunicazione tra Microservizi:**
   - Implementare la comunicazione tra **OrderService** e **ProductService** per ottenere i dettagli dei prodotti quando viene creato o aggiornato un ordine.
   - Verificare che OrderService sia in grado di effettuare richieste a ProductService e gestire eventuali errori (ad esempio, se un prodotto non esiste).

5. **Gestione delle Risposte e degli Errori:**
   - Implementare la gestione delle eccezioni per risposte errate o fallite durante la comunicazione tra microservizi.
   - Gestire correttamente le risposte HTTP restituendo messaggi adeguati in caso di successo o errore.

6. **Test dell'API:**
   - Testare separatamente i due microservizi utilizzando Postman per verificare il corretto funzionamento delle operazioni CRUD.
   - Verificare la corretta comunicazione tra OrderService e ProductService attraverso FeignClient o RestTemplate.

7. **Gestione del Codice Sorgente:**
   - Utilizzare GIT per la gestione del codice sorgente.
   - Creare repository separati per **ProductService** e **OrderService** su una piattaforma di hosting GIT.
   - Effettuare commit frequenti e ben documentati durante lo sviluppo.
   - Utilizzare branching e merging per gestire le diverse funzionalità e le revisioni del codice.

## Conclusione:

Questo documento definisce i requisiti per la creazione di due microservizi che comunicano tra loro in un sistema di gestione prodotti e ordini. L'obiettivo è implementare la logica di business nei due servizi separati e consentire loro di interagire in maniera sicura e corretta tramite FeignClient o RestTemplate.