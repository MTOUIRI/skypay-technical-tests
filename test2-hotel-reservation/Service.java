import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Enum for room types
enum RoomType {
    STANDARD, JUNIOR, MASTER
}

// User Entity
class User {
    private int userId;
    private int balance;

    public User(int userId, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.userId = userId;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public int getBalance() {
        return balance;
    }

    public void deductBalance(int amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return "User{ID=" + userId + ", Balance=" + balance + "}";
    }
}

// Room Entity
class Room {
    private int roomNumber;
    private RoomType roomType;
    private int pricePerNight;

    public Room(int roomNumber, RoomType roomType, int pricePerNight) {
        if (pricePerNight < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setPricePerNight(int pricePerNight) {
        if (pricePerNight < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString() {
        return "Room{Number=" + roomNumber + ", Type=" + roomType + ", Price/Night=" + pricePerNight + "}";
    }
}

// Booking Entity
class Booking {
    private int bookingId;
    private static int bookingCounter = 0;
    
    // Store snapshot data at booking time
    private int userId;
    private int userBalanceAtBooking;
    private int roomNumber;
    private RoomType roomTypeAtBooking;
    private int roomPriceAtBooking;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int totalCost;
    private LocalDate bookingDate;

    public Booking(User user, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.bookingId = ++bookingCounter;
        
        // Validate dates
        if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            throw new IllegalArgumentException("Invalid dates: Check-in must be before check-out");
        }
        
        // Store snapshot of user and room data at booking time
        this.userId = user.getUserId();
        this.userBalanceAtBooking = user.getBalance();
        this.roomNumber = room.getRoomNumber();
        this.roomTypeAtBooking = room.getRoomType();
        this.roomPriceAtBooking = room.getPricePerNight();
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bookingDate = LocalDate.now();
        
        // Calculate total cost
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        this.totalCost = (int) nights * roomPriceAtBooking;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public boolean overlapsWith(LocalDate start, LocalDate end) {
        return !(end.isBefore(checkIn) || end.isEqual(checkIn) || 
                 start.isAfter(checkOut) || start.isEqual(checkOut));
    }

    @Override
    public String toString() {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        return "Booking{ID=" + bookingId + 
               ", User{ID=" + userId + ", Balance=" + userBalanceAtBooking + "}" +
               ", Room{Number=" + roomNumber + ", Type=" + roomTypeAtBooking + 
               ", Price=" + roomPriceAtBooking + "}" +
               ", CheckIn=" + checkIn + ", CheckOut=" + checkOut + 
               ", Nights=" + nights + ", TotalCost=" + totalCost + "}";
    }
}

// Service Class
public class Service {
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private ArrayList<Booking> bookings;

    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Creates or updates a room
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        try {
            Room existingRoom = findRoomByNumber(roomNumber);
            if (existingRoom != null) {
                // Update existing room without affecting bookings
                existingRoom.setRoomType(roomType);
                existingRoom.setPricePerNight(roomPricePerNight);
                System.out.println("Room " + roomNumber + " updated successfully");
            } else {
                // Create new room
                Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
                rooms.add(newRoom);
                System.out.println("Room " + roomNumber + " created successfully");
            }
        } catch (Exception e) {
            System.out.println("Error setting room: " + e.getMessage());
        }
    }

    // Creates a user if doesn't exist
    public void setUser(int userId, int balance) {
        try {
            User existingUser = findUserById(userId);
            if (existingUser == null) {
                User newUser = new User(userId, balance);
                users.add(newUser);
                System.out.println("User " + userId + " created successfully");
            } else {
                System.out.println("User " + userId + " already exists");
            }
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    // Books a room for a user
    public void bookRoom(int userId, int roomNumber, Date checkInDate, Date checkOutDate) {
        try {
            // Convert Date to LocalDate
            LocalDate checkIn = convertToLocalDate(checkInDate);
            LocalDate checkOut = convertToLocalDate(checkOutDate);

            // Validate dates
            if (checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
                System.out.println("Booking failed: Invalid dates. Check-in must be before check-out");
                return;
            }

            // Find user and room
            User user = findUserById(userId);
            Room room = findRoomByNumber(roomNumber);

            if (user == null) {
                System.out.println("Booking failed: User " + userId + " not found");
                return;
            }

            if (room == null) {
                System.out.println("Booking failed: Room " + roomNumber + " not found");
                return;
            }

            // Check if room is available
            if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
                System.out.println("Booking failed: Room " + roomNumber + " is not available for selected dates");
                return;
            }

            // Calculate total cost
            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            int totalCost = (int) nights * room.getPricePerNight();

            // Check user balance
            if (user.getBalance() < totalCost) {
                System.out.println("Booking failed: Insufficient balance. Required: " + totalCost + ", Available: " + user.getBalance());
                return;
            }

            // Create booking and deduct balance
            Booking booking = new Booking(user, room, checkIn, checkOut);
            user.deductBalance(totalCost);
            bookings.add(booking);

            System.out.println("Booking successful! User " + userId + " booked Room " + roomNumber + 
                             " for " + nights + " nights. Total cost: " + totalCost);

        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

    // Print all rooms and bookings (latest to oldest)
    public void printAll() {
        System.out.println("\n========== ALL ROOMS (Latest to Oldest) ==========");
        if (rooms.isEmpty()) {
            System.out.println("No rooms available");
        } else {
            for (int i = rooms.size() - 1; i >= 0; i--) {
                System.out.println(rooms.get(i));
            }
        }

        System.out.println("\n========== ALL BOOKINGS (Latest to Oldest) ==========");
        if (bookings.isEmpty()) {
            System.out.println("No bookings available");
        } else {
            for (int i = bookings.size() - 1; i >= 0; i--) {
                System.out.println(bookings.get(i));
            }
        }
        System.out.println();
    }

    // Print all users (latest to oldest)
    public void printAllUsers() {
        System.out.println("\n========== ALL USERS (Latest to Oldest) ==========");
        if (users.isEmpty()) {
            System.out.println("No users available");
        } else {
            for (int i = users.size() - 1; i >= 0; i--) {
                System.out.println(users.get(i));
            }
        }
        System.out.println();
    }

    // Helper: Find room by number
    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    // Helper: Find user by ID
    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    // Helper: Check room availability
    private boolean isRoomAvailable(int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                if (booking.overlapsWith(checkIn, checkOut)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Helper: Convert Date to LocalDate (only year, month, day)
    private LocalDate convertToLocalDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return LocalDate.of(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH)
        );
    }

    // Main method with test case
    public static void main(String[] args) {
        Service service = new Service();

        System.out.println("========== CREATING ROOMS ==========");
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.MASTER, 3000);

        System.out.println("\n========== CREATING USERS ==========");
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        System.out.println("\n========== BOOKING ATTEMPTS ==========");
        
        // Test 1: User 1 books Room 2 for 7 nights (30/06/2026 to 07/07/2026)
        service.bookRoom(1, 2, new Date(2026 - 1900, 5, 30), new Date(2026 - 1900, 6, 7));

        // Test 2: User 1 tries invalid dates (checkOut before checkIn)
        service.bookRoom(1, 2, new Date(2026 - 1900, 6, 7), new Date(2026 - 1900, 5, 30));

        // Test 3: User 1 books Room 1 for 1 night (07/07/2026 to 08/07/2026)
        service.bookRoom(1, 1, new Date(2026 - 1900, 6, 7), new Date(2026 - 1900, 6, 8));

        // Test 4: User 2 books Room 1 for 2 nights (07/07/2026 to 09/07/2026) - should fail (overlap)
        service.bookRoom(2, 1, new Date(2026 - 1900, 6, 7), new Date(2026 - 1900, 6, 9));

        // Test 5: User 2 books Room 3 for 1 night (07/07/2026 to 08/07/2026)
        service.bookRoom(2, 3, new Date(2026 - 1900, 6, 7), new Date(2026 - 1900, 6, 8));

        // Test 6: Update Room 1 (should not affect existing bookings)
        System.out.println("\n========== UPDATING ROOM 1 ==========");
        service.setRoom(1, RoomType.MASTER, 10000);

        // Print final results
        service.printAll();
        service.printAllUsers();
    }
}