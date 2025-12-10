import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightServiceTest {

    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightService = new FlightService();

        // Add sample flights
        flightService.getFlights().add(new Flight(
                "F001", "New York",
                LocalDateTime.of(2025, 12, 10, 10, 0),
                50
        ));

        flightService.getFlights().add(new Flight(
                "F002", "Chicago",
                LocalDateTime.of(2025, 12, 10, 12, 0),
                30
        ));

        flightService.getFlights().add(new Flight(
                "F003", "New York",
                LocalDateTime.of(2025, 12, 11, 14, 0),
                20
        ));
    }

    // -------------------------------------------------------
    // SEARCH TESTS
    // -------------------------------------------------------

    @Test
    void testSearchFlights_found() {
        List<Flight> result = flightService.searchFlights(
                "New York",
                LocalDateTime.of(2025, 12, 10, 0, 0)
        );

        assertEquals(1, result.size());
        assertEquals("F001", result.get(0).getFlightNumber());
    }

    @Test
    void testSearchFlights_notFound() {
        List<Flight> result = flightService.searchFlights(
                "Los Angeles",
                LocalDateTime.of(2025, 12, 10, 0, 0)
        );

        assertTrue(result.isEmpty());
    }

    // -------------------------------------------------------
    // BOOKING TESTS
    // -------------------------------------------------------

    @Test
    void testBookFlight_success() {
        Flight flight = flightService.getFlights().get(0);

        Reservation reservation = flightService.bookFlight("Teja", flight, 3);

        assertNotNull(reservation);
        assertEquals("Teja", reservation.getCustomerName());
        assertEquals(47, flight.getAvailableSeats());
    }

    @Test
    void testBookFlight_fail_notEnoughSeats() {
        Flight flight = flightService.getFlights().get(0);

        Reservation reservation = flightService.bookFlight("Teja", flight, 100);

        assertNull(reservation); // booking should fail
        assertEquals(50, flight.getAvailableSeats());
    }

    @Test
    void testBookFlight_exactSeats() {
        Flight flight = flightService.getFlights().get(1); // 30 seats

        Reservation reservation = flightService.bookFlight("Teja", flight, 30);

        assertNotNull(reservation);
        assertEquals(0, flight.getAvailableSeats());
    }
}
