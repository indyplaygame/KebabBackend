# Table of Contents
- **[Authentication](#authentication)**
    - [<code style="color: rgb(250, 224, 124)">POST</code> Register](#code-stylecolor-rgb250-224-124postcode-register)
    - [<code style="color: rgb(250, 224, 124)">POST</code> Login](#code-stylecolor-rgb250-224-124postcode-login)
    - [<code style="color: rgb(250, 224, 124)">POST</code> Logout](#logout)

# Authentication
## <code style="color: rgb(250, 224, 124)">POST</code> Register
**URL:** `/auth/register`<br>
**Method:** <code style="color: rgb(250, 224, 124)">POST</code><br>
**Authentication:** Not required<br>
**Content-Type:** `application/json`<br>
**Description:** Register a new user.<br>

### **Request Body:**
```json
{
  "username": "String",
  "email": "String",
  "firstName": "String",
  "middleName": "String (Optional)",
  "lastName": "String",
  "dateOfBirth": "DD/MM/YYYY",
  "password": "String"
}
```

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">201 Created</code><br>
**Description**: User successfully registered.<br>

```json
{
  "message": "User created successfully"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Invalid request body format or missing required fields.<br>

```json
{
  "errors": {
    "username": [
      "Username cannot be empty",
      "Username can only contain alphanumeric characters and underscores",
      "Username must be between 3 and 20 characters"
    ],
    "email": [
      "Email cannot be empty",
      "Invalid email format",
      "Email must be between 5 and 100 characters"
    ],
    "firstName": [
      "First name cannot be empty",
      "First name can only contain alphabetic characters",
      "First name must be between 1 and 50 characters"
    ],
    "middleName": [
      "Middle name can only contain alphabetic characters",
      "Middle name must be up to 50 characters"
    ],
    "lastName": [
      "Last name cannot be empty",
      "Last name can only contain alphabetic characters",
      "Last name must be between 1 and 50 characters"
    ],
    "dateOfBirth": [
      "Date of birth cannot be empty",
      "Date of birth must be in the format DD/MM/YYYY"
    ],
    "password": [
      "Password cannot be empty",
      "Password can only contain alphanumeric characters and special characters (!@#$%^&*-_)",
      "Password must be between 6 and 20 characters"
    ]
  }
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Illegal argument provided.<br>

```json
{
  "error": "Illegal argument: {message}"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">409 Conflict</code><br>
**Description**: Username or email already in use.<br>

```json
{
  "error": "Username or email already in use"
}
```
<br>

## <code style="color: rgb(250, 224, 124)">POST</code> Login
**URL:** `/auth/login`<br>
**Method:** <code style="color: rgb(250, 224, 124)">POST</code><br>
**Authentication:** Not required<br>
**Content-Type:** `application/json`<br>
**Description:** Authenticate a user.<br>

### **Request Body:**
```json
{
  "login": "String",
  "password": "String"
}
```

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Logged in successfully.<br>

```json
{
  "message": "Logged in successfully"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Invalid request body format or missing required fields.<br>

```json
{
  "errors": {
    "login": [
      "Login (either username or email) cannot be empty"
    ],
    "password": [
      "Password cannot be empty"
    ]
  }
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">401 Unauthorized</code><br>
**Description**: Invalid login credentials. Login or password is either missing or incorrect.<br>

```json
{
  "error": "Invalid login credentials"
}
```
<br>

## <code style="color: rgb(250, 224, 124)">POST</code> Logout
**URL:** `/auth/logout`<br>
**Method:** <code style="color: rgb(250, 224, 124)">POST</code><br>
**Authentication:** Not required<br>
**Content-Type:** `application/json`<br>
**Description:** Log out of the current session.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Logged in successfully.<br>

```json
{
  "message": "Logged out successfully"
}
```