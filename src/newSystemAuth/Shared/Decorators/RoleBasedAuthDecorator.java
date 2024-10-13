package newSystemAuth.Shared.Decorators;

import newSystemAuth.Models.User.UserModel;

public class RoleBasedAuthDecorator extends AuthUserDecorator {
    private String role;

    public RoleBasedAuthDecorator(UserModel user, String role) {
        super(user);
        this.role = role;
    }

    @Override
    public boolean authenticate(String password) {
        boolean isAuthenticated = super.authenticate(password);
        if (isAuthenticated) {
            return authorizeRoleAccess(role);
        }
        return false;
    }

    private boolean authorizeRoleAccess(String role) {
        // Lógica para autorizar acceso según el rol
        return role.equals("Admin") || role.equals("Manager"); // Solo Admins y Managers
    }
}
