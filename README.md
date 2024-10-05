# 🎵 Hybridvision - Music Studio Website

A **Music Studio** website built with **Angular**, **Spring Boot**, **Stripe**, **AWS S3**, and **Docker**. 
This platform allows users to explore the studio's services, purchase music productions, and sample packs, while providing an admin panel to manage users and content.

![Hybridvision Gif](https://github.com/user-attachments/assets/2a16bb4b-37da-4281-9c19-df265d1a2ff8)

## Features

### 🎸 User Functionality:
- **Browse Productions & Services**: Explore the studio’s music productions and services.
- **Purchase Options**: Users can listen to and purchase productions or sample packs.
- **Wishlist & Shopping Cart**: Add products or sample packs to the wishlist or shopping cart.
- **User Account Management**: 
  - Register, login, reset password via email.
  - Edit profile information or disable account.
  - View and download purchased products on a dedicated download page.
- **Form Validation**: Every form is validated to ensure data integrity.
- **Contact Form**: Get in touch with the studio via a contact form.

### 🛠️ Admin Features:
- **Content Management**: Admins can upload new music productions and sample packs.
- **User Management**: Block or deactivate user accounts as necessary.
  
### 🧑‍💻 General:
- **Error Handling**: Custom 404 error page for better user experience.
- **Secure Storage**: Files are securely stored in **AWS S3**.

## Tech Stack

### Frontend:
- **Angular**: For building a dynamic and responsive user interface.
  
### Backend:
- **Spring Boot**: REST API for business logic and user management.

### Payment:
- **Stripe**: For handling payments for productions and sample packs.

### File Storage:
- **AWS S3**: Cloud storage for uploaded files and assets.

### Deployment:
- **Docker**: For containerizing and simplifying deployment across different environments.

## Setup Note

⚠️ **Important**: All sensitive information, including passwords, API keys, and configurations, has been removed from this repository. The project will not run without setting up these credentials.

You will need to configure the following services:
- **Stripe**: Obtain your API keys from the [Stripe Dashboard](https://stripe.com) and configure the backend.
- **AWS S3**: Set up an S3 bucket on AWS and add your AWS credentials to the backend configuration.
