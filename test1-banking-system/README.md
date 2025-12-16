# Banking Service - Technical Test 1

## Overview

Implementation of a core banking system supporting deposits, withdrawals, and statement printing.

**Author:** Mouad TOUIRI  
**Date:** December 2025  

---

## Features Implemented

✅ **Deposit Money** - Add funds to account with date tracking  
✅ **Withdraw Money** - Remove funds with balance validation  
✅ **Print Statement** - Display transaction history (newest first)  
✅ **Exception Handling** - Comprehensive error handling  
✅ **Transaction History** - Complete audit trail with dates and balances  

---

## Technical Implementation

### Class Structure

```
Account.java
├── Transaction (Inner Class)
│   ├── date: LocalDate
│   ├── amount: int
│   ├── type: TransactionType (DEPOSIT/WITHDRAWAL)
│   └── balanceAfter: int
│
├── TransactionType (Enum)
│   ├── DEPOSIT
│   └── WITHDRAWAL
│
└── Account (Main Class)
    ├── balance: int
    ├── transactions: ArrayList<Transaction>
    ├── deposit(amount, date)
    ├── withdraw(amount, date)
    └── printStatement()
```

---

## Design Decisions

### 1. **Transaction History with Snapshots**
- Each transaction stores the balance **after** the operation
- Provides complete audit trail
- Enables accurate statement printing

### 2. **Inner Transaction Class**
- Encapsulates transaction details
- Keeps code organized and maintainable
- No need for separate file (simplicity)

### 3. **Enum for Transaction Types**
- Type-safe transaction categorization
- Easy to extend for future transaction types
- Clear code readability

### 4. **LocalDate for Dates**
- Modern Java date API (Java 8+)
- Better than deprecated `Date` class
- Cleaner date manipulation

### 5. **ArrayList for Storage**
- As per requirements (no repositories)
- Simple and efficient for this use case
- Easy to iterate in reverse for statement printing

---

## Exception Handling

The implementation handles various error scenarios:

| Scenario | Exception | Message |
|----------|-----------|---------|
| Negative deposit | `IllegalArgumentException` | "Deposit amount must be positive" |
| Negative withdrawal | `IllegalArgumentException` | "Withdrawal amount must be positive" |
| Insufficient balance | `IllegalStateException` | "Insufficient balance. Available: X, Requested: Y" |
| Null date | `IllegalArgumentException` | "Date cannot be null" |
| Zero amount | `IllegalArgumentException` | "Amount must be positive" |

---

## Test Case (From Specification)

### Given:
- Client makes a deposit of **1000** on **10-01-2012**
- And a deposit of **2000** on **13-01-2012**
- And a withdrawal of **500** on **14-01-2012**

### When:
- They print their bank statement

### Then:
```
Date       | Amount | Balance
14-01-2012 | -500   | 2500
13-01-2012 | +2000  | 3000
10-01-2012 | +1000  | 1000
```

---

## How to Run

### Compile:
```bash
javac Account.java
```

### Run:
```bash
java Account
```

### Expected Output:
```
========== BANKING SERVICE TEST ==========

--- Performing Transactions ---

Deposit successful: 1000 on 10-01-2012
Deposit successful: 2000 on 13-01-2012
Withdrawal successful: 500 on 14-01-2012

--- Bank Statement ---

Date       | Amount | Balance
14-01-2012 | -500   | 2500
13-01-2012 | +2000  | 3000
10-01-2012 | +1000  | 1000

--- Account Summary ---
Current Balance: 2500
Total Transactions: 3

========== ADDITIONAL TEST CASES ==========

--- Testing Exception Handling ---

Test 1: Attempting negative deposit...
✅ PASSED: Deposit amount must be positive. Received: -100

Test 2: Attempting withdrawal with insufficient balance...
✅ PASSED: Insufficient balance. Available: 0, Requested: 1000

Test 3: Attempting deposit with null date...
✅ PASSED: Date cannot be null

Test 4: Attempting zero deposit...
✅ PASSED: Deposit amount must be positive. Received: 0

Test 5: Valid transaction sequence...
Deposit successful: 1000 on 2024-12-16
Withdrawal successful: 500 on 2024-12-16
✅ PASSED: Balance = 500

--- All Exception Tests Completed ---
```

---

## Key Features

### 1. **Transaction Ordering**
- Statements print in **reverse chronological order** (newest first)
- Matches standard banking practice
- Easy to see recent activity

### 2. **Balance Tracking**
- Balance calculated and stored after each transaction
- No need to recalculate on statement printing
- O(1) balance retrieval

### 3. **Comprehensive Testing**
- Main test case from specification ✅
- Additional exception handling tests ✅
- Edge case validation ✅

### 4. **Clean Code Principles**
- Single Responsibility Principle
- Clear method names
- Comprehensive documentation
- Proper encapsulation

---

## Performance Considerations

✅ **Deposit/Withdraw: O(1)** - Constant time operations  
✅ **Print Statement: O(n)** - Linear time, where n = number of transactions  
✅ **Get Balance: O(1)** - Stored, not calculated  
✅ **Memory Efficient** - Only essential data stored  

---

## Requirements Compliance

| Requirement | Status | Implementation |
|-------------|--------|----------------|
| Cannot change public interface | ✅ | Exact method signatures maintained |
| Handle exceptions | ✅ | Comprehensive error handling |
| Performance efficient | ✅ | O(1) for deposits/withdrawals |
| Use ArrayList (no repositories) | ✅ | ArrayList for transaction storage |
| Use ints for money | ✅ | All amounts are int type |
| Test code well | ✅ | Main method with test cases |

---

## Future Enhancements

If this were a production system:

1. **Money Type**: Use `BigDecimal` for arbitrary precision
2. **Persistence**: Add database integration
3. **Thread Safety**: Synchronize methods for concurrent access
4. **Transfer Operation**: Add account-to-account transfers
5. **Interest Calculation**: Add interest on balance
6. **Transaction Categories**: Expand transaction types
7. **Statement Filters**: Filter by date range, type, amount

---

## Technologies Used

- **Java 21** (JDK 21)
- **java.time.LocalDate** (Modern date handling)
- **java.util.ArrayList** (Transaction storage)
- **java.time.format.DateTimeFormatter** (Date formatting)

---

## Conclusion

This implementation provides a solid foundation for a banking system with:
- ✅ Clean, maintainable code
- ✅ Proper exception handling
- ✅ Comprehensive testing
- ✅ Efficient performance
- ✅ Complete requirement compliance

The solution balances simplicity with best practices, making it both easy to understand and production-ready.

---

**Contact:**  
Mouad TOUIRI  
📧 mouad.touiri@gmail.com  
💼 [LinkedIn](linkedin.com/in/mouad-touiri-10b064247/)  
💻 [GitHub](https://github.com/MTOUIRI)
