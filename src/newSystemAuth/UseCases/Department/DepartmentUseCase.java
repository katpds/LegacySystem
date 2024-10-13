package newSystemAuth.UseCases.Department;

import java.util.ArrayList;
import java.util.List;

import newSystemAuth.Models.Department.DepartmentModel;
import newSystemAuth.Models.Site.SiteModel;
import newSystemAuth.Models.User.UserModel;

public class DepartmentUseCase {

    private List<DepartmentModel> departments;
    private List<SiteModel> sites;

    public DepartmentUseCase() {

        departments = new ArrayList<>();
        // Create main departments
        DepartmentModel hr = new DepartmentModel("Recursos Humanos");
        DepartmentModel finance = new DepartmentModel("Finanzas");
        DepartmentModel it = new DepartmentModel("IT");

        // Create subdepartments
        DepartmentModel payroll = new DepartmentModel("Nómina");
        DepartmentModel recruitment = new DepartmentModel("Reclutamiento");
        DepartmentModel accounting = new DepartmentModel("Contabilidad");
        DepartmentModel software = new DepartmentModel("Desarrollo de Software");
        DepartmentModel support = new DepartmentModel("Soporte Técnico");

        // Assign subdepartments to main departments
        hr.addSubDepartment(payroll);
        hr.addSubDepartment(recruitment);
        finance.addSubDepartment(accounting);
        it.addSubDepartment(software);
        it.addSubDepartment(support);

        SiteModel site1 = new SiteModel("Sede Central");
        SiteModel site2 = new SiteModel("Sede Norte");

        hr.addUser(new UserModel("test.user1", "password1", "Test", "User1", "Admin", hr, site1));
        finance.addUser(new UserModel("test.user2", "password2", "Test", "User2", "User", finance, site2));
        it.addUser(new UserModel("test.user3", "password3", "Test", "User3", "User", it, site2));

        departments.add(hr);
        departments.add(finance);
        departments.add(it);
    }

    public void moveUserToDepartment(UserModel user, DepartmentModel fromDepartment, DepartmentModel toDepartment) {
        if (user == null || toDepartment == null) {
            throw new IllegalArgumentException("Usuario o departamento destino no puede ser null");
        }

        if (fromDepartment != null) {
            fromDepartment.getUsers().remove(user);
        }

        toDepartment.addUser(user);
        user.setDepartment(toDepartment);
    }

    public List<DepartmentModel> getSubDepartments(DepartmentModel department) {
        return department.getSubDepartments();
    }

    public List<DepartmentModel> getAllDepartments() {
        return departments != null ? departments : new ArrayList<>();
    }

    public DepartmentModel findDepartmentByName(String departmentName) {
        if (departmentName == null || departmentName.isEmpty()) {
            return null;
        }

        return departments.stream()
                .filter(department -> department.getName().equals(departmentName))
                .findFirst()
                .orElse(null);
    }

    public List<DepartmentModel> searchDepartments(String criteria) {
        List<DepartmentModel> result = new ArrayList<>();
        for (DepartmentModel department : departments) {
            if (department.getName().contains(criteria)) {
                result.add(department);
            }
        }
        return result;
    }

    public void addSubDepartment(DepartmentModel departmentParent, DepartmentModel subDeparment) {
        departmentParent.getSubDepartments().add(subDeparment);
    }

    public void addSubDepartment(String departmentParentName, String subDeparmentName) {
        for (SiteModel site : sites) {
            for (DepartmentModel department : site.getDepartments()) {
                if (department.getName().equals(departmentParentName)) {
                    department.addSubDepartment(new DepartmentModel(subDeparmentName, department));
                    return;
                }
            }
        }
    }

}
