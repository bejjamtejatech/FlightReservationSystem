#Flight Reservation System 

This project is a simple **Flight Reservation System** built in Java as part of a Java Engineer assessment.  

#Features
 Search Flights
Users can search for available flights using:
*Destination*
*Date (yyyy-MM-dd)*

Search results show all matching flights and available seats.

#Book a Flight
Users can:
- Enter their name  
- Choose a flight number  
- Enter number of seats  

The system validates seat availability and prevents overbooking.

#View Reservations
Users can list all reservations made under their name.

#Project Structure

#Design Decisions

1. *Separation of Concerns*
   - Flight and Reservation are simple model/entity classes.
   - `FlightService` contains all business logic such as searching flights, validating seat availability, and booking reservations.
   - `FlightReservationApp` handles only user interaction (input/output).  
     This separation improves testability and maintainability.

2. *In-Memory Data Storage*
   - As required, the application uses simple in-memory `List<Flight>` and `List<Reservation>` collections.
   - Data does not persist after stopping the application, matching assessment guidelines.

3. *Validation & Error Handling*
   - Prevents overbooking by checking `availableSeats` before creating a reservation.
   - Ensures requested seats > 0.
   - Provides console messages when operations fail.

4. *Use of Java Date/Time API*
   - `LocalDateTime` ensures proper flight date comparison.
   - Users input date only; time defaults to midnight for accurate matching.

5. *Testability*
   - `FlightService` is fully isolated from user interface, making it easy to test using JUnit.
   - Methods are pure functions with clear inputs and outputs.

6. *Stream API Usage*
   - Java Streams improve readability and make search filtering concise and expressive.

#Real-Life Considerations

If this system were deployed as a real airline booking service, the following enhancements would be necessary:

1. *Database Persistence*
   - Real flights and reservations should be stored in a relational or NoSQL database.
   - Prevents data loss and supports analytics and reporting.

2. *Concurrency & Overbooking Prevention*
   - In real systems, multiple users may try to book the same seat simultaneously.
   - Requires row-level locking, transactions, or optimistic concurrency control.

3. *REST API Implementation**
   - The console application would be replaced with REST endpoints using Spring Boot.
   - UI could become a web or mobile interface.

4. *Authentication & Authorization**
   - Users must log in.
   - Bookings should only be accessible by the user who created them.

5. *Scalability*
   - Deploy services in containers (Docker/Kubernetes).
   - Add caching mechanisms (Redis) for frequent flight searches.

6. *Logging & Monitoring*
   - Use robust logging frameworks (Logback/SLF4J).
   - Integrate observability tools like Prometheus, Grafana, or ELK.

7. *Input Validation & Error Messages*
   - In production, invalid inputs must produce descriptive error responses, not console messages.

8. *Improved Search Functionality**
   - Allow searching by date range, airline, price, flight duration, etc.


