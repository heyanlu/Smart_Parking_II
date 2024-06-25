# Parking Lot System 

- [Description](#description)
  - [About the Project](#about-the-project)
  - [Buid with](#buid-with)
  - [Features](#features)
- [Installation](#installation)
- [Architecture](#architecture)
- [Technical Stacks](#technical-stacks)


## Description

### About the Project
The Parking Lot System is designed to handle both customer and administrator interactions, enabling management and usage of the parking facility. 
Customers can park, pay, and leave the parking lot, while administrators can add members, and view all parked vehicles and members.

#### Frontend code link 
```bash
https://github.com/heyanlu/Smart_Parking_II_Frontend
```

### Build with
- Java
- Spring boot
- PostgreSQL
- React
- JavaScript
- Material UI

### Features

Customer Side
- Park Vehicle: Customers can park their vehicles based on the occupancy. 
- Pay for Parking: Calculate parking fee based on the parking duration.
- Leave Parking Lot: Simplified process for customers to exit the parking lot.

Administrator Side
- Add Members: Administrators can add new members to the system.
- View Parked Vehicles: Comprehensive view of all vehicles currently parked in the lot.
- View Members: Access to the list of all members.

#### Demo
```bash
https://northeastern.zoom.us/rec/share/cSDw0ex5NxXTJsnVPeDEEH-X38HoVOjs5o5eHv4dZBplYnAkA01BaWnDyx7AlMTQ.DV91vg9UDyia8nai?startTime=1719279196000
```
```bash
Passcode: P%nUW6z#
```

## Installation

### Prerequisites

Backend:
IntelliJ IDEA (or any preferred IDE)
Java Development Kit (JDK) installed
Frontend:

Frontend:
VS Code (or any preferred code editor)
Node.js and npm installed
Steps
Backend

### Clone the Backend
Clone the backend repository:
```bash
git clone https://github.com/heyanlu/Smart_Parking_II.git
```
Open the project in IntelliJ IDEA:

Launch IntelliJ IDEA.
Select File -> Open from the menu.
Navigate to the directory where you cloned the repository (Smart_Parking_II) and select it.
Click Open to open the project in IntelliJ.

### Run the Backend:
In IntelliJ, locate the main class of the application (named DemoApplication or similar).
Right-click on the main class file.
Select Run 'DemoApplication.main()' from the context menu.

Once the application has started successfully, you can access the backend API at http://localhost:8080/api/v1/smart_parking/ (assuming it's running on the default port).

### Clone the Frontend
Clone the frontend repository:
```bash
git clone https://github.com/heyanlu/Smart_Parking_II_Frontend.git
```
Open the project in VS Code:

Navigate into the cloned frontend directory:
```bash
cd smart_parking
```

Install dependencies and run the frontend application:
While in the Smart_Parking_II_Frontend directory, install dependencies:
```bash
npm install
```

### Run the Frontend:

Start the development server:
```bash
npm run dev
```

Once the frontend application has started successfully, you can access it at the local host .

## Architect

![Project Layout and Skills](https://github.com/heyanlu/Smart_Parking_II/assets/116776352/7d6febd7-9060-481e-98ec-bc447a4afb80)

Backend Architect
![Project Structure in Detail](https://github.com/heyanlu/Smart_Parking_II/assets/116776352/5779d878-5425-4809-a9c2-bd765ec7f779)

## Technical Stacks
### Frontend
Server-side rendering with React and Node.js

### Backend
Build with Java and Spring Boot

### Database
Used PostgreSQL
