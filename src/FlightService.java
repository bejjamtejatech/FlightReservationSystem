import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService {

    private List<Flight> flights = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    // Seed some initial flights (or you can add via code)
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> searchFlights(String destination, LocalDateTime date) {
        return flights.stream()
                .filter(f -> f.getDestination().equalsIgnoreCase(destination)
                        && f.getDepartureTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }

    public Reservation bookFlight(String customerName, Flight flight, int seats) {
        if (seats <= 0) {
            System.out.println("Seats must be greater than 0.");
            return null;
        }

        if (flight.getAvailableSeats() < seats) {
            System.out.println("Not enough seats available on flight " + flight.getFlightNumber());
            return null;
        }

        flight.reduceSeats(seats);
        Reservation reservation = new Reservation(customerName, flight, seats);
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getReservationsByCustomer(String customerName) {
        return reservations.stream()
                .filter(r -> r.getCustomerName().equalsIgnoreCase(customerName))
                .collect(Collectors.toList());
    }

    public Flight findFlightByNumber(String flightNumber) {
        return flights.stream()
                .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);
    }
}
