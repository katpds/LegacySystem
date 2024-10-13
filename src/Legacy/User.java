package Legacy;

public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    
    public User(String username, String password, String firstName, String lastName) {  
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }   
    
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }    
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }    
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }    
    public void setUserName(String UserName) {
        this.userName = UserName;
    }    
    
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkUserNameAndPassword(String userName, String password) {
        return this.userName.equals(userName) && this.password.equals(password);
    }
}
