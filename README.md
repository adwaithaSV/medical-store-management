# Medical Store Management System

A comprehensive web application for managing medicines and their stock in a medical store. This project is built with Spring Boot, Spring Security, Spring Data JPA, and Thymeleaf for a server-side rendered frontend.

## 🚀 Features

* **User Authentication & Authorization**: Secure signup, login, and logout functionalities for store managers.
* **Medicine Management (CRUD)**:
    * **Add Medicine**: Logged-in users can add new medicines with details like name, description, and available stock.
    * **5 Medicine Limit**: A user can add a maximum of 5 medicines, after which an error is displayed.
    * **View Medicines**: Display a list of medicines added by the logged-in user, including added time and current stock.
    * **Pagination**: The medicine listing page includes pagination for easy navigation through large inventories.
    * **Search**: Users can search for specific medicines by name.
    * **Edit Medicine**: Update details of existing medicines.
    * **Delete Medicine**: Remove medicines from the inventory.
* **User-Specific Data**: Each logged-in user can only view, add, edit, or delete medicines they have personally added.
* **Secure Password Storage**: User passwords are securely stored using BCrypt encryption.

## 🛠️ Technologies Used

* **Backend**:
    * Spring Boot 3.x
    * Spring Security (for authentication and authorization)
    * Spring Data JPA (for database interaction)
    * H2 Database (in-memory, for development)
    * Maven (for dependency management and build automation)
    * Java 17
* **Frontend**:
    * Thymeleaf (for server-side rendering of HTML templates)
    * HTML5
    * CSS (Bootstrap 4.x for basic styling)

## 📂 Project Structure

Okay, here's a README.md file tailored for your Medical Store Management Spring Boot project, suitable for a GitHub repository.

Markdown

# Medical Store Management System

A comprehensive web application for managing medicines and their stock in a medical store. This project is built with Spring Boot, Spring Security, Spring Data JPA, and Thymeleaf for a server-side rendered frontend.

## 🚀 Features

* **User Authentication & Authorization**: Secure signup, login, and logout functionalities for store managers.
* **Medicine Management (CRUD)**:
    * **Add Medicine**: Logged-in users can add new medicines with details like name, description, and available stock.
    * **5 Medicine Limit**: A user can add a maximum of 5 medicines, after which an error is displayed.
    * **View Medicines**: Display a list of medicines added by the logged-in user, including added time and current stock.
    * **Pagination**: The medicine listing page includes pagination for easy navigation through large inventories.
    * **Search**: Users can search for specific medicines by name.
    * **Edit Medicine**: Update details of existing medicines.
    * **Delete Medicine**: Remove medicines from the inventory.
* **User-Specific Data**: Each logged-in user can only view, add, edit, or delete medicines they have personally added.
* **Secure Password Storage**: User passwords are securely stored using BCrypt encryption.

## 🛠️ Technologies Used

* **Backend**:
    * Spring Boot 3.x
    * Spring Security (for authentication and authorization)
    * Spring Data JPA (for database interaction)
    * H2 Database (in-memory, for development)
    * Maven (for dependency management and build automation)
    * Java 17
* **Frontend**:
    * Thymeleaf (for server-side rendering of HTML templates)
    * HTML5
    * CSS (Bootstrap 4.x for basic styling)
* **Development Tools**:
    * Git / GitHub

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

* Java Development Kit (JDK) 17 or newer
* Apache Maven 3.6.0 or newer

### Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/](https://github.com/)[YourUsername]/medical-store-management.git
    cd medical-store-management
    ```

2.  **Build the project with Maven:**
    ```bash
    mvn clean install
    ```

### Running the Application

1.  **Run from Maven:**
    ```bash
    mvn spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

2.  **Access H2 Console (Development Database):**
    While the application is running, you can access the H2 database console at `http://localhost:8080/h2-console`.
    * **JDBC URL:** `jdbc:h2:mem:medicaldb`
    * **Username:** `sa`
    * **Password:** (leave empty)

## 🌐 Usage (Web Interface)

After starting the application, open your web browser and navigate to:

1.  **Homepage / Medicines List:** `http://localhost:8080/` (This will redirect to `/medicines`, then to `/login` if not authenticated).
2.  **Login Page:** `http://localhost:8080/login`
    * Use this to log in with existing user credentials.
    * Error messages for invalid credentials or successful logout messages are displayed here.
3.  **Sign Up Page:** `http://localhost:8080/signup`
    * Register a new store manager account.
4.  **Medicines List:** `http://localhost:8080/medicines`
    * View all medicines added by the logged-in user.
    * Use the search bar to filter medicines by name.
    * Navigate through pages using the pagination controls.
    * "Add New Medicine" button to add a new medicine.
5.  **Add/Edit Medicine Form:**
    * Accessible via "Add New Medicine" button or by clicking "Edit" next to a medicine in the list.
    * Fill in medicine details and save.
    * An error will be displayed if a user tries to add more than 5 medicines.


