# Flight Reservation System ✈️ <span style="background-color: #3498db; color: white; padding: 2px 8px; border-radius: 12px; font-size: 0.8em;">v1.2</span>

<div style="text-align: center; font-style: italic; color: #555;">
A Java-based console application for managing flight bookings and routes with efficiency and ease.
</div>

---

## Overview

The **Flight Reservation System** is a robust Java console application designed to streamline flight management processes. It supports user registration, flight searches (direct and connecting), booking, cancellation, administrative tasks, and detailed flight and route information. Powered by advanced data structures** like a Trie for city suggestions, a graph for route management, and an AVL tree for seat allocation, it ensures high performance and scalability.

---

## Features

- **User Management**: Register and authenticate passengers with secure email and password login.
- **Flight Search**: Search for direct and connecting flights with city prefix autocomplete powered by a Trie.
- **Booking Management**: Book flights with Economy or Business class options, cancel bookings, and view booking history.
- **Admin Operations**: Add, modify, or remove flights, generate seat availability reports, and view all flights.
- **Flight & Route Details**: View comprehensive flight details (e.g., seat breakdown) and all possible routes between cities.
- **Efficient Data Structures**:
    - **Trie**: For fast city name autocomplete.
    - **Undirected Graph**: Uses Dijkstra’s algorithm for shortest paths and DFS for all routes.
    - **AVL Tree**: Manages seat availability with balanced operations.

---

## Project Structure

FlightReservationSystem/├── src/│   ├── core/│   │   ├── CityTrie.java│   │   ├── FlightRouteGraph.java│   │   └── AVLTree.java│   ├── core/dto/│   │   ├── TrieNode.java│   │   └── AVLNode.java│   ├── database/│   │   ├── FlightDatabase.java│   │   └── UserDatabase.java│   ├── models/│   │   ├── Flight.java│   │   ├── Passenger.java│   │   ├── Booking.java│   │   └── Seat.java│   ├── resource/│   │   ├── SeatClass.java│   │   ├── BookingStatus.java│   │   └── PaymentStatus.java│   ├── services/│   │   ├── FlightSearchService.java│   │   ├── BookingService.java│   │   └── AdminService.java│   └── Main.java└── README.md

---

## Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A Java IDE (e.g., IntelliJ IDEA, Eclipse) or text editor with CLI
- Git (optional, for cloning)

### Steps

1. **Clone the repository**:
```bash
git clone https://github.com/your-org/flight-reservation-system.git


Navigate to the project directory:cd flight-reservation-system


Compile the project:

javac -d bin src/**/*.java


Run the application:

java -cp bin Main


Usage
Upon launching, the application displays the main menu:
=== Flight Reservation System ===
1. User Login
2. Register
3. Admin Login
4. Exit
Choose an option:

User Features
1. Search Flights
Search for direct or connecting flights with city suggestions.
Example Input/Output:
Enter Source City (or prefix): Ne
Suggested cities: [New York]
Enter Source City: New York
Enter Destination City: Los Angeles
Enter Date (yyyy-MM-dd): 2025-06-01

Direct Flights:
American Airlines AA101: New York to Los Angeles | Dep: 2025-06-01T08:00 | Arr: 2025-06-01T11:00 | Available: 100/100 | Price: $200.00

Connecting Flights:
Connection: [American Airlines AA103: New York to Chicago | Dep: 2025-06-01T07:00 | Arr: 2025-06-01T08:30 | Available: 120/120 | Price: $150.00, American Airlines AA102: Chicago to Los Angeles | Dep: 2025-06-01T09:00 | Arr: 2025-06-01T11:30 | Available: 80/80 | Price: $180.00]

2. Book Flight
Reserve a seat in Economy or Business class.
Example Input/Output:
Enter Flight Number: AA101
Enter Seat Class (ECONOMY/BUSINESS): BUSINESS
Booking successful: Booking ID: 123e4567-e89b-12d3-a456-426614174000 | Flight: AA101 | Passenger: 987fcdeb-1234-5678-abcd-426614174001 | Seat: 1
Status: CONFIRMED | Payment: COMPLETED | Price: $300.00 | Booked on: 2025-05-30T17:39

3. Cancel Booking
Cancel an existing booking.
Example Input/Output:
Enter Booking ID: 123e4567-e89b-12d3-a456-426614174000
Booking cancelled successfully.

4. View Bookings
View all confirmed bookings for a passenger.
Example Input/Output:
Booking ID: 123e4567-e89b-12d3-a456-426614174000 | Flight: AA101 | Passenger: 987fcdeb-1234-5678-abcd-426614174001 | Seat: 1
Status: CONFIRMED | Payment: COMPLETED | Price: $300.00 | Booked on: 2025-05-30T17:39

5. View Flight and Route Details
View detailed flight information or all possible routes.
Example Input/Output (Flight Details):
=== View Flight and Route Details ===
1. View Flight Details
2. View All Routes
Choose an option: 1
Enter Flight Number: AA101
Flight Number: AA101
Airline: American Airlines
Route: New York to Los Angeles
Departure: 2025-06-01T08:00
Arrival: 2025-06-01T11:00
Total Seats: 100
Available Seats: 90
Economy Seats: 80 (Available: 72)
Business Seats: 20 (Available: 18)
Price: $200.00

Example Input/Output (All Routes):
=== View Flight and Route Details ===
1. View Flight Details
2. View All Routes
Choose an option: 2
Enter Source City (or prefix): Mi
Suggested cities: [Miami]
Enter Source City: Miami
Enter Destination City: Los Angeles
All Possible Routes:
Route 1: [Miami, New York, Los Angeles]
Route 2: [Miami, New York, Chicago, Los Angeles]

Admin Features
1. Add Flight
Add a new flight to the database.
Example Input/Output:
Enter Flight Number: AA105
Enter Airline: Delta
Enter Source City: Los Angeles
Enter Destination City: Miami
Enter Departure Time (yyyy-MM-dd HH:mm): 2025-06-02 14:00
Enter Arrival Time (yyyy-MM-dd HH:mm): 2025-06-02 18:00
Enter Total Seats: 200
Enter Price: 250.0
Flight added successfully.

2. Modify Flight
Update an existing flight’s details.
Example Input/Output:
Enter Flight Number: AA101
Enter New Departure Time (yyyy-MM-dd HH:mm): 2025-06-01 09:00
Enter New Arrival Time (yyyy-MM-dd HH:mm): 2025-06-01 12:00
Enter New Price: 220.0
Flight modified successfully.

3. Remove Flight
Delete a flight from the database.
Example Input/Output:
Enter Flight Number: AA101
Flight removed successfully.

4. Generate Seat Report
View seat availability for a flight.
Example Input/Output:
Enter Flight Number: AA101
Flight AA101: 10 seats booked, 90 seats available.

5. View All Flights
List all flights in the database.
Example Input/Output:
American Airlines AA101: New York to Los Angeles | Dep: 2025-06-01T08:00 | Arr: 2025-06-01T11:00 | Available: 100/100 | Price: $200.00
American Airlines AA102: Chicago to Los Angeles | Dep: 2025-06-01T09:00 | Arr: 2025-06-01T11:30 | Available: 80/80 | Price: $180.00
American Airlines AA103: New York to Chicago | Dep: 2025-06-01T07:00 | Arr: 2025-06-01T08:30 | Available: 120/120 | Price: $150.00
American Airlines AA104: Miami to New York | Dep: 2025-06-01T10:00 | Arr: 2025-06-01T12:00 | Available: 150/150 | Price: $170.00

6. View Flight and Route Details
Same as user feature, accessible with admin privileges.


```