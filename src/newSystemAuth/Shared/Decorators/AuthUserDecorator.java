package newSystemAuth.Shared.Decorators;

import newSystemAuth.Models.User.UserModel;

public class AuthUserDecorator extends UserDecorator {
    public AuthUserDecorator(UserModel user) {
        super(user);
    }

    @Override
    public boolean authenticate(String password) {
        return this.user.getPassword().equals(password);
    }
}
