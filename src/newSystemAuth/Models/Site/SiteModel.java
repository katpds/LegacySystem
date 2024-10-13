package newSystemAuth.Models.Site;

import java.util.ArrayList;
import java.util.List;

import newSystemAuth.Models.Department.DepartmentModel;
import newSystemAuth.Models.User.UserModel;
import newSystemAuth.UseCases.User.UserUseCase;

public class SiteModel {
    private String name;
    private List<DepartmentModel> departments;

    public SiteModel(String name) {
        this.name = name;
        this.departments = new ArrayList<>();
    }

    public SiteModel(String name, List<DepartmentModel> departments) {
        this.name = name;
        this.departments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addDepartment(DepartmentModel department) {
        departments.add(department);
    }

    public List<DepartmentModel> getDepartments() {
        return departments;
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> allUsers = new ArrayList<>();
        for (DepartmentModel department : departments) {
            allUsers.addAll(department.getUsers());
        }
        return allUsers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SiteModel{name='").append(name).append("', departments=[");
        for (DepartmentModel department : departments) {
            sb.append(department.getName()).append(", ");
        }
        sb.append("], users=[");
        UserUseCase userUseCase = new UserUseCase();
        for (UserModel user : userUseCase.searchUsersBySite(this)) {
            sb.append(user.getUsername()).append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }
}
