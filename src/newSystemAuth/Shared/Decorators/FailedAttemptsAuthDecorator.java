package newSystemAuth.Shared.Decorators;

import newSystemAuth.Models.User.UserModel;

public class FailedAttemptsAuthDecorator extends AuthUserDecorator {

    private int failedAttempts = 0;
    public static final int MAX_ATTEMPTS = 5;

    public FailedAttemptsAuthDecorator(UserModel user) {
        super(user);
    }

    @Override
    public boolean authenticate(String password) {
        if (failedAttempts >= MAX_ATTEMPTS) {
            System.out.println("Cuenta bloqueada. Demasiados intentos fallidos.");
            return false;
        }

        boolean isAuthenticated = super.authenticate(password);
        if (!isAuthenticated) {
            failedAttempts++;
            System.out.println("Intento fallido. Intentos restantes: " + (MAX_ATTEMPTS - failedAttempts));
        } else {
            failedAttempts = 0; // Resetear si la autenticación es exitosa
        }

        return isAuthenticated;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }
}
