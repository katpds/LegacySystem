package newSystemAuth.Shared.Decorators;

import newSystemAuth.Models.User.UserModel;

public abstract class UserDecorator extends UserModel {
    protected UserModel user;

    public UserDecorator(UserModel user) {
        super(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName());
        this.user = user;
    }

    public abstract boolean authenticate(String password);
}
