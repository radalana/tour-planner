package at.technikum_wien.tourplanner.viewmodel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import at.technikum_wien.tourplanner.httpClient.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NewTourViewModelTest {
    private NewTourViewModel viewModel;
    private MainViewModel mockMain;
    private TourService mockService;
    @BeforeEach
    public void setup() {
        mockMain = mock(MainViewModel.class);
        mockService = mock(TourService.class);
        viewModel = new NewTourViewModel(mockMain, mockService);}

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
    public void testCreateTourAddsTourToList() {
        viewModel.nameProperty().set("Trip");
        viewModel.descriptionProperty().set("desc");
        viewModel.fromProperty().set("from");
        viewModel.toProperty().set("to");
        viewModel.transportTypeProperty().set("Train");
        viewModel.estTimeProperty().set("1");
        viewModel.distanceProperty().set(10);

        boolean saved = viewModel.createTour();
        assertTrue(saved);
    }
}