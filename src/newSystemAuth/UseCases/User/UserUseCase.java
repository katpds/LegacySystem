package newSystemAuth.UseCases.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Legacy.User;
import newSystemAuth.Infrastructure.drivenAdapters.AdapterPattern.LegacyUserAdapter;
import newSystemAuth.Models.Department.DepartmentModel;
import newSystemAuth.Models.Site.SiteModel;
import newSystemAuth.Models.User.UserModel;

public class UserUseCase {

    private List<User> legacyUsers;
    private List<UserModel> users;
    private List<SiteModel> sites;
    private List<DepartmentModel> departments;

    public UserUseCase() {
        legacyUsers = new ArrayList<>();
        users = new ArrayList<>();

        departments = new ArrayList<>();
        DepartmentModel hr = new DepartmentModel("Recursos Humanos");
        DepartmentModel finance = new DepartmentModel("Finanzas");
        DepartmentModel it = new DepartmentModel("IT");

        DepartmentModel payroll = new DepartmentModel("Nómina");
        DepartmentModel recruitment = new DepartmentModel("Reclutamiento");
        DepartmentModel accounting = new DepartmentModel("Contabilidad");
        DepartmentModel software = new DepartmentModel("Desarrollo de Software");
        DepartmentModel support = new DepartmentModel("Soporte Técnico");

        hr.addSubDepartment(payroll);
        hr.addSubDepartment(recruitment);
        finance.addSubDepartment(accounting);
        it.addSubDepartment(software);
        it.addSubDepartment(support);

        departments.add(hr);
        departments.add(finance);
        departments.add(it);

        SiteModel site1 = new SiteModel("Sede Central");
        SiteModel site2 = new SiteModel("Sede Norte");

        // Cargar usuarios en la memoria (simulado)
        legacyUsers.add(new User("jhojal.perea", "jhojal123", "Jhojal", "Perea"));
        legacyUsers.add(new User("edwin.tangarife", "edwin456", "Edwin", "Tangarife"));
        legacyUsers.add(new User("catherine.delgado", "cathe789", "Catherine", "Delgado"));
        legacyUsers.add(new User("mauricio.salazar", "mauro000", "Mauricio", "Salazar"));

        users.add(new UserModel("test.user1", "password1", "Test", "User1", "Admin", hr, site1));
        users.add(new UserModel("test.user2", "password2", "Test", "User2", "User", finance, site2));
        users.add(new UserModel("test.user3", "password3", "Test", "User3", "User", it, site2));
    }

    public UserUseCase(List<UserModel> users, List<User> legacyUsers, List<SiteModel> sites) {
        this.users = users != null ? users : new ArrayList<>();
        this.legacyUsers = legacyUsers != null ? legacyUsers : new ArrayList<>();
        this.sites = sites != null ? sites : new ArrayList<>();
    }

    public UserModel searchUserByUsername(String username) {
         return getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<UserModel> searchUsersByName(SiteModel site, String name) {
        return site.getAllUsers().stream()
                .filter(user -> user.getUsername().contains(name))
                .collect(Collectors.toList());
    }

    public void addUser(String username, String password, String firstName, String lastName, String role,
            DepartmentModel department, SiteModel site) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(new UserModel(username, password, firstName, lastName, role, department, site));
    }

    public List<UserModel> searchUsers(String criteria) {
        List<UserModel> result = new ArrayList<>();
        for (UserModel user : users) {
            if (user.getUsername().contains(criteria) ||
                    user.getFirstName().contains(criteria) ||
                    user.getLastName().contains(criteria) ||
                    user.getRole().contains(criteria) ||
                    (user.getDepartment() != null && user.getDepartment().getName().contains(criteria)) ||
                    (user.getSite() != null && user.getSite().getName().contains(criteria))) {
                result.add(user);
            }
        }
        return result;
    }

    public List<UserModel> searchUsersByRole(SiteModel site, String role) {
        return site.getAllUsers().stream()
                .filter(user -> user.hasRole(role)) // Agregar un método hasRole a UserModel
                .collect(Collectors.toList());
    }

    public void generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Username,FirstName,LastName,Role,Department,Site\n");

        List<UserModel> allUser = getAllUsers().stream()
                .sorted((u1, u2) -> u1.getUsername().compareTo(u2.getUsername()))
                .collect(Collectors.toList());

        for (UserModel user : allUser) {
            report.append(user.getUsername()).append(",");
            report.append(user.getFirstName()).append(",");
            report.append(user.getLastName()).append(",");
            report.append(user.getRole()).append(",");
            report.append(user.getDepartment() != null ? user.getDepartment().getName() : "").append(",");
            report.append(user.getSite() != null ? user.getSite().getName() : "").append("\n");
        }

        try (FileWriter fileWriter = new FileWriter("reporte_usuarios.csv")) {
            fileWriter.write(report.toString());
            System.out.println("Reporte generado exitosamente: reporte_usuarios.csv");
        } catch (IOException e) {
            System.err.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> allUsers = new ArrayList<>(users);
        for (User legacyUser : legacyUsers) {
            allUsers.add(new LegacyUserAdapter(legacyUser));
        }
        return allUsers;
    }

    public List<UserModel> searchUsersBySite(SiteModel site) {
        List<UserModel> siteUsers = new ArrayList<>();
        for (UserModel user : users) {
            if (user.getSite() != null && user.getSite().getName().equals(site.getName())) {
                siteUsers.add(user);
            } else if (user.getSite() == null) {
                // Crear una copia del usuario con la sede asignada como "sin sede asignada"
                UserModel userWithoutSite = new UserModel(
                        user.getUsername(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getRole(),
                        user.getDepartment(),
                        new SiteModel("Sin sede asignada"));
                siteUsers.add(userWithoutSite);
            }
        }

        for (User legacyUser : legacyUsers) {
            UserModel adaptedUser = new LegacyUserAdapter(legacyUser);
            if (adaptedUser.getSite() != null && adaptedUser.getSite().getName().equals(site.getName())) {
                siteUsers.add(adaptedUser);
            } else if (adaptedUser.getSite() == null) {
                // Crear una copia del usuario con la sede asignada como "sin sede asignada"
                UserModel userWithoutSite = new UserModel(
                        adaptedUser.getUsername(),
                        adaptedUser.getPassword(),
                        adaptedUser.getFirstName(),
                        adaptedUser.getLastName(),
                        adaptedUser.getRole(),
                        adaptedUser.getDepartment(),
                        new SiteModel("Sin sede asignada"));
                siteUsers.add(userWithoutSite);
            }
        }

        return siteUsers;
    }
}
