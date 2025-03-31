package at.technikum_wien.tourplanner.viewmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class NewTourViewModelTest {
    private NewTourViewModel viewModel;
    @BeforeEach
    public void setup() {
        MainViewModel mockMain = new MainViewModel();
        viewModel = new NewTourViewModel(mockMain);}

    @Test
    public void testValidationFailsWhenNameIsEmpty() {
        viewModel.nameProperty().set("");  // Invalid
        viewModel.descriptionProperty().set("desc");
        viewModel.fromProperty().set("from");
        viewModel.toProperty().set("to");
        viewModel.transportTypeProperty().set("Train");
        viewModel.estTimeProperty().set("1");
        viewModel.distanceProperty().set(10);
        assertFalse(viewModel.validate());
    }
    @Test
    public void testValidationPassesForValidInput() {
        viewModel.nameProperty().set("Trip");
        viewModel.descriptionProperty().set("desc");
        viewModel.fromProperty().set("from");
        viewModel.toProperty().set("to");
        viewModel.transportTypeProperty().set("Train");
        viewModel.estTimeProperty().set("1");
        viewModel.distanceProperty().set(10);
        assertTrue(viewModel.validate());
    }

    @Test
    public void testSaveTourAddsTourToList() {
        viewModel.nameProperty().set("Trip");
        viewModel.descriptionProperty().set("desc");
        viewModel.fromProperty().set("from");
        viewModel.toProperty().set("to");
        viewModel.transportTypeProperty().set("Train");
        viewModel.estTimeProperty().set("1");
        viewModel.distanceProperty().set(10);

        boolean saved = viewModel.saveTour();
        assertTrue(saved);
    }
}