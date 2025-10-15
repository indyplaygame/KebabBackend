package api.indy.kebab.repository;

import api.indy.kebab.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("NewClassNamingConvention")
public interface MenuRepository extends JpaRepository<MenuItem, Long> {

    public MenuItem findByMenuItemId(long id);
}
