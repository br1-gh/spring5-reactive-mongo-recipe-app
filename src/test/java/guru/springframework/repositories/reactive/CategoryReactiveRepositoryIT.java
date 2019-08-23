package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    Category category;
    private final String CATEGORY_DESCRIPTION = "Test Category";

    @Before
    public void setUp() {
        categoryReactiveRepository.deleteAll().block();
        category = new Category();
        category.setDescription(CATEGORY_DESCRIPTION);
        categoryReactiveRepository.save(category).block();
    }

    @Test
    public void findAllAndDisplay() {
        Flux<Category> categoryFlux = categoryReactiveRepository.findAll();
        categoryFlux.toStream().forEach(category ->
                System.out.println("Category: id -> " + category.getId()
                        + ", description -> " + category.getDescription()));
        Category foundCategory = categoryFlux.blockFirst();
        assertNotNull(foundCategory);
        assertEquals(foundCategory.getDescription(), CATEGORY_DESCRIPTION);
    }
}