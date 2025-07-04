package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NewTourViewModelTest {

    private NewTourViewModel viewModel;
    private MainViewModel mainViewModel;
    private TourService tourService;

    static {
        //start JavaFX platform once
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // JavaFX already started
        }
    }

    @BeforeEach
    void setUp() {
        mainViewModel = mock(MainViewModel.class);
        tourService = mock(TourService.class);
        viewModel = new NewTourViewModel(mainViewModel, tourService);
    }

    @Test
        // Test 1: Validates with all fields filled
    void testValidate_allFieldsFilled_returnsTrue() {
        viewModel.nameProperty().set("Tour A");
        viewModel.descriptionProperty().set("Description");
        viewModel.fromProperty().set("Vienna");
        viewModel.toProperty().set("Graz");
        viewModel.transportTypeProperty().set("car");

        assertTrue(viewModel.validate());
    }

    @Test
        // Test 2: Validates fails with missing name
    void testValidate_missingRequiredFields_returnsFalse() {
        viewModel.nameProperty().set(""); // Missing name
        viewModel.descriptionProperty().set("desc");
        viewModel.fromProperty().set("Start");
        viewModel.toProperty().set("End");
        viewModel.transportTypeProperty().set("walk");

        assertFalse(viewModel.validate());
    }

    @Test
        // Test 3: Valid tour creation triggers backend
    void testCreateTour_validInput_callsAddTourAndClearsFields() throws InterruptedException {
        viewModel.nameProperty().set("Tour A");
        viewModel.descriptionProperty().set("Nice tour");
        viewModel.fromProperty().set("Vienna");
        viewModel.toProperty().set("Linz");
        viewModel.transportTypeProperty().set("car");

        Tour createdTour = new Tour("Tour A", "Nice tour", "Vienna", "Linz", "car");
        when(tourService.createTourAsync(any(Tour.class))).thenReturn(CompletableFuture.completedFuture(createdTour));

        boolean result = viewModel.createTour();

        Thread.sleep(100); //let Platform.runLater execute

        assertTrue(result);
        verify(tourService).createTourAsync(any(Tour.class));
        verify(mainViewModel).addTour(any());

        assertEquals("", viewModel.nameProperty().get());
        assertEquals("", viewModel.descriptionProperty().get());
        assertEquals("", viewModel.fromProperty().get());
        assertEquals("", viewModel.toProperty().get());
        assertEquals("", viewModel.transportTypeProperty().get());
    }

    @Test
        // Test 4: Invalid form should skip backend
    void testCreateTour_invalidInput_returnsFalseAndDoesNotCallService() {
        viewModel.nameProperty().set(""); // Invalid
        viewModel.descriptionProperty().set("test");
        viewModel.fromProperty().set("A");
        viewModel.toProperty().set("B");
        viewModel.transportTypeProperty().set("walk");

        boolean result = viewModel.createTour();

        assertFalse(result);
        verify(tourService, never()).createTourAsync(any(Tour.class));
        verify(mainViewModel, never()).addTour(any());
    }

    @Test
        //Test 5:  Delegates search to service
    void testFetchLocationSuggestions_callsServiceAndReturnsList() {
        String input = "Vie";
        List<String> expected = List.of("Vienna", "Viena", "Vienne");

        when(tourService.fetchLocationSuggestions(input)).thenReturn(expected);

        List<String> result = viewModel.fetchLocationSuggestions(input);

        assertEquals(expected, result);
        verify(tourService).fetchLocationSuggestions(input);
    }
}
