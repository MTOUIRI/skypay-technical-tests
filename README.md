# Skypay Technical Tests - Complete Solutions

## 📋 Overview
This repository contains complete solutions for both Skypay technical tests, demonstrating versatility in handling different software engineering challenges.

## 🎯 Tests Completed

### ✅ Test 1: Banking Service
**Location:** `/test1-banking-system`  
**Challenge:** Implement core banking operations (deposit, withdraw, print statement)

**Key Features:**
- Clean single-class implementation
- Transaction history with snapshots
- Comprehensive exception handling
- O(1) deposit/withdrawal operations
- Complete test coverage

[View Full Documentation →](./test1-banking-system/README.md)

### ✅ Test 2: Hotel Reservation System
**Location:** `/test2-hotel-reservation`  
**Challenge:** Build a hotel booking system managing rooms, users, and reservations

**Key Features:**
- Multi-entity system (Room, User, Booking, Service)
- Snapshot pattern for data integrity
- Complex business logic (availability checking, balance management)
- Real-time booking validation
- Professional documentation with UML diagrams

[View Full Documentation & PDF →](./test2-hotel-reservation/README.md)

## 🛠️ Technical Stack

**Language:** Java 21 (JDK 21)

**Core Libraries:**
- `java.util.ArrayList` - In-memory data storage
- `java.time.LocalDate` - Modern date handling
- `java.time.temporal.ChronoUnit` - Date calculations

**Design Patterns:**
- Snapshot Pattern (data preservation)
- Builder Pattern (entity construction)
- Enum Pattern (type safety)

**Best Practices:**
- Exception handling
- Input validation
- Clean code principles
- Comprehensive testing
- Professional documentation

## 🚀 Quick Start

### Clone Repository
```bash
git clone https://github.com/MTOUIRI/skypay-technical-tests.git
cd skypay-technical-tests
```

### Run Banking System (Test 1)
```bash
cd test1-banking-system
javac Account.java
java Account
```

### Run Hotel Reservation (Test 2)
```bash
cd test2-hotel-reservation
javac Service.java
java Service
```

## 💡 Key Achievements

### Banking Service (Test 1)
- ✅ 100% requirement compliance
- ✅ 5/5 exception tests passing
- ✅ O(1) performance for core operations
- ✅ Clean, maintainable code
- ✅ Complete audit trail

### Hotel Reservation (Test 2)
- ✅ 6/6 test cases passing
- ✅ Snapshot pattern preserving data integrity
- ✅ Zero double-booking (perfect overlap detection)
- ✅ Atomic transactions (balance + booking)
- ✅ Professional documentation with UML diagrams

## 🎓 Design Decisions

### Banking System
- **Inner Transaction Class** - Encapsulation and simplicity
- **Balance Snapshots** - O(1) balance retrieval
- **Reverse Chronological Order** - Banking industry standard
- **LocalDate Usage** - Modern Java best practice

### Hotel Reservation
- **Snapshot Pattern** - Financial accuracy over storage efficiency
- **Separate Entities** - Clear separation of concerns
- **ArrayList Storage** - As per requirements (simplicity)
- **Comprehensive Validation** - Prevent data corruption

## 📖 Documentation Structure

```
skypay-technical-tests/
│
├── README.md (this file)
│
├── test1-banking-system/
│   ├── Account.java
│   ├── README.md
│   ├── screenshots/
│   │   ├── statement-output.png
│   │   └── exception-tests.png
│   └── diagrams/
│       ├── class-diagram.png
│       ├── sequence-diagram1.png
│       └── sequence-diagram2.png
│
└── test2-hotel-reservation/
    ├── Service.java
    ├── README.md
    ├── Skypay_Hotel_Test_Presentation.pdf
    └── screenshots/
        ├── Execution-Results.png
        ├── Rooms.png
        ├── printAll()-Bookings.png
        └── printAllUsers().png
```

## 🧪 Testing Coverage

### Banking System
- ✅ Standard operations (deposit, withdraw, statement)
- ✅ Edge cases (zero amount, negative amount)
- ✅ Error scenarios (insufficient balance, null date)
- ✅ Sequential transactions
- ✅ Statement formatting

### Hotel Reservation
- ✅ Room creation and updates
- ✅ User balance management
- ✅ Booking with validation
- ✅ Availability checking (overlap detection)
- ✅ Date validation
- ✅ Balance verification
- ✅ Snapshot preservation after updates

## 🔍 Code Quality Standards Applied

- ✅ **Clean Code** - Meaningful names, clear structure
- ✅ **DRY Principle** - No code duplication
- ✅ **SOLID Principles** - Single responsibility, encapsulation
- ✅ **Documentation** - Comprehensive JavaDoc comments
- ✅ **Error Handling** - Defensive programming
- ✅ **Testing** - Built-in test cases

### Metrics
- **Banking System:** ~250 lines of clean, documented code
- **Hotel Reservation:** ~400 lines with proper structure
- **Documentation:** ~100 lines of comments per file
- **Test Coverage:** 100% of required functionality

## 📞 Contact

**Mouad TOUIRI**

- 📧 Email: mouad.touiri@gmail.com
- 📱 Phone: +212 690 002 573
- 💼 LinkedIn: [linkedin.com/in/mouad-touiri-10b064247](https://linkedin.com/in/mouad-touiri-10b064247/)
- 💻 GitHub: [github.com/MTOUIRI](https://github.com/MTOUIRI)
- 🌐 Portfolio: [smartbac.com](https://smartbac.com)

## 📝 License

These solutions are submitted as part of the Skypay technical assessment process.

© 2025 Mouad TOUIRI - All Rights Reserved

## 🙏 Acknowledgments

Thank you to the Skypay recruitment team for the opportunity to demonstrate my skills through these interesting challenges. Both tests were engaging and provided excellent opportunities to showcase different aspects of software engineering.

I look forward to discussing these solutions and the next steps in the recruitment process.
