# Serendib Digital Banking CLI

This project is a Java-based CLI application that implements the core business logic for onboarding and authentication
in a digital banking platform. The implementation follows software design principles (SOLID, DRY, KISS) and incorporates
design patterns from Creational, Structural, and Behavioral categories to ensure maintainability, scalability, and
robustness.

The application supports two key functionalities:

- New CASA Customer Onboarding - A new customer can register using their CASA account and set up their login
  credentials.
- User Login with Two-Factor Authentication (2FA) - An onboarded customer can log in using a username, password, and
  OTP.

## Features

- Object-Oriented Design adhering to SOLID principles
- Design Patterns: Singleton, Builder, Decorator, Strategy
- Error handling and validation for user inputs
- Scalable and maintainable codebase

## Flow

1. Customer Onboarding
    - Enter NIC/Passport number and CASA account number.
    - Validate OTP received via SMS/email.
    - Choose verification method: Call Center or Branch.
    - Create a username and password.
    - Successful onboarding leads to login access.
2. Login with 2FA
    - Enter username and password.
    - Receive and enter OTP for authentication.
    - Successful login grants access to banking services.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
