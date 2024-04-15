src
├───main
│   ├───java
│   │   └───learn
│   │       └───DWMH
│   │           │   App.java
│   │           │
│   │           ├───data
│   │           │       DataException.java
│   │           │       ReservationFileRepository.java
│   │           │       ReservationRepository.java
│   │           │       GuestFileRepository.java
│   │           │       GuestRepository.java
│   │           │       HostFileRepository.java
│   │           │       HostRepository.java
│   │           │
│   │           ├───domain
│   │           │       ReservationService.java
│   │           │       GuestService.java
│   │           │       HostService.java
│   │           │       Response.java
│   │           │       Result.java
│   │           │
│   │           ├───models
│   │           │       Reservation.java
│   │           │       Host.java
│   │           │       Guest.java
│   │           │
│   │           └───ui
│   │                   ConsoleIO.java
│   │                   Controller.java
│   │                   MainMenuOption.java
│   │                   View.java
│   │
│   └───resources
│           dependency-configuration.xml (if necessary)
└───test
    └───java
        └───learn
            └───DWMH
                ├───data
                │       ReservationFileRepositoryTest.java
                │       ReservationRepositoryDouble.java
                │       HostFileRepositoryTest.java
                │       HostRepositoryDouble.java
                │       GuestFileRepositoryTest.java
                │       GuestRepositoryDouble.java
                │
                └───domain
                        ReservationServiceTest.java
                        GuestServiceTest.java
                        HostServiceTest.java