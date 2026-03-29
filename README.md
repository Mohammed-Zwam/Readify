<div align="center">

# 📚 Borrowly Platform

### A Production-Grade Full-Stack Library Management System

*Manage books, borrowing cycles, reservations, fines, subscriptions, reviews, wishlists, and analytics — built for real-world use.*

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Next.js](https://img.shields.io/badge/Next.js-13-black?style=flat-square&logo=next.js)
![Status](https://img.shields.io/badge/Status-In_Progress-yellow?style=flat-square)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Progress](#-progress)
- [Modules](#-modules)
- [Database Schema](#-database-schema)
- [Getting Started](#-getting-started)
- [API Reference](#-api-reference)
- [Roadmap](#-roadmap)

---

## 🔍 Overview

Borrowly is a comprehensive library management platform built with a clean layered architecture, covering everything from book cataloging to subscription billing and payment processing.

Supports two roles: `USER` / `ADMIN` with JWT-based stateless authentication and Google OAuth2 login.

| Metric | Value |
|--------|-------|
| Core Modules | 10 |
| Database Tables | 12 |
| Auth Levels | 3 (PUBLIC / USER / ADMIN) |
| Payment Gateways | Stripe / PayPal |

---

## 🛠 Tech Stack

### Backend
| Technology | Purpose |
|-----------|---------|
| Spring Boot 3.x | Core framework |
| Spring Security 6 | Auth & authorization |
| Spring Data JPA + Hibernate | ORM & database layer |
| JWT (stateless) | Token-based authentication |
| OAuth2 — Google | Social login |
| Bean Validation (@Valid) | Request validation |
| Scheduled Tasks (CRON) | Background jobs |
| PostgreSQL 16 | Primary database |
| SMTP | Email service |
| Stripe / PayPal | Payment gateways |

### Frontend *(In Progress)*
| Technology | Purpose |
|-----------|---------|
| Next.js 13 | React framework |
| TailwindCSS | Utility styling |
| Redux Toolkit | State management |
| React Query / Axios | API client |
| React Router DOM | Client-side routing |
| MUI (Material UI) | Component library |

### API Docs
| Tool | Purpose |
|------|---------|
| Scalar / OpenAPI | API schema & client generation |

---

## 🏗 Architecture

```
Borrowly-Platform/
├── Backend/
│   └── src/main/java/
│       ├── auth/               ✅ Done
│       ├── user/               ✅ Done
│       ├── book/               ✅ Done
│       ├── categories/         ✅ Done
│       ├── bookloan/           🔄 In Progress
│       ├── review/             ⏳ Planned
│       ├── fine/               ⏳ Planned
│       ├── reservation/        ⏳ Planned
│       ├── subscription/       ⏳ Planned
│       ├── subscriptionplan/   ⏳ Planned
│       ├── wishlist/           ⏳ Planned
│       └── payment/            ⏳ Planned
│
└── Frontend/                   ⏳ Planned
    └── src/
        ├── pages/
        ├── components/
        ├── store/
        └── services/
```

**Request Flow:**
```
Client → Spring Security Filter → JWT Validation → Controller → Service → Repository → PostgreSQL
                                        ↓
                              OAuth2 (Google) for social login
```

---

## ✅ Progress

### Backend Modules

| Module | Status | Notes |
|--------|--------|-------|
| 🔐 Authentication | ✅ Done | JWT + Google OAuth2 + Password Reset |
| 👤 Users | ✅ Done | Profile management, roles, stats |
| 📖 Books | ✅ Done | Full CRUD, bulk ops, soft delete, advanced search |
| 🏷️ Categories | ✅ Done | Unlimited depth hierarchy, bulk ops |
| 📋 Book Loans | 🔄 In Progress | Checkout/Return/Renewal + CRON overdue detection |
| ⭐ Reviews | ⏳ Planned | Verified reader only, helpful voting |
| 💸 Fines | ⏳ Planned | Multi-fine per loan, partial pay, waiver |
| 🔖 Reservations | ⏳ Planned | Queue system with auto-expiry |
| 💳 Subscriptions | ⏳ Planned | Full lifecycle: subscribe → renew → cancel |
| 📦 Subscription Plans | ⏳ Planned | Plan definitions with borrow limits |
| ❤️ Wishlist | ⏳ Planned | Personal bookmarks with notes |
| 💰 Payments | ⏳ Planned | Stripe + PayPal + Webhooks + retry logic |

### Frontend

| Section | Status |
|---------|--------|
| Project Setup (Next.js) | ⏳ Planned |
| Auth Pages | ⏳ Planned |
| Book Catalog & Search | ⏳ Planned |
| User Dashboard | ⏳ Planned |
| Admin Dashboard | ⏳ Planned |
| Payment Integration | ⏳ Planned |

---

## 📦 Modules

<details>
<summary><b>🔐 Authentication — <code>/auth</code></b></summary>

Stateless JWT authentication with Google OAuth2 support and password recovery flow.

| Method | Endpoint | Access |
|--------|----------|--------|
| POST | `/auth/signup` | PUBLIC |
| POST | `/auth/login` | PUBLIC |
| POST | `/auth/forgot-password` | PUBLIC |
| POST | `/auth/reset-password` | PUBLIC |

```
Signup → JWT Issued
Login  → Token Response
Forgot Password → Email Token → Reset Done
Google OAuth2   → JWT Issued
```
</details>

<details>
<summary><b>👤 Users — <code>/api/users</code></b></summary>

Profile management with dual roles (USER / ADMIN) and per-user statistics.

| Method | Endpoint | Access |
|--------|----------|--------|
| GET | `/api/users/profile` | USER/ADMIN |
| GET | `/api/users/list` | ADMIN |
| GET | `/api/users/{userId}` | PUBLIC |
| GET | `/api/users/statistics` | ADMIN |
</details>

<details>
<summary><b>📖 Books — <code>/api/books</code></b></summary>

Full book catalog with multi-criteria search, bulk operations, inventory tracking, and soft delete.

**Business Rules:**
- `availableCopies` must never exceed `totalCopies`
- `ISBN` must be unique system-wide
- Deletion sets `active = false` (soft delete)

| Method | Endpoint | Access |
|--------|----------|--------|
| POST | `/api/books` | ADMIN |
| POST | `/api/books/bulk` | ADMIN |
| GET | `/api/books/{id}` | PUBLIC |
| GET | `/api/books/isbn/{isbn}` | PUBLIC |
| GET | `/api/books` | PUBLIC |
| POST | `/api/books/search` | PUBLIC |
| PUT | `/api/books/{id}` | ADMIN |
| DELETE | `/api/books/{id}` | ADMIN |
| DELETE | `/api/books/{id}/permanent` | ADMIN ⚠️ |
</details>

<details>
<summary><b>🏷️ Categories — <code>/api/categories</code></b></summary>

Self-referential hierarchy with unlimited depth and bulk operations support.

**Business Rules:**
- Category code: uppercase letters + underscores only
- Supports parent-child nesting at any level
</details>

<details>
<summary><b>📋 Book Loans — <code>/api/book-loans</code></b></summary>

Full lifecycle management from checkout to return with automatic overdue detection via CRON.

```
CHECKED_OUT → OVERDUE → RETURNED
```

**Renewal Rule:** Only allowed if loan is not overdue and `renewalCount < maxRenewals` (default: 2).

**CRON Job:** Runs daily to detect and flag overdue loans automatically.
</details>

<details>
<summary><b>💸 Fines — <code>/api/fines</code></b></summary>

Multiple fine types per loan with partial payment and admin waiver support.

```
PENDING → PARTIALLY_PAID → PAID
        ↘ WAIVED
```

**Fine Types:** `OVERDUE` | `DAMAGE` | `LOST`
</details>

<details>
<summary><b>🔖 Reservations — <code>/api/reservations</code></b></summary>

Queue system — books held 48–72h before auto-expiry.

```
PENDING → AVAILABLE → FULFILLED
        ↘ EXPIRED
        ↘ CANCELLED
```
</details>

<details>
<summary><b>💳 Subscriptions — <code>/api/subscriptions</code></b></summary>

**Borrow Gate:** Active subscription required to checkout.

```
subscribe() → Payment Processing → ACTIVE → EXPIRED / CANCELLED
```
</details>

<details>
<summary><b>💰 Payments — <code>/api/payments</code></b></summary>

Handles `FINE` and `MEMBERSHIP` payments with max 3 retry attempts.

```
Initiate → PENDING → PROCESSING → SUCCESS
                                ↘ FAILED → Retry ≤ 3
```

Gateways: **Stripe** + **PayPal**
</details>

---

## 🗄 Database Schema

```
users ───────────────────────────────────────────────────────────┐
│                                                                 │
├──< book_loans >──< fines                                        │
│        └──< books >──< categories (self-ref hierarchy)         │
│                                                                 │
├──< reservations >──< books                                      │
├──< subscriptions >──< subscription_plans                        │
├──< payments >──< fines / subscriptions                          │
├──< book_reviews >──< books    (unique: user_id + book_id)       │
└──< wishlist >──< books        (unique: user_id + book_id)       │
```

**Key Constraints:**
- ISBN unique system-wide
- One review per user per book
- No duplicate wishlist entries per user
- Subscription plan name unique
- Category code: uppercase letters + underscore only

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- PostgreSQL 16+
- Maven 3.9+
- Node.js 20+ *(for frontend)*

### Backend Setup

```bash
git clone https://github.com/Mohammed-Zwam/Borrowly-Platform.git
cd Borrowly-Platform/Backend

cp src/main/resources/application.example.properties src/main/resources/application.properties

# Edit application.properties:
# → PostgreSQL credentials
# → JWT secret
# → Google OAuth2 keys
# → Stripe / PayPal keys
# → SMTP config

./mvnw spring-boot:run
```

### Frontend Setup *(Planned)*

```bash
cd Borrowly-Platform/Frontend
npm install
npm run dev
```

---

## 📡 API Reference

**Base URL:** `http://localhost:8080`

All protected endpoints require:
```
Authorization: Bearer <JWT_TOKEN>
```

| Module | Path |
|--------|------|
| Auth | `/auth` |
| Users | `/api/users` |
| Books | `/api/books` |
| Categories | `/api/categories` |
| Book Loans | `/api/book-loans` |
| Reviews | `/api/reviews` |
| Fines | `/api/fines` |
| Reservations | `/api/reservations` |
| Subscriptions | `/api/subscriptions` |
| Subscription Plans | `/api/subscription-plans` |
| Wishlist | `/api/wishlist` |
| Payments | `/api/payments` |

> 📖 Full interactive docs available via **Scalar / OpenAPI** at `http://localhost:8080/docs`

---

## 🗺 Roadmap

**Phase 1 — Backend Core** *(Current)*
- [x] Setup & configuration
- [x] Authentication (JWT + Google OAuth2)
- [x] Users module
- [x] Books module (CRUD + bulk + search)
- [x] Categories module (hierarchy)
- [ ] Book Loans + CRON overdue detection
- [ ] Reviews module
- [ ] Fines module
- [ ] Reservations queue system

**Phase 2 — Backend Business Logic**
- [ ] Subscriptions + Plans
- [ ] Wishlist
- [ ] Payment integration (Stripe + PayPal)
- [ ] Webhook handlers
- [ ] Email notifications
- [ ] Full CRON jobs suite

**Phase 3 — Frontend (Next.js)**
- [ ] Project setup
- [ ] Auth pages (Login, Register, Google OAuth)
- [ ] Book catalog with search & filters
- [ ] User dashboard (loans, reservations, fines)
- [ ] Admin dashboard (analytics, management)
- [ ] Payment checkout flow

**Phase 4 — Production**
- [ ] Dockerize the application
- [ ] CI/CD pipeline (GitHub Actions)
- [ ] Deploy backend (Railway / Render)
- [ ] Deploy frontend (Vercel)
- [ ] API documentation (Scalar / OpenAPI)

---

<div align="center">

Built with ☕ and Spring Boot

**Borrowly Platform — Mohammed Zwam**

</div>