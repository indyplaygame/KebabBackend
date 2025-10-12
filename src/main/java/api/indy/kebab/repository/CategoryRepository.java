package api.indy.kebab.repository;

import api.indy.kebab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("NewClassNamingConvention")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category getCategoryById(long id);
}
