# login
 Byggar en webbapplikation för webbshop. Vissa sidor är öppna och andra är låsta, krävs inloggning för att komma åt. Använder Spring Security. 

## Description
I det här projektet användts Spring Boot, Thymeleaf, Spring Security och mysql databas för att bygga en webbapplikation. Applicaktionen består av fyra sidor, index, register, pdocutdetails and orders. 

### Index sida
  * indext sidan är den som man ser när programmet körs, visar några prokter. Har 'Logga in', 'Registrera' och 'Detaljer' länk.
  * 'Logga in' tat till Spring Securitys default inlognings form.
  * Efter inlogningen redirekterar till start sidan.
  * Visar 'Logga ut' om man är inloggad.
  * Visar 'Mina ordrar', if inloggad.

### Register sida
  * Visar en formulär, där kan man registreara sig.
  * Det sparar i database och efter lyckad inlogning redirekter till start sidan.

### ProductDetails sida
  * Visar detaljrad info t.ex. pris och andra detaljer.
  * Om man är inloggad visar en knapp 'Lägg till i kundvagn'. Knappen har ingen funktion!

### Orders
  * Krävs inloggning för att komma åt sidan. 
  * Sidan innhåller ingenting, men man kommer inte åt sidan om inte är inloggad.
  * Om man försöker komma åt sidan manuellt via url, redirekterar til login sida.
