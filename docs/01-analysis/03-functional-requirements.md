# Functional Requirements

---

# 1. Introduction

This document defines the functional requirements of the TekhouFeha platform. It describes the system functionalities that must be implemented to satisfy the business requirements and support the Minimum Viable Product (MVP).

---

# 2. User Management

## FR-001 User Registration

The system shall allow visitors to create a new account using:

- First Name
- Last Name
- Email Address
- Password

The email address shall be unique.

---

## FR-002 User Authentication

The system shall allow registered users to log in using their email address and password.

Only authenticated users shall access protected features.

---

## FR-003 User Profile

The system shall allow authenticated users to:

- View their profile
- Update profile information
- Change password
- Upload a profile picture

---

# 3. Product Management

## FR-004 Publish Product

The system shall allow authenticated users to publish second-hand products.

Each product shall include:

- Title
- Description
- Category
- Product Condition
- Images
- Location
- Selling Mode

---

## FR-005 Selling Modes

The platform shall support two selling modes:

### Fixed Price

The seller specifies a selling price.

### Make an Offer

The seller publishes the product without displaying a selling price.

Interested users submit confidential purchase offers.

---

## FR-006 Product Search

The system shall allow visitors and users to:

- Browse products
- Search by keyword
- Filter by category
- Filter by selling mode
- Filter by location

---

## FR-007 Product Update

Only the product owner shall be allowed to edit product information.

---

## FR-008 Product Deletion

Only the product owner shall be allowed to delete an available product.

---

# 4. Offer Management

## FR-009 Submit Offer

Authenticated users shall be able to submit an offer for products using the "Make an Offer" mode.

Each offer shall include:

- Proposed price
- Optional message

---

## FR-010 Offer Visibility

Users shall only view their own submitted offers.

Only the seller shall view all received offers.

---

## FR-011 Offer Decision

The seller shall be able to:

- Accept an offer
- Reject an offer
- Keep an offer pending

---

## FR-012 Reservation

When an offer is accepted:

- The product status becomes Reserved.
- No additional offers shall be accepted.
- A private chat shall be created.

---

## FR-013 Offer Cancellation

Users shall be able to cancel their pending offers.

After cancellation, they may submit a new offer.

---

# 5. Fixed Price Products

## FR-014 Purchase Request

Users shall be able to request to purchase products listed with a fixed price.

The seller must approve the request before communication begins.

---

# 6. Chat

## FR-015 Private Chat

A private chat shall be created only after:

- An offer has been accepted, or
- A purchase request has been approved.

---

## FR-016 Chat Features

The private chat shall allow users to:

- Send text messages
- Send images
- Share a meeting location
- Mark the transaction as completed
- Cancel the transaction

---

# 7. Notifications

## FR-017 Notifications

The system shall notify users when:

- A new offer is received.
- An offer is accepted.
- An offer is rejected.
- A purchase request is accepted.
- A new message is received.

---

# 8. Favorites

## FR-018 Favorites

Authenticated users shall be able to:

- Add products to favorites.
- Remove products from favorites.
- View favorite products.

---

# 9. Administration

## FR-019 User Management

Administrators shall be able to:

- View users
- Suspend users
- Delete users

---

## FR-020 Product Moderation

Administrators shall be able to:

- Remove products
- Hide inappropriate products
- Review reported products

---

# 10. Security

## FR-021 Authorization

The platform shall support the following roles:

- Visitor
- User
- Administrator

---

## FR-022 Resource Ownership

Only the owner of a product shall be allowed to modify or delete it.

Only the owner of an offer shall be allowed to cancel it.

---

# 11. Dashboard

## FR-023 User Dashboard

Authenticated users shall access a dashboard containing:

- My Products
- My Offers
- My Purchase Requests
- Favorite Products
- Notifications
- Active Chats

---

# 12. Future Functionalities

The following functionalities are excluded from the MVP:

- AI price estimation
- Recommendation system
- Online payment
- Delivery management
- Reputation system
- Mobile application
- Premium subscription

---

# Version

**Document Version:** 1.0

**Status:** Approved

**Project:** TekhouFeha