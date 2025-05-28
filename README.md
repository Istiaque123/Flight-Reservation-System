✈️ Flight Reservation System
A powerful, open-source Java application for seamless flight booking and management.


📋 Table of Contents

✨ About the Project
🚀 Features
🛠️ Tech Stack
🏁 Getting Started
🎮 Usage
📸 Showcase
🤝 Contributing
📜 License
📬 Contact
❓ FAQ
🌟 Acknowledgements


✨ About the Project
The Flight Reservation System (FRS) is a robust Java-based application designed to simplify flight bookings, cancellations, and management. Whether you're a traveler searching for flights or an admin managing schedules, FRS offers a fast, efficient, and user-friendly experience without relying on a database. Built with advanced data structures like AVL Trees, Tries, and Graphs, it’s perfect for developers exploring system design or contributing to open-source aviation software.
Why Choose FRS?

Fast and Efficient: Optimized search and booking with O(log n) operations.
Educational: Learn advanced algorithms (Dijkstra’s, AVL balancing) in a real-world context.
Extensible: Ready for enhancements like a GUI or database integration.



🚀 Features



Feature
Description



🔍 Flight Search
Find direct and connecting flights with city autocompletion using a Trie.


🎟️ Booking System
Book and cancel flights with dynamic seat allocation (Economy/Business).


🛡️ Admin Panel
Add, modify, or remove flights and generate seat reports.


⚡ Efficient Data Structures
AVL Tree for seats, Graph for routes, Priority Queue for sorting.


🔐 User Authentication
Secure login and registration for passengers.



🛠️ Tech Stack

Language: Java 8+
Data Structures:
Trie: Fast city name searches.
AVL Tree: Balanced seat management.
Graph: Route finding with Dijkstra’s algorithm.
HashMap: Quick booking retrieval.
Priority Queue: Flight sorting by departure time.


Storage: In-memory (no external database).
Environment: Console-based interface.


🏁 Getting Started
Prerequisites

Java Development Kit (JDK): Version 8 or higher
Git: To clone the repository
IDE: IntelliJ IDEA, Eclipse, or VS Code (optional)
OS: Windows, macOS, or Linux

Installation

Clone the Repository:
git clone https://github.com/<your-username>/<your-repo>.git
cd <your-repo>


Compile the Code:
javac FlightReservationSystem.java


Run the Application:
java Main




Tip: Ensure JDK is in your PATH. See Java Installation Guide for help.


🎮 Usage

Main Menu:Launch the app to see:
=== Flight Reservation System ===
1. User Login
2. Register
3. Admin Login
4. Exit


User Flow:

Register: Create an account with name, email, password, etc.
Search Flights: Find direct or connecting flights by city and date.
Book/Cancel: Reserve a seat or cancel a booking.
View Bookings: See all your confirmed bookings.


Admin Flow:

Log in with password admin123.
Manage flights or generate seat reports.



Demo GIF:

📸 Showcase
Sample Input/Output
Search Flights:
Enter Source City: New York
Enter Destination City: Los Angeles
Enter Date (yyyy-MM-dd): 2025-06-01

Output:
Direct Flights:
American Airlines AA101: New York to Los Angeles | Dep: 2025-06-01T08:00 | Arr: 2025-06-01T11:00 | Available: 100/100 | Price: $200.00

Connecting Flights:
Connection: [AA103: New York to Chicago | Dep: 2025-06-01T07:00 | Arr: 2025-06-01T08:30,
AA102: Chicago to Los Angeles | Dep: 2025-06-01T09:00 | Arr: 2025-06-01T11:30]

Book a Flight:
Enter Flight Number: AA101
Enter Seat Class (ECONOMY/BUSINESS): ECONOMY

Output:
Booking successful: Booking ID: <uuid> | Flight: AA101 | Seat: 21
Status: CONFIRMED | Payment: COMPLETED | Price: $200.00

Admin Report:
Enter Flight Number: AA101

Output:
Flight AA101: 1 seats booked, 99 seats available.


🤝 Contributing
We’d love your contributions! Here’s how to get started:

Fork the repository.
Create a branch: git checkout -b feature/your-feature.
Commit changes: git commit -m "Add your feature".
Push to the branch: git push origin feature/your-feature.
Open a pull request.

See CONTRIBUTING.md for guidelines and our code of conduct.
Ideas for Contributions:

Add a GUI (e.g., JavaFX, Swing).
Integrate a database (e.g., SQLite).
Enhance search with filters (price, airline).


📜 License
This project is licensed under the MIT License. Feel free to use, modify, and distribute.

📬 Contact

Author:
Email: istiaqueremon@gmial.com
GitHub: github.com/istiaque123
Twitter/X: x.com/

Got questions? Open an issue or ping me on X!

❓ FAQ

🔍 What is the default admin password?
The admin password is admin123. You can modify it in the source code for security.



🚀 Can I add a GUI?
Yes! The system is modular, so you can integrate JavaFX or Swing. Check the Contributing section for ideas.



📦 Does it support a database?
Currently, it uses in-memory storage (HashMap, AVL Tree). You can contribute to add database support like MySQL or SQLite.



🌟 Acknowledgements

freeCodeCamp for README best practices.
GitHub Docs for Markdown and repository tips.
Shields.io for awesome badges.
Canva for creating stunning project visuals.
Awesome README for inspiration.


⭐ Star this repo if you find it useful! Share your feedback or contribute to make FRS even better. Check out the project at github.com//. ✈️
