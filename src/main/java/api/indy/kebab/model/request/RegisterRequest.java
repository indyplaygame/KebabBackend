package api.indy.kebab.model.request;

public record RegisterRequest(String username, String email, String firstName, String middleName, String lastName, String dateOfBirth, String password) {}