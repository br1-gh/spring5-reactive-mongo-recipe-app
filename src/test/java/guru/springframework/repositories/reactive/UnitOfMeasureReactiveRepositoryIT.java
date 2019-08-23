package guru.springframework.repositories.reactive;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryIT {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    UnitOfMeasure unitOfMeasure;

    @Before
    public void setUp() {
        unitOfMeasureReactiveRepository.deleteAll().block();
        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Test Unit");
        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
    }

    @Test
    public void findAllAndDisplay() {
        final String UOM_DESCRIPTION = "Test Unit";
        final Flux<UnitOfMeasure> allUoms = unitOfMeasureReactiveRepository.findAll();
        allUoms.toStream().forEach(uom ->
                System.out.println("UOM: id -> " + uom.getId() + ", description -> " + uom.getDescription()));

        assertEquals(1, allUoms.count().block().intValue());
        assertEquals(UOM_DESCRIPTION, allUoms.blockFirst().getDescription());
    }
}