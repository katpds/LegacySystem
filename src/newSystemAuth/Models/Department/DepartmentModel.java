package newSystemAuth.Models.Department;

import java.util.ArrayList;
import java.util.List;

import newSystemAuth.Models.User.UserModel;

public class DepartmentModel {
    private String name;
    private DepartmentModel departmentParent;
    private DepartmentModel department;
    private List<UserModel> users;
    private List<DepartmentModel> subDepartments;
    private List<DepartmentModel> departments;

    public DepartmentModel(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.subDepartments = new ArrayList<>();
    }

    public DepartmentModel(String name, DepartmentModel departmentParent) {
        this.name = name;
        this.departmentParent = departmentParent;
        this.subDepartments = new ArrayList<>();
    }

    public DepartmentModel(String name, List<UserModel> users, List<DepartmentModel> subDepartments) {
        this.name = name;
        this.users = new ArrayList<>();
        this.subDepartments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentModel getDepartment() {
        return department;
    }
    
    public DepartmentModel getDepartmentParent() {
        return departmentParent;
    }

    public List<DepartmentModel> getSubDepartments() {
        return subDepartments;
    }
    
    public List<DepartmentModel> getDepartments() {
        return departments;
    }

    public void setDepartmentParent(DepartmentModel departmentParent) {
        this.departmentParent = departmentParent;
    }

    public void addUser(UserModel user) {
        users.add(user);
    }

    public void addSubDepartment(DepartmentModel department) {
        this.subDepartments.add(department);
    }

    public void addSubDepartment(List<DepartmentModel> departments) {
        subDepartments.addAll(departments);
    }

    public List<UserModel> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "DepartmentModel{" +
                "name='" + name + '\'' +
                ", departmentParent=" + departmentParent +
                ", users=" + users +
                ", subDepartments=" + subDepartments +
                '}';
    }
    
}
