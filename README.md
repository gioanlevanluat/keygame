# 🎮 Spring Digital Store – Sell Game Keys & Digital Products

A fully functional e-commerce web application built with **Spring Boot**, designed to sell **game keys** or other **digital goods** with **PayPal payment integration** and **automated delivery**. Manage products, orders, users, and get paid instantly.

---

## ✨ Features

- 🛒 **Product Management**
  - Add/edit products with title, description, price, and category
  - Upload digital content (game keys, download files)
  
- 💳 **PayPal Integration**
  - Seamless checkout using PayPal REST API
  - Supports both Sandbox and Live environments

- 📦 **Automated Delivery**
  - Instantly send game keys or download links to the buyer’s email after successful payment

- 📑 **Order Management**
  - Order tracking and status (Pending, Paid, Delivered)
  - View purchase history

- 👤 **User Management**
  - Registration, login, user roles (Admin/User)

- 📊 **Admin Dashboard**
  - Overview of revenue, order statistics, and system activity
  - Manage users and inventory

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: MySQL / PostgreSQL
- **Frontend**: Thymeleaf (or Vue/React if extended)
- **Payment**: PayPal REST API
- **Email Service**: Spring Mail + SMTP

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/spring-digital-store.git
cd spring-digital-store
2. Configure Application Properties
Edit application.yml or application.properties:

yaml
Sao chép
Chỉnh sửa
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/digital_store
    username: root
    password: yourpassword

paypal:
  client-id: YOUR_PAYPAL_CLIENT_ID
  client-secret: YOUR_PAYPAL_CLIENT_SECRET
  mode: sandbox

spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your_email@gmail.com
    password: your_email_password
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
3. Run the Application
bash
Sao chép
Chỉnh sửa
./mvnw spring-boot:run
📬 How Delivery Works
After successful payment:

The user receives a game key or file download link via email

The system marks the order as Delivered

📁 Project Structure
arduino
Sao chép
Chỉnh sửa
├── controller/
├── entity/
├── repository/
├── service/
├── config/
├── util/
├── templates/       # Thymeleaf (optional)
├── static/          # CSS / JS / Images
└── resources/
