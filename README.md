# EPAMCourseFinalProjectLibrary
This is my final Java Web Project related to EPAM Web Development Courses in 2020. The project topic is LIBRARY.

This project imitates a library with a web access to some functionality. Depending on users' roles the functionality includes the features described below.
The roles are listed from those with the least functionality to the most advanced. Each next role level provides all the features the previous levels have access to, adding new ones.

### User - GUEST
This is a default role. Every Internet user has an access to its functionality. This one includes abilities to search books in this Library by titles or authors.

### User - READER
The initial reader registration is made by LIBRARIAN (see below). Signing in is made by providing the surname, name and the password sent by email during the registration process.
The READER can select books and order available ones for reading in a reading hall of the Library.

### User - SUBSCRIBER
The SUBSCRIBER have a choice between ordering books to a reading hall or to the delivery desk of the Library.

### User - LIBRARIAN
This user has an access to the three main functionality groups:
- Processing other users - the Librarian can add READERS and SIBSCRIBERS to the system and can change their status (roles)
- Processing READER/SUBSCRIBER orders - the Librarian can look through other users' book orders, can prepare orders to dispatching, dispatch them, return books from the users.
- Processing books - the Librarian can add new editions and concrete books, decommission them.

### User - ADMIN
This user can add LIBRARIANS in the system