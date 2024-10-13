package newSystemAuth.Models.User;

import newSystemAuth.Models.Department.DepartmentModel;
import newSystemAuth.Models.Site.SiteModel;

public class UserModel {
    private String username;
    private String password;
    private String firstName;
    private String lastName; 
    private String role;
    private DepartmentModel department;
    private SiteModel site;

    public UserModel(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = "Empleado";  
        this.department = new DepartmentModel("Ventas");
        this.site = new SiteModel("Bogotá");
    }

    public UserModel(String username, String password, String firstName, String lastName, String role, DepartmentModel department, SiteModel site) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.site = site;
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public SiteModel getSite() {
        return site;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void setDepartment(DepartmentModel department) {
        this.department = department;

    }

    public void setSite(SiteModel site) {
        this.site = site;

    }

    public boolean hasRole(String role) {
        return this.role != null && this.role.equals(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", department=" + (department != null ? department.getName() : "N/A") +
                ", site=" + (site != null ? site.getName() : "N/A") +
                '}';
    }
    
}