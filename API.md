# Table of Contents'
- **[Model](#model)**
  - **[Category](#category)**
- **[Authentication](#authentication)**
    - [<code style="color: rgb(250, 224, 124)">POST</code> Register](#register)
    - [<code style="color: rgb(250, 224, 124)">POST</code> Login](#login)
    - [<code style="color: rgb(250, 224, 124)">POST</code> Logout](#logout)
- **[Categories](#categories)**
    - [<code style="color: rgb(250, 224, 124)">POST</code> Create](#create)
    - [<code style="color: rgb(95, 221, 154)">GET</code> Get](#get)
    - [<code style="color: rgb(95, 221, 154)">GET</code> Get Icon](#get-icon)
    - [<code style="color: rgb(95, 221, 154)">GET</code> List](#list)
    - [<code style="color: rgb(103, 174, 246)">PUT</code> Update](#update)
    - [<code style="color: rgb(234, 154, 142)">DELETE</code> Delete](#delete)

# Model
Application data models.

## Category
Defines the structure of a category object.
```json
{
  "categoryId": "Long",
  "name": "String",
  "imageUrl": "String",
  "description": "String (Optional)"
}
```

# Authentication
Endpoints for user authentication and session management.

## Register
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

## Login
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

## Logout
**URL:** `/auth/logout`<br>
**Method:** <code style="color: rgb(250, 224, 124)">POST</code><br>
**Authentication:** Not required<br>
**Content-Type:** None<br>
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

# Categories
Endpoints for managing categories.

## Create
**URL:** `/categories/create`<br>
**Method:** <code style="color: rgb(250, 224, 124)">POST</code><br>
**Authentication:** Required<br>
**Content-Type:** `multipart/form-data`<br>
**Description:** Create a new category.<br>

### **Request Body:**
- `name`: String
- `icon`: File (png, jpeg, jpg, gif, svg, webp)
- `description`: String (optional)

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">201 Created</code><br>
**Description**: Category created successfully.<br>
**Body**: `Category`
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Invalid request body format or missing required fields.<br>

```json
{
  "errors": {
    "name": [
      "Name cannot be empty",
      "Name can only contain alphanumeric characters and spaces",
      "Name must be between 3 and 50 characters"
    ],
    "icon": [
      "Icon file cannot be empty",
      "Icon must be a PNG, JPEG, GIF, SVG or WEBP image",
      "Icon file size cannot exceed 5MB"
    ],
    "description": [
      "Description cannot exceed 1000 characters"
    ]
  }
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">500 Internal Server Error</code><br>
**Description**: Failed to upload icon.<br>

```json
{
  "error": "Failed to upload icon: {message}"
}
```

## Get
**URL:** `/categories/{id}`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a category by its `id`.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Category retrieved successfully.<br>
**Body**: `Category`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No category found with the provided `id`.<br>

```json
{
  "error": "No category found with the provided ID"
}
```
<br>

## Get Icon
**URL:** `/categories/{id}/icon`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a category's icon by its `id`.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Category icon retrieved successfully.<br>
**Body**: `Image`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No category found with the provided `id`.<br>

```json
{
  "error": "No category found with the provided ID"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: Couldn't find icon for category with the provided `id`.<br>

```json
{
  "error": "Couldn't find icon for category with the provided ID"
}
```
<br>

## List
**URL:** `/categories/list`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a list of all categories.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Categories retrieved successfully.<br>
**Body**: `List[Category]`<br>
<br>

## Update
**URL:** `/categories/list`<br>
**Method:** <code style="color: rgb(103, 174, 246)">PUT</code><br>
**Authentication:** Required<br>
**Content-Type:** `multipart/form-data`<br>
**Description:** Update an existing category.<br>

### **Request Body:**
- `name`: String (optional)
- `icon`: File (png, jpeg, jpg, gif, svg, webp) (optional)
- `description`: String (optional)

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Category updated successfully.<br>
**Body**: `Category`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Invalid request body format or missing required fields.<br>

```json
{
  "errors": {
    "name": [
      "Name cannot be empty",
      "Name can only contain alphanumeric characters and spaces",
      "Name must be between 3 and 50 characters"
    ],
    "icon": [
      "Icon file cannot be empty",
      "Icon must be a PNG, JPEG, GIF, SVG or WEBP image",
      "Icon file size cannot exceed 5MB"
    ],
    "description": [
      "Description cannot exceed 1000 characters"
    ]
  }
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No category found with the provided `id`.<br>

```json
{
  "error": "No category found with the provided ID"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">500 Internal Server Error</code><br>
**Description**: Failed to upload icon.<br>

```json
{
  "error": "Failed to upload icon: {message}"
}
```

## Delete
**URL:** `/categories/{id}/delete`<br>
**Method:** <code style="color: rgb(234, 154, 142)">DELETE</code><br>
**Authentication:** Required<br>
**Content-Type:** None<br>
**Description:** Delete an existing category.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">204 No Content</code><br>
**Description**: Category deleted successfully.<br>
**Body**: None<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No category found with the provided `id`.<br>

```json
{
  "error": "No category found with the provided ID"
}
```
<br>
```