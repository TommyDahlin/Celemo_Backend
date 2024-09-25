## Celemo Backend Installation Guide

Follow these steps to set up and run the Celemo backend locally:

### 1. Clone the Project Repository

- Open a terminal and navigate to the directory where you want to clone the project.
- Run the following command to clone the repository:

![bild](https://github.com/user-attachments/assets/3db769bb-0fe7-4cdf-9e27-fedeaf6310e8)
- Open IntelliJ IDEA (or your preferred IDE) and navigate to the folder containing the cloned repository.

### 2. Create the ![bild](https://github.com/user-attachments/assets/c48b0216-8ead-4996-80e0-307789de3450) File

- Navigate to the following directory in your project:

  ![bild](https://github.com/user-attachments/assets/eae7ed04-f50c-43f8-9921-9e5fb6b72ccd)

- Create a new file called ![bild](https://github.com/user-attachments/assets/010c77cf-8a0e-4ac6-9fc8-5c0a071075ee)
 in the resources folder which lays inside the ![bild](https://github.com/user-attachments/assets/c52aef1e-7db9-4890-bbc2-0fb1d8f481f8)
folder.

### 3. Configure Environment Properties

- Open the ![bild](https://github.com/user-attachments/assets/010c77cf-8a0e-4ac6-9fc8-5c0a071075ee) file and add the following lines:

  ![bild](https://github.com/user-attachments/assets/89b30501-b728-4345-b477-e1b3d64fc308)
  
- Replace {your value} with the appropriate sensitive values, such as your MongoDB URI, JWT secret, and cookie name.

### 4. Build and Run the Application

- In your IDE terminal, run the following command to build the application:
![bild](https://github.com/user-attachments/assets/3b9c39db-abe6-4e51-8900-5222c22d72b1)

- or if your're using Maven globally:

  ![bild](https://github.com/user-attachments/assets/03780ab7-e81b-4c3a-968b-04bf03dacd2c)

- After the build completes successfully, run the application with:

  ![bild](https://github.com/user-attachments/assets/780def09-8e4c-451c-9764-0b72a29d352c)

  - or
 
  ![bild](https://github.com/user-attachments/assets/888944ae-fbe1-403f-857d-19a916c2ae34)


### 5. Access the API

- Once the application is running, you can access the backend API at:

  ![bild](https://github.com/user-attachments/assets/4ee83d59-1f2a-4d5d-a697-f7689f40e8d0)

### 6. Verify Installation

- Check your terminal or IDE console for log messages indicating the application has started successfully.
- Ensure you have provided the correct environment values (MongoDB URI, JWT settings, etc.).
