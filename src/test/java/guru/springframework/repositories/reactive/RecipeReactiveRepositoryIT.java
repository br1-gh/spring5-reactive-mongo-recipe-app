package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
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
public class RecipeReactiveRepositoryIT {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    Recipe recipe;
    private final String RECIPE_DESCRIPTION = "Test Recipe";

    @Before
    public void setUp() {
        recipeReactiveRepository.deleteAll().block();
        recipe = new Recipe();
        recipe.setDescription(RECIPE_DESCRIPTION);
        recipeReactiveRepository.save(recipe).block();
    }

    @Test
    public void findAllAndDisplay() {
        Flux<Recipe> recipeFlux = recipeReactiveRepository.findAll();
        recipeFlux.toStream().forEach(recipe ->
                System.out.println("Recipe: id -> " + recipe.getId() + ", description -> " + recipe.getDescription()));
        Recipe foundRecipe = recipeFlux.blockFirst();
        assertNotNull(foundRecipe);
        assertEquals(foundRecipe.getDescription(), RECIPE_DESCRIPTION);
    }
}