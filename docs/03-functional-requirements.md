# Functional Requirements

## 1. Introduction

This document defines the functional requirements of the TekhouFeha platform. It describes the system functionalities that must be implemented to satisfy the business requirements and support the product vision.

---

# 2. User Management

## FR-001 User Registration

The system shall allow visitors to create a new account by providing:

- First name
- Last name
- Email
- Password
- Phone number

The system shall validate the uniqueness of the email address.

---

## FR-002 User Authentication

The system shall allow registered users to authenticate using their email and password.

The system shall deny access if the credentials are invalid.

---

## FR-003 User Profile

The system shall allow authenticated users to:

- View their profile
- Update personal information
- Change password
- Upload a profile picture

---

# 3. Product Management

## FR-004 Publish Product

The system shall allow sellers to publish a second-hand product.

A product shall contain:

- Title
- Description
- Category
- Condition
- Price
- Images
- Location

---

## FR-005 Update Product

The seller shall be able to edit product information before the product is sold.

---

## FR-006 Delete Product

The seller shall be able to delete an active product.

---

## FR-007 Browse Products

Visitors and buyers shall be able to browse available products.

The system shall support:

- Categories
- Search
- Filters
- Sorting

---

# 4. Negotiation Management

## FR-008 Submit Offer

Authenticated buyers shall be able to submit an offer for a product.

Each offer shall contain:

- Proposed price
- Optional message

---

## FR-009 Counter Offer

The seller shall be able to:

- Accept
- Reject
- Submit a counter-offer

---

## FR-010 Negotiation History

The system shall store every negotiation event.

Each negotiation shall include:

- Date
- User
- Offered price
- Status

---

## FR-011 Offer Expiration

Offers shall expire automatically after a configurable period.

---

# 5. Notification Management

## FR-012 Notifications

The system shall notify users when:

- A new offer is received.
- A counter-offer is received.
- An offer is accepted.
- An offer is rejected.

---

# 6. Administration

## FR-013 User Management

Administrators shall be able to:

- View users
- Suspend users
- Delete users

---

## FR-014 Product Moderation

Administrators shall be able to:

- Remove inappropriate products
- Hide products
- Review reported products

---

# 7. Security

## FR-015 Authorization

The system shall enforce role-based access control.

Supported roles:

- Visitor
- Buyer
- Seller
- Administrator

---

## FR-016 Authentication

Only authenticated users shall access protected resources.

---

# 8. Reporting

## FR-017 Dashboard

Authenticated users shall have access to a dashboard showing:

- Published products
- Active negotiations
- Sold products
- Purchased products

---

# 9. Future Functionalities

The following functionalities are excluded from the MVP:

- Online payment
- AI price recommendation
- Mobile application
- Delivery management
- Live chat