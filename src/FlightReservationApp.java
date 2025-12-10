import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class FlightReservationApp {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        FlightService flightService = new FlightService();
        seedFlights(flightService);

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Flight Reservation System ===");

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Search Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View My Reservations");
            System.out.println("4. Exit");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleSearchFlights(scanner, flightService);
                    break;
                case "2":
                    handleBookFlight(scanner, flightService);
                    break;
                case "3":
                    handleViewReservations(scanner, flightService);
                    break;
                case "4":
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void seedFlights(FlightService service) {
        // Add some sample flights
        service.addFlight(new Flight("FL100",
                "New York",
                LocalDateTime.of(2025, 1, 10, 10, 30),
                50));

        service.addFlight(new Flight("FL101",
                "New York",
                LocalDateTime.of(2025, 1, 10, 18, 0),
                30));

        service.addFlight(new Flight("FL200",
                "Chicago",
                LocalDateTime.of(2025, 1, 11, 9, 0),
                20));
    }

    private static void handleSearchFlights(Scanner scanner, FlightService service) {
        try {
            System.out.print("Enter destination: ");
            String destination = scanner.nextLine();

            System.out.print("Enter date (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine();

            LocalDateTime date = LocalDateTime.parse(dateStr + " 00:00", DATE_TIME_FORMAT);

            List<Flight> flights = service.searchFlights(destination, date);

            if (flights.isEmpty()) {
                System.out.println("No flights found for " + destination + " on " + dateStr);
            } else {
                System.out.println("Available flights:");
                flights.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void handleBookFlight(Scanner scanner, FlightService service) {
        try {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter flight number to book: ");
            String flightNumber = scanner.nextLine();

            Flight flight = service.findFlightByNumber(flightNumber);
            if (flight == null) {
                System.out.println("Flight not found.");
                return;
            }

            System.out.print("Enter number of seats: ");
            int seats = Integer.parseInt(scanner.nextLine());

            Reservation reservation = service.bookFlight(name, flight, seats);
            if (reservation != null) {
                System.out.println("Booking successful: " + reservation);
            } else {
                System.out.println("Booking failed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat number. Please enter a valid integer.");
        }
    }

    private static void handleViewReservations(Scanner scanner, FlightService service) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        List<Reservation> reservations = service.getReservationsByCustomer(name);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for " + name);
        } else {
            System.out.println("Your reservations:");
            reservations.forEach(System.out::println);
        }
    }
}
