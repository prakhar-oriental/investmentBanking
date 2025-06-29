# 💸 Investment Banking Backend Application

This is a Spring Boot backend-only project that simulates a simplified investment banking system, allowing users to buy/sell investment products (stocks or mutual funds), track their portfolio, receive transaction confirmations via email, and fetch live market NAV updates via Alpha Vantage API.

---

## 🚀 Features

### 👥 User Management
- JWT-based authentication and role-based access
- User registration and login

### 📦 Investment Products
- Add, update, and manage investment products (stocks, mutual funds)
- Fetch real-time NAV values using **Alpha Vantage API**

### 💰 Buy/Sell Transactions
- Buy or sell any investment product
- Auto-updates portfolio and transaction records

### 📊 Portfolio Management
- Track user portfolio with:
  - Product name, quantity
  - Invested amount
  - Live NAV
  - Current value
  - Gain/Loss

### 🧾 Transaction History
- View full history of all buy/sell operations per user

### 📥 Email Invoice (PDF)
- Sends transaction confirmation PDF after every buy/sell
- Fields include product, NAV, price, quantity, time

### 🔄 Live NAV Update
- Fetch and update NAV values using Alpha Vantage API
- Supports manual and scheduled NAV refresh

---

## 🛠 Tech Stack

| Layer        | Technology                  |
|-------------|-----------------------------|
| Backend      | Spring Boot, Spring Data JPA |
| Security     | Spring Security + JWT       |
| DB           | MySQL                       |
| External API | Alpha Vantage (live NAV)    |
| Mail         | Jakarta Mail (SMTP)         |
| PDF Report   | OpenPDF / iText             |

---


# DB Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/investmentdb
spring.datasource.username=root
spring.datasource.password=root

# Alpha Vantage API
alpha.vantage.api.key=YOUR_API_KEY

# Mail Config (use app password for Gmail)
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

👨‍💻 Author
Prakhar Rathore
Backend Developer | Java + Spring Boot Enthusiast

Create investmentdb schema and run table creation if needed. Spring JPA can auto-create tables on startup.


