package newSystemAuth.UseCases.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import newSystemAuth.Models.Department.DepartmentModel;
import newSystemAuth.Models.Site.SiteModel;
import newSystemAuth.Models.User.UserModel;
import newSystemAuth.UseCases.Department.DepartmentUseCase;
import newSystemAuth.UseCases.Site.SiteUseCase;
import newSystemAuth.UseCases.User.UserUseCase;
import newSystemAuth.Shared.Decorators.AuthUserDecorator;
import newSystemAuth.Shared.Decorators.FailedAttemptsAuthDecorator;
import newSystemAuth.Shared.Decorators.RoleBasedAuthDecorator;

public class ConsoleAuth {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        autenticateUser();
    }

    public static void adminOps() {
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1.  Agregar sede");
            System.out.println("2.  Agregar departamento a sede");
            System.out.println("3.  Agregar subdepartamento");
            System.out.println("4.  Búsqueda de información de usuario");
            System.out.println("5.  Búsqueda de departamento del usuario");
            System.out.println("6.  Búsqueda de la sede del usuario");
            System.out.println("7.  Generar reporte de usuarios");
            System.out.println("8.  Listar usuarios");
            System.out.println("9.  Listar sedes");
            System.out.println("10. Listar departamentos");
            System.out.println("11. Listar subdepartamentos por departamento");
            System.out.println("12. Mover un usuario de un departamento para otro");
            System.out.println("13. Salir");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    createSite();
                    break;
                case 2:
                    addDepartmentToSite();
                    break;
                case 3:
                    addSubDepartment();
                    break;
                case 4:
                    searchUsersByName();
                    break;
                case 5:
                    searchDepartmentByUsername();
                    break;
                case 6:
                    searchSiteByUsername();
                    break;
                case 7:
                    generateReport();
                    break;
                case 8:
                    listUsers();
                    break;
                case 9:
                    listSites();
                    break;
                case 10:
                    listDepartments();
                    break;
                case 11:
                    listSubDepartments();
                    break;
                case 12:
                    moveUserToDepartment();
                    break;
                case 13:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    public static void autenticateUser() {

        UserUseCase userUseCase = new UserUseCase();

        System.out.print("Ingrese su nombre de usuario: ");
        String username = scanner.nextLine();

        UserModel user = userUseCase.searchUserByUsername(username);

        if (user != null) {

            AuthUserDecorator authUser = new FailedAttemptsAuthDecorator(user);

            authUser = new RoleBasedAuthDecorator(authUser, "Admin");

            boolean isAuthenticated = false;
            int failedAttempts = 0;

            while (!isAuthenticated && failedAttempts < FailedAttemptsAuthDecorator.MAX_ATTEMPTS) {
                System.out.print("Ingrese su contraseña: ");
                String password = scanner.nextLine();

                isAuthenticated = authUser.authenticate(password);

                if (!isAuthenticated) {
                    failedAttempts++;
                    System.out.println("Autenticación fallida. Intentos restantes: " +
                            (FailedAttemptsAuthDecorator.MAX_ATTEMPTS - failedAttempts));
                }

                if (failedAttempts >= FailedAttemptsAuthDecorator.MAX_ATTEMPTS) {
                    System.out.println("Cuenta bloqueada. Ha alcanzado el máximo de intentos fallidos.");
                    break;
                }
            }

            if (isAuthenticated) {
                System.out.println("Autenticación exitosa. ¡Bienvenido " + username + "!");
                System.out.println("Rol: " + user.getRole());
                System.out.println("Información del departamento asignado: " + user.getDepartment());
                isAdmin(user.getRole());
            }
        } else {
            System.out.println("Usuario no encontrado.");
            createUser();
        }
        scanner.close();
    }

    public static void createUser() {
        System.out.println("Creando un nuevo usuario...");
        System.out.print("Ingrese su nombre de usuario: ");
        String username = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Ingrese su primer nombre: ");
        String firstName = scanner.nextLine();
        System.out.print("Ingrese su apellido: ");
        String lastName = scanner.nextLine();
        System.out.print("Ingrese su rol: ");
        String role = scanner.nextLine();
        System.out.print("Ingrese su departamento: ");
        String departmentName = scanner.nextLine();
        System.out.print("Ingrese su sede: ");
        String siteName = scanner.nextLine();

        DepartmentModel department = new DepartmentModel(departmentName, null, new ArrayList<>());
        SiteModel site = new SiteModel(siteName, new ArrayList<>());

        UserUseCase userManager = new UserUseCase();
        userManager.addUser(username, password, firstName, lastName, role, department, site);
        System.out.println("Usuario creado exitosamente.");
    }

    public static void createSite() {
        System.out.println("Creando una nueva sede...");
        System.out.print("Ingrese el nombre de la sede: ");
        String siteName = scanner.nextLine();
        SiteModel site = new SiteModel(siteName);
        SiteUseCase siteUseCase = new SiteUseCase();
        siteUseCase.addSite(site);
        System.out.println("Sede creada exitosamente.");
    }

    private static void generateReport() {
        UserUseCase userUseCase = new UserUseCase();
        userUseCase.generateReport();
        System.out.println("Reporte generado exitosamente.");
    }

    private static boolean isAdmin(String rol) {
        if (rol.equals("Admin")) {
            System.out.println("¡Bienvenido administrador!");
            adminOps();
            return true;
        }
        return false;
    }

    private static void addDepartmentToSite() {
        System.out.print("Ingrese el nombre de la sede: ");
        String siteName = scanner.nextLine();
        System.out.print("Ingrese el nombre de la dependencia: ");
        String departmentName = scanner.nextLine();
        DepartmentModel department = new DepartmentModel(departmentName, null, new ArrayList<>());
        SiteModel site = new SiteModel(siteName, new ArrayList<>());
        SiteUseCase siteUseCase = new SiteUseCase();
        siteUseCase.addDepartmentToSite(site, department);
        System.out.println("Dependencia agregada a la sede exitosamente.");
    }

    private static void addSubDepartment() {
        DepartmentUseCase DepartmentUseCase = new DepartmentUseCase();
        System.out.print("Ingrese el nombre de la dependencia: ");
        String departmentName = scanner.nextLine();
        System.out.print("Ingrese el nombre de la subdependencia: ");
        String subDepartmentName = scanner.nextLine();
        DepartmentModel subDepartment = new DepartmentModel(subDepartmentName, null, new ArrayList<>());
        DepartmentModel department = new DepartmentModel(departmentName, null, new ArrayList<>());
        DepartmentUseCase.addSubDepartment(department, subDepartment);
        System.out.println("Subdependencia agregada a la dependencia exitosamente.");
    }

    private static void moveUserToDepartment() {
        UserUseCase userUseCase = new UserUseCase();
        DepartmentUseCase departmentUseCase = new DepartmentUseCase();
        System.out.print("Ingrese el nombre de usuario: ");
        String username = scanner.nextLine();
        UserModel user = userUseCase.searchUserByUsername(username);
        if (user != null) {
            DepartmentModel currentDepartment = user.getDepartment();
            String departmentName = currentDepartment != null ? currentDepartment.getName() : "N/A";
            System.out.print("El departamento actual del usuario es: " + departmentName + ".");

            System.out.print(" Ingrese el nombre del departamento al que quiere mover al usuario: ");
            String toDepartmentName = scanner.nextLine();
            DepartmentModel toDepartment = departmentUseCase.findDepartmentByName(toDepartmentName);
            if (toDepartment != null) {
                departmentUseCase.moveUserToDepartment(user, user.getDepartment(), toDepartment);
                System.out.println("Usuario movido exitosamente.");
            } else {
                System.out.println("Departamento destino no encontrado.");
            }

        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private static void listUsers() {
        UserUseCase userUseCase = new UserUseCase();
        List<UserModel> users = userUseCase.getAllUsers();
        if (users != null && !users.isEmpty()) {
            for (UserModel user : users) {
                System.out.println(user);

            }
        } else {
            System.out.println("No hay usuarios disponibles.");
        }
    }

    private static void listSites() {
        SiteUseCase siteUseCase = new SiteUseCase();
        List<SiteModel> sites = siteUseCase.getSites();
        for (SiteModel site : sites) {
            System.out.println(site);
        }
    }

    private static void listDepartments() {
        DepartmentUseCase departmentUseCase = new DepartmentUseCase();
        List<DepartmentModel> departments = departmentUseCase.getAllDepartments();
        if (departments != null) {
            for (DepartmentModel department : departments) {
                System.out.println(department.getName());
                listUsersByDepartment(department, "  ");
                listSubDepartments(department, "  ");
            }
        } else {
            System.out.println("No se encontraron departamentos.");
        }
    }

    private static List<DepartmentModel> listSubDepartments() {
        System.out.print("Ingrese el nombre del departamento al que quiere buscar su(s) subdepartamento(s): ");
        String departmentName = scanner.nextLine();
        DepartmentModel department = findDepartmentByName(departmentName);
        if (department != null) {
            DepartmentUseCase departmentUseCase = new DepartmentUseCase();
            List<DepartmentModel> subDepartments = departmentUseCase.getSubDepartments(department);
            for (DepartmentModel subDepartment : subDepartments) {
                System.out.println("Subdepartamento: " + subDepartment.getName());
            }
            return subDepartments;
        } else {
            System.out.println("Dependencia no encontrada.");
            return null;
        }
    }

    private static void listSubDepartments(DepartmentModel department, String indent) {
        for (DepartmentModel subDepartment : department.getSubDepartments()) {
            System.out.println(indent + subDepartment.getName());
            listUsersByDepartment(subDepartment, indent + "  ");
            listSubDepartments(subDepartment, indent + "  ");
        }
    }

    private static void listUsersByDepartment(DepartmentModel department, String indent) {
        for (UserModel user : department.getUsers()) {
            System.out.println(indent + "User: " + user.getFirstName() + " " + user.getLastName() + " ("
                    + user.getUsername() + ") - " + user.getRole());
        }
    }

    private static DepartmentModel findDepartmentByName(String departmentName) {
        DepartmentUseCase departmentUseCase = new DepartmentUseCase();
        List<DepartmentModel> departments = departmentUseCase.searchDepartments(departmentName);
        for (DepartmentModel department : departments) {
            if (department.getName().equals(departmentName)) {
                return department;
            }
        }
        return null;
    }

    private static UserModel searchUsersByName() {
        UserUseCase userUseCase = new UserUseCase();
        System.out.print("Ingrese el nombre del usuario: ");
        String username = scanner.nextLine();
        UserModel user = userUseCase.searchUserByUsername(username);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("Usuario no encontrado.");
        }
        return user;
    }

    private static void searchDepartmentByUsername() {
        UserUseCase userUseCase = new UserUseCase();
        System.out.print("Ingrese el nombre del usuario: ");
        String username = scanner.nextLine();
        UserModel user = userUseCase.searchUserByUsername(username);
        if (user != null) {
            DepartmentModel department = user.getDepartment();
            if (department != null) {
                System.out.println("Departamento: " + department.getName());
            } else {
                System.out.println("Usuario sin departamento asignado.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private static void searchSiteByUsername() {
        UserUseCase userUseCase = new UserUseCase();
        System.out.print("Ingrese el nombre del usuario: ");
        String username = scanner.nextLine();
        UserModel user = userUseCase.searchUserByUsername(username);
        if (user != null) {
            SiteModel site = user.getSite();
            if (site != null) {
                System.out.println("Sede: " + site.getName());
            } else {
                System.out.println("Usuario sin sede asignado.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
}
