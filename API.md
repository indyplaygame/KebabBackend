# Table of Contents
- **[Model](#model)**
  - **[Category](#category)**
  - **[MenuItem](#menuitem)**
  - **[Review](#review)**
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
- **[Menu](#menu)**
    - [<code style="color: rgb(250, 224, 124)">POST</code> Create](#create-1)
    - [<code style="color: rgb(95, 221, 154)">GET</code> Get](#get-1)
    - [<code style="color: rgb(95, 221, 154)">GET</code> Get Image](#get-image)
    - [<code style="color: rgb(95, 221, 154)">GET</code> List](#list-1)
    - [<code style="color: rgb(103, 174, 246)">PUT</code> Update](#update-1)
    - [<code style="color: rgb(234, 154, 142)">DELETE</code> Delete](#delete-1)
- **[Reviews](#reviews)**
    - [<code style="color: rgb(250, 224, 124)">POST</code> Create](#create-2)
    - [<code style="color: rgb(95, 221, 154)">GET</code> Get](#get-2)
    - [<code style="color: rgb(95, 221, 154)">GET</code> Get Image](#get-image-1)
    - [<code style="color: rgb(95, 221, 154)">GET</code> List](#list-2)
    - [<code style="color: rgb(103, 174, 246)">PUT</code> Update](#update-2)
    - [<code style="color: rgb(234, 154, 142)">DELETE</code> Delete](#delete-2)

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

## MenuItem
Defines the structure of a menu item object.
```json
{
  "menuItemId": "Long", 
  "name": "String",
  "description": "String (Optional)",
  "imageUrl": "String",
  "categoryId": "Long",
  "available": "Boolean",
  "rating": "Double",
  "price": "Double",
  "deliveryFee": "Double"
}
```

## Review
Defines the structure of a review object.
```json
{
  "reviewId": "Long",
  "title": "String (Optional",
  "description": "String (Optional)",
  "imageUrl": "String (Optional)",
  "createdAt": "String (ISO 8601 DateTime)",
  "updatedAt": "String (ISO 8601 DateTime)",
  "userId": "Long",
  "anonymous": "Boolean",
  "rating": "Double",
  "likes": "Integer"
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
  "error": "Could not find Category with ID {id}"
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
  "error": "Could not find Category with ID {id}"
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
**URL:** `/categories/{id}/update`<br>
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
      "Name can only contain alphanumeric characters and spaces",
      "Name must be between 3 and 50 characters"
    ],
    "icon": [
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
  "error": "NCould not find Category with ID {id}"
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
  "error": "Could not find Category with ID {id}"
}
```
<br>
```

# Menu
Endpoints for managing menu items.

## Create
**URL:** `/menu/create`<br>
**Method:** <code style="color: rgb(250, 224, 124)">POST</code><br>
**Authentication:** Required<br>
**Content-Type:** `multipart/form-data`<br>
**Description:** Create a new menu item.<br>

### **Request Body:**
- `name`: String
- `description`: String (optional)
- `image`: File (png, jpeg, jpg, gif, svg, webp)
- `price`: Double
- `deliveryFee`: Double (optional, default: 0.0)
- `available`: Boolean (optional, default: true)
- `categoryId`: Long (optional)

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">201 Created</code><br>
**Description**: Menu item created successfully.<br>
**Body**: `MenuItem`
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Invalid request body format or missing required fields.<br>

```json
{
  "errors": {
    "name": [
      "Name cannot be empty",
      "Name can only contain alphanumeric characters, apostrophes and spaces",
      "Name must be between 3 and 50 characters"
    ],
    "description": [
      "Description cannot exceed 1000 characters"
    ],
    "image": [
      "Image file cannot be empty",
      "Image must be a PNG, JPEG, GIF, SVG or WEBP image",
      "Image file size cannot exceed 5MB"
    ],
    "price": [
      "Price cannot be empty",
      "Price must be a positive number"
    ],
    "deliveryFee": [
      "Delivery fee cannot be empty",
      "Delivery fee must be a non-negative number"
    ],
    "categoryId": [
      "Category ID must be a positive number"
    ]
  }
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No category found with the provided `categoryId`.<br>

```json
{
  "error": "Could not find Category with ID {id}"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">500 Internal Server Error</code><br>
**Description**: Failed to upload image.<br>

```json
{
  "error": "Failed to upload image: {message}"
}
```

## Get
**URL:** `/menu/{id}`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a menu item by its `id`.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Menu item retrieved successfully.<br>
**Body**: `MenuItem`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No menu item found with the provided `id`.<br>

```json
{
  "error": "Could not find MenuItem with ID {id}"
}
```
<br>

## Get Image
**URL:** `/menu/{id}/image`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a menu item's image by its `id`.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Menu item image retrieved successfully.<br>
**Body**: `Image`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No menu item found with the provided `id`.<br>

```json
{
  "error": "Could not find MenuItem with ID {id}"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: Couldn't find image for menu item with the provided `id`.<br>

```json
{
  "error": "Couldn't find image for menu item with the provided ID"
}
```
<br>

## List
**URL:** `/menu/list`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a list of all menu items.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Menu items retrieved successfully.<br>
**Body**: `List[MenuItem]`<br>
<br>

## Update
**URL:** `/menu/{id}/update`<br>
**Method:** <code style="color: rgb(103, 174, 246)">PUT</code><br>
**Authentication:** Required<br>
**Content-Type:** `multipart/form-data`<br>
**Description:** Update an existing menu item.<br>

### **Request Body:**
- `name`: String (optional)
- `description`: String (optional)
- `image`: File (png, jpeg, jpg, gif, svg, webp) (optional)
- `price`: Double (optional)
- `deliveryFee`: Double (optional, default: 0.0) (optional)
- `available`: Boolean (optional, default: true) (optional)
- `categoryId`: Long (optional)

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Menu item updated successfully.<br>
**Body**: `MenuItem`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Invalid request body format or missing required fields.<br>

```json
{
  "errors": {
    "name": [
      "Name can only contain alphanumeric characters, apostrophes and spaces",
      "Name must be between 3 and 50 characters"
    ],
    "description": [
      "Description cannot exceed 1000 characters"
    ],
    "image": [
      "Image must be a PNG, JPEG, GIF, SVG or WEBP image",
      "Image file size cannot exceed 5MB"
    ],
    "price": [
      "Price must be a positive number"
    ],
    "deliveryFee": [
      "Delivery fee must be a non-negative number"
    ],
    "categoryId": [
      "Category ID must be a positive number"
    ]
  }
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No menu item found with the provided `id`.<br>

```json
{
  "error": "Could not find MenuItem with ID {id}"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">500 Internal Server Error</code><br>
**Description**: Failed to upload image.<br>

```json
{
  "error": "Failed to upload image: {message}"
}
```

## Delete
**URL:** `/menu/{id}/delete`<br>
**Method:** <code style="color: rgb(234, 154, 142)">DELETE</code><br>
**Authentication:** Required<br>
**Content-Type:** None<br>
**Description:** Delete an existing menu item.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">204 No Content</code><br>
**Description**: Menu item deleted successfully.<br>
**Body**: None<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No menu item found with the provided `id`.<br>

```json
{
  "error": "Could not find MenuItem with ID {id}"
}
```
<br>

# Reviews
Endpoints for managing reviews.

## Create
**URL:** `/reviews/create`<br>
**Method:** <code style="color: rgb(250, 224, 124)">POST</code><br>
**Authentication:** Required<br>
**Content-Type:** `multipart/form-data`<br>
**Description:** Create a new review.<br>

### **Request Body:**
- `title`: String (optional)
- `description`: String (optional)
- `image`: File (png, jpeg, jpg, gif, svg, webp) (optional)
- `rating`: Double (multiple of 0.5 between 0.0 and 5.0)
- `available`: Boolean (optional, default: false)

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">201 Created</code><br>
**Description**: Review created successfully.<br>
**Body**: `Review`
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Invalid request body format or missing required fields.<br>

```json
{
  "errors": {
    "title": [
      "Title can only contain alphanumeric characters, apostrophes and spaces",
      "Title must be between 3 and 100 characters"
    ],
    "description": [
      "Description cannot exceed 1000 characters"
    ],
    "image": [
      "Image must be a PNG, JPEG, GIF, SVG or WEBP image",
      "Image file size cannot exceed 5MB"
    ],
    "rating": [
      "Rating cannot be empty",
      "Rating must be in increments of 0.5",
      "Rating must be between 0.0 and 5.0"
    ]
  }
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">500 Internal Server Error</code><br>
**Description**: Failed to upload image.<br>

```json
{
  "error": "Failed to upload image: {message}"
}
```

## Get
**URL:** `/reviews/{id}`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a review by its `id`.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Review retrieved successfully.<br>
**Body**: `Review`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No review found with the provided `id`.<br>

```json
{
  "error": "Could not find Review with ID {id}"
}
```
<br>

## Get Image
**URL:** `/reviews/{id}/image`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a review's image by its `id`.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Review image retrieved successfully.<br>
**Body**: `Image`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No review found with the provided `id`.<br>

```json
{
  "error": "Could not find Review with ID {id}"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: Couldn't find image for menu item with the provided `id`.<br>

```json
{
  "error": "Couldn't find image for review with the provided ID"
}
```
<br>

## List
**URL:** `/reviews/list`<br>
**Method:** <code style="color: rgb(95, 221, 154)">GET</code><br>
**Authentication:** Not Required<br>
**Content-Type:** None<br>
**Description:** Retrieve a list of all menu items.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Menu items retrieved successfully.<br>
**Body**: `List[Review]`<br>
<br>

## Update
**URL:** `/reviews/{id}/update`<br>
**Method:** <code style="color: rgb(103, 174, 246)">PUT</code><br>
**Authentication:** Required<br>
**Content-Type:** `multipart/form-data`<br>
**Description:** Update an existing review.<br>

### **Request Body:**
- `title`: String (optional)
- `description`: String (optional)
- `image`: File (png, jpeg, jpg, gif, svg, webp) (optional)
- `rating`: Double (optional)
- `anonymous`: Boolean (optional, default: false) (optional)

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">200 OK</code><br>
**Description**: Review updated successfully.<br>
**Body**: `Review`<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">400 Bad Request</code><br>
**Description**: Invalid request body format or missing required fields.<br>

```json
{
  "errors": {
    "title": [
      "Title can only contain alphanumeric characters, apostrophes and spaces",
      "Title must be between 3 and 100 characters"
    ],
    "description": [
      "Description cannot exceed 1000 characters"
    ],
    "image": [
      "Image must be a PNG, JPEG, GIF, SVG or WEBP image",
      "Image file size cannot exceed 5MB"
    ],
    "rating": [
      "Rating must be in increments of 0.5",
      "Rating must be between 0.0 and 5.0"
    ]
  }
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No review found with the provided `id`.<br>

```json
{
  "error": "Could not find Review with ID {id}"
}
```
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">500 Internal Server Error</code><br>
**Description**: Failed to upload image.<br>

```json
{
  "error": "Failed to upload image: {message}"
}
```

## Delete
**URL:** `/reviews/{id}/delete`<br>
**Method:** <code style="color: rgb(234, 154, 142)">DELETE</code><br>
**Authentication:** Required<br>
**Content-Type:** None<br>
**Description:** Delete an existing review.<br>

### **Request Body:**
None

### **Response:**<br>
**Status**: <code style="color: rgb(107, 208, 98); background-color: rgb(1, 54, 20)">204 No Content</code><br>
**Description**: Review deleted successfully.<br>
**Body**: None<br>
<br>

**Status**: <code style="color: rgb(222, 154, 142); background-color: rgb(89, 27, 8)">404 Not Found</code><br>
**Description**: No review found with the provided `id`.<br>

```json
{
  "error": "Could not find Review with ID {id}"
}
```
<br>