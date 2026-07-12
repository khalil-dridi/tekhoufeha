# Business Rules

---

# Introduction

This document defines the business rules governing the TekhouFeha platform. These rules describe how the platform must behave in different business scenarios and ensure consistency across the application.

---

# User Rules

## BR-001 User Registration

Every user must create an account before publishing products or submitting offers.

---

## BR-002 User Ownership

A registered user may act as both a buyer and a seller using the same account.

---

## BR-003 User Authentication

Only authenticated users may perform protected actions.

---

# Product Rules

## BR-004 Product Ownership

Only the owner of a product may edit or delete it.

---

## BR-005 Product Visibility

Only published products are visible to visitors.

---

## BR-006 Selling Modes

Every product must belong to exactly one selling mode:

- Fixed Price
- Make an Offer

---

## BR-007 Product Status

A product may have one of the following statuses:

- Published
- Reserved
- Sold

---

# Offer Rules

## BR-008 Offer Eligibility

Offers can only be submitted for products using the "Make an Offer" selling mode.

---

## BR-009 Offer Confidentiality

Only the seller can view all received offers.

Each buyer can view only their own offers.

---

## BR-010 Offer Ownership

Only the user who submitted an offer may cancel it.

---

## BR-011 Offer Cancellation

A cancelled offer cannot be restored.

The user may submit a new offer.

---

## BR-012 Seller Decision

The seller may:

- Accept an offer
- Reject an offer
- Leave an offer pending

---

## BR-013 Single Accepted Offer

A seller may accept only one offer for a product at a time.

---

## BR-014 Product Reservation

When an offer is accepted:

- The product status becomes Reserved.
- No new offers can be submitted.
- A private chat is created.

---

## BR-015 Pending Offers

When a product becomes Reserved:

Existing pending offers remain stored but become inactive until the reservation ends.

---

## BR-016 Reservation Cancellation

If the transaction fails:

- Product status returns to Published.
- Pending offers become active again.

---

## BR-017 Successful Transaction

When both users confirm the transaction:

- Product status becomes Sold.
- All remaining offers are automatically rejected.

---

# Fixed Price Rules

## BR-018 Purchase Request

Users may request to purchase Fixed Price products.

---

## BR-019 Seller Approval

The seller must approve the purchase request before communication begins.

---

# Chat Rules

## BR-020 Chat Availability

A private chat may only exist after:

- Offer acceptance
- Purchase request approval

---

## BR-021 Chat Participants

Only the buyer and seller involved in the transaction may access the chat.

---

# Favorite Rules

## BR-022 Favorites

Users may add or remove products from their favorites.

---

# Notification Rules

## BR-023 Notifications

The platform shall notify users when:

- A new offer is received.
- An offer is accepted.
- An offer is rejected.
- A purchase request is accepted.
- A new message is received.

---

# Administration Rules

## BR-024 User Moderation

Administrators may suspend or remove users violating platform policies.

---

## BR-025 Product Moderation

Administrators may remove products violating platform policies.

---

# MVP Constraints

## BR-026 Out of Scope

The MVP does not include:

- Online payment
- Delivery management
- AI price estimation
- Recommendation system
- Reputation system
- Mobile application

---

# Version

**Document Version:** 1.0

**Status:** Approved

**Project:** TekhouFeha