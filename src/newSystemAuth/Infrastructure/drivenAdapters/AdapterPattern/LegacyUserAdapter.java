package newSystemAuth.Infrastructure.drivenAdapters.AdapterPattern;

import Legacy.User;
import newSystemAuth.Models.Department.DepartmentModel;
import newSystemAuth.Models.Site.SiteModel;
import newSystemAuth.Models.User.UserModel;

public class LegacyUserAdapter extends UserModel {

    private User legacyUser;

    // Constructor adaptador
    public LegacyUserAdapter(User legacyUser) {
        super(
            legacyUser.getUserName(), 
            legacyUser.getPassword(), 
            legacyUser.getFirstName(), 
            legacyUser.getLastName(),
            "Empleado",  // Valor por defecto de rol
            new DepartmentModel("Ventas"),  // Valor por defecto de departamento
            new SiteModel("Sede Central")  // Valor por defecto de sitio
        );
        this.legacyUser = legacyUser;
    }

    public LegacyUserAdapter(User legacyUser, String role, DepartmentModel department, SiteModel site) {
        super(
            legacyUser.getUserName(), 
            legacyUser.getPassword(), 
            legacyUser.getFirstName(), 
            legacyUser.getLastName(), 
            role, 
            department, 
            site
        );
        this.legacyUser = legacyUser;
    }

    // Métodos getter de la clase adaptada
    @Override
    public String getUsername() {
        return legacyUser.getUserName();
    }

    @Override
    public String getPassword() {
        return legacyUser.getPassword();
    }

    @Override
    public String getFirstName() {
        return legacyUser.getFirstName();
    }

    @Override
    public String getLastName() {
        return legacyUser.getLastName();
    }
}
