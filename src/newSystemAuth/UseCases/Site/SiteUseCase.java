package newSystemAuth.UseCases.Site;

import java.util.ArrayList;
import java.util.List;

import newSystemAuth.Models.Department.DepartmentModel;
import newSystemAuth.Models.Site.SiteModel;

public class SiteUseCase {

    private List<SiteModel> sites;

    public SiteUseCase() {
        sites = new ArrayList<>();

        // Precargar sedes y departamentos
        SiteModel site1 = new SiteModel("Sede Central");
        SiteModel site2 = new SiteModel("Sede Norte");

        DepartmentModel dept1 = new DepartmentModel("Recursos Humanos");
        DepartmentModel dept2 = new DepartmentModel("Finanzas");
        DepartmentModel dept3 = new DepartmentModel("IT");

        site1.getDepartments().add(dept1);
        site1.getDepartments().add(dept2);
        site2.getDepartments().add(dept3);

        sites.add(site1);
        sites.add(site2);

    }

    public List<SiteModel> getSites() {
        return sites;
    }

    public void addSite(SiteModel site) {
        if (sites == null) {
            sites = new ArrayList<>();
        }
        sites.add(site);
    }

    public void addDepartmentToSite(SiteModel site, DepartmentModel department) {
        site.getDepartments().add(department);
    }

    public void addDepartmentToSite(String siteName, String departmentName) {
        for (SiteModel site : sites) {
            if (site.getName().equals(siteName)) {
                site.addDepartment(new DepartmentModel(departmentName));
                return;
            }
        }
    }
}
