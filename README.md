# CardVault

Author: Hao Fu (Nia)  
Date: September 2025

A simple Spring Boot application to store and search cardholder information securely.
- **Create** a card (name + PAN, encrypted with AES-GCM)
- **Search** by last 4 digits (optionally by name)
- **Output** masked PAN, name, and created time
- **Frontend:** simple HTML form at [ http://localhost:8080/index.html ]
---

## Features
- Add a new card:
   - Cardholder name
   - PAN (encrypted with AES-GCM)
- Search cards by last 4 digits (optional filter by name)
- Safe output:
   - Masked PAN (**** **** 1234)
   - Cardholder name
   - Created time (UTC)
- Basic HTML frontend for add/search

---

## Tech Stack
- Java 17
- Spring Boot 3
- H2 in-memory database (dev/demo)
- AES encryption (Javax Crypto)
- HTML/CSS/JS frontend (ElementUI-like styling, no frameworks)

---

## Database Structure
Two tables:

1) **cards**
   - `id` (UUID, PK)
   - `cardholder_name`
   - `last4` *(indexed)*
   - `create_at` *(UTC creation time)*

2) **card_secrets**
   - `card_id` (PK & FK → cards.id; shared primary key)
   - `pan_ciphertext` *(Base64)*
   - `pan_iv` *(Base64, per-row random IV)*

Relation: **1:1** (`cards` ↔ `card_secrets`, shared PK via `@MapsId`).

---

## Additional Deliverable — DB Design & Rationale
**Why two tables?**
- **Separation of concerns:** non-sensitive fields (name, last4, timestamps) stay in `cards`; sensitive data (encrypted PAN) is isolated in `card_secrets`.
- **Least privilege & performance:** most queries only need `cards` (search by `last4`); sensitive payload is loaded only when required.
- **Security-in-depth:** isolating encrypted PAN reduces accidental exposure and makes auditing/rotation easier.

**Key design choices**
- **Shared PK (UUID) & 1:1 mapping (`@MapsId`):** guarantees each `card_secrets` row corresponds to exactly one `cards` row; simplifies joins and integrity.
- **Index on `last4`:** meets the requirement “search by last 4 digits” efficiently.
- **AES-GCM with per-row random IV:** provides confidentiality + integrity (auth tag). IV stored alongside ciphertext for deterministic decryption.
- **No plaintext PAN at rest or in logs:** only `last4` is persisted for search/display; responses return masked PAN.

**Trade-offs & extensions**
- **H2 in-memory** used for demo; can swap to Postgres/MySQL with the same schema.
- Optional hardening (out of scope for this task): KMS-backed key management, envelope encryption, PAN format validation (Luhn), and audit trails.

---

## Run & Access
1) Build & run:
```bash
./mvnw spring-boot:run

