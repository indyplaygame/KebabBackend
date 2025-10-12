package api.indy.kebab.repository;

import api.indy.kebab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SuppressWarnings("NewClassNamingConvention")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.categoryId = ?1")
    public Category getCategoryById(long id);
}
