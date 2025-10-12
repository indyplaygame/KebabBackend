package api.indy.kebab.repository;

import api.indy.kebab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("NewClassNamingConvention")
interface CategoryRepository extends JpaRepository<Category, Long> {
}
