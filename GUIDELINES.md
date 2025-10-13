# 1. Code Conventions
## Naming conventions:
 - **Local Variables and Method parameters**: Use `snake_case` for names of local variables and method parameters.
 - **Classes, Interfaces, Enums and Records**: Use `PascalCase` for names of classes, interfaces, enums and records.
 - **Private fields**: Prefix names of private and protected fields with underscore (`_`) and use `camelCase` (`_camelCase`).
 - **Methods, static functions and public fields**: Use `camelCase` for names of methods and static functions.
 - **Constants and Enum values**: Use `UPPER_SNAKE_CASE` for names of constants and enum values.
 - **Short and Descriptive Names**: Use meaningful names only in **English**.
 - **Nouns or Noun Phrases**: Use nouns or noun phrases for naming classes, interfaces, enums, and structs.
 - **Avoid Numbers in Names**: Avoid using numbers in names, unless they add meaningful context.
 - **Avoid Unnecessary Prefixes**: Avoid prefixes like `do`, `perform` in names unless they clearly improve clarity.

## Prefix Rules:
 - **Interfaces**: Prefix interfaces names with an uppercase `I`.
 - **Private fields**: Prefix names of private fields with an underscore (`_`).
 - **Boolean field getters**: Prefix getters for boolean fields with `is`, `has`, `can`, or similar verbs, unless unnecessary.

## Suffix Rules:
 - **Exceptions**: Suffix exception classes with `Exception`.

## Formatting and Style:
 - **Indentation**: Use **4 spaces** per level of indentation.
 - **Line Length**: Maximum of **120 characters** per line.
 - **Braces**: Use **K&R Style**:
   - Opening braces on the same line as the declaration.
   - Closing braces aligned with the beginning of the statement.
 - **Blank Lines**: Use blank lines to:
   - Separate methods, blocks of code, or logical sections within a method.
   - Improve readability and group related code together.

## Commenting:
 - **Inline Comments**:
   - Use sparingly for clarification in complex code sections.
   - Place on the same line or above the code it describes.
 - **Javadoc Comments**:
   - Use for Packages, Classes, Interfaces, Enums, Records and Methods.
   - Describe the purpose, parameters, return values and possible exceptions.
   - Maintain a `package-info.java` file for package-level documentation.
   - Format:
   ```java
   /*
     * Brief description of the method's purpose.
     *
     * @param paramName1 description of a parameter
     * @param paramName2 description of a parameter
     * ...
     * @return description of the return value
   
     * @throws ExceptionType description of the exception
     * @throws AnotherExceptionType description of another exception
     * ...
   */
   ```
 - **Avoid Redundant Comments**: Do not restate the obvious.
 - **Language**: Write all comments in **English**.

## General Best Practices:
 - **Avoid Magic Numbers**: Replace unexplained constants with named constants or configuration values.
 - **Field Declaration Order**:
   1. **Constants**
   2. **Static fields**
   3. **Instance fields**
   4. **Constructors**
   5. **Methods**
 - **Variables**: Explicitly state the type of variable, even when the type is obvious (Avoid using `var` keyword).
 - **Instance Fields**: Always use `this` keyword for accessing instance fields or methods.
 - **Switch Statements**: Use `switch` statements for enums or other variables with a large number of constant options.
 - **Encapsulation**: Always keep fields private and provide controlled access through methods.
 - **Single Responsibility Principle**: Each class or method should do one thing only.
 - **Follow DRY Principle**: Avoid repeating code by extracting reusable methods.
 - **Use `StringBuilder` for Concatenation**: Prefer `StringBuilder` for constructing strings in loops.
 - **Close Resources**: Always close resources like files, streams, or database connections using `try-with-resources` or `finally` blocks.
 - **Error Handling**: Use exceptions for error handling, and avoid returning error codes.

## Code Readability:
 - **Method Length**: Keep methods short and focused on a single responsibility. If a method exceeds **30-40 lines**, consider refactoring.
 - **Chained Calls**: Break chained calls across multiple lines for readability.

# 2. Project Structure
## Directory Structure:
```
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── api.indy.kebab
│   │   │       ├── auth                            # Authentication and Authorization
│   │   │       ├── config                          # Application configuration
│   │   │       ├── controller                      # REST controllers
│   │   │       ├── exceptions                      # Custom exceptions
│   │   │       ├── main                            # Main application class
│   │   │       ├── model                           # Data models and entities
│   │   │       ├── repository                      # Data access layer
│   │   │       ├── service                         # Application logic layer
│   │   │       ├── util                            # Utility classes
│   │   │       └── validation                      # Validation annotations and logic
│   │   └── resources/
│   │       ├── static/                             # Static resources (eg. CSS, images)
│   │       ├── templates/                          # Template files
│   │       ├── application.properties              # Application configuration file
│   │       ├── application-prod.properties         # Production configuration file
│   │       └── application-dev.properties          # Development configuration file
│   └── test/                                       # Test source code
│       └── java/
│           └── api.indy.kebab
├── docs/                                           # Documentation
├── build.gradle                                    # Gradle build file
├── settings.gradle                                 # Gradle settings file
├── GUIDELINES.md                                   # Coding guidelines
├── API.md                                          # API documentation
└── README.md                                       # Project overview
```

## File Naming:
 - Source code and tests: PascalCase
 - Configuration files: kebab-case
 - Documentation files: UPPER_SNAKE_CASE
 - Match the file names with the class, enum, record or interface they define.
 - Keep each class, enum, record or interface in its own file for clarity and modularity.

# 3. Development Process
## Branching Strategy
 - **Main (or Master) Branch**: Always reflects the latest stable release.
 - **Development Branch**: For integrating new features and preparing the next release.
   - **Feature Branches**: Use for individual features or changes.
   - **Issue-Fix Branches**: Use for individual bug or issue fixes.
 - **Branching Structure**:
 ```
 main 
 └─ development ┬─ feature/<feature-name>
                ├─ refactor/<refactor-description>
                └─ issue-fix/<issue-id>
 ```

## Commit Guidelines
 - **Format**: `[<type>] <description>` (eg. `[feature] add login system`, `[fix] fix issue #53`)
 - **Keep messages concise but descriptive**: Commit messages should be short yet meaningful. Avoid vague messages like "fix issue" or "update code." Instead, describe the change in a way that provides context for future reviewers.
 - **Break large changes into multiple commits**: Large changes should be split into smaller, logically grouped commits. This makes it easier to review and revert specific changes if needed.
 - **Additional Details in Commit Body**: If the change is complex, add a detailed explanation in the commit body below the description. Separate it from the header with a blank line.

## Issue Tracking
 - **Creating Issues**: Issues should include a clear title and a detailed description of the problem, issue or request. Follow the template for consistency:
```
Title: <Type>: <Title>
Description: <Description>
```
 - **Tags for Categorization**: Use tags to classify and prioritize issues:
   - `bug`: For errors or unintended behavior
   - `enhancement`: For feature requests or improvements
   - `question`: For requests for further information
   - `documentation`: For improvements or additions to documentation
   - `duplicate`: For issues or pull requests that already exist
   - `critical`: For issues that must be resolved immediately
   - `low-priority`: For tasks that can be delayed
   - `help-wanted`: For issues that need extra attention
   - `wontfix`: For issues that will not be worked on
 - **Prioritization**: Prioritize issues based on urgency and impact:
   - **Critical**: Blocks progress or affects key functionality
   - **High**: Impacts functionality but has a workaround
   - **Medium**: Non-critical but important improvements
   - **Low**: Minor enhancements or trivial bugs