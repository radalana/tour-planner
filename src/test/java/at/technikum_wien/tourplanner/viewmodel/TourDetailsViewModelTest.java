package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.application.Platform;
import javafx.beans.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TourDetailsViewModelTest {

    private TourDetailsViewModel viewModel;
    private MainViewModel mainViewModel;
    private TourService tourService;
    private Tour selectedTour;

    static {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
        }
    }

    @BeforeEach
    void setUp() {
        mainViewModel = mock(MainViewModel.class);
        tourService = mock(TourService.class);

        selectedTour = new Tour();
        selectedTour.setTourName("Alps Tour");
        selectedTour.setDescription("Exciting journey");
        selectedTour.setFrom("Vienna");
        selectedTour.setTo("Innsbruck");
        selectedTour.setTransportType("car");
        selectedTour.setId(1L);

        selectedTour.tourNameProperty().set("Alps Tour");
        selectedTour.descriptionProperty().set("Exciting journey");
        selectedTour.fromProperty().set("Vienna");
        selectedTour.toProperty().set("Innsbruck");
        selectedTour.transportTypeProperty().set("car");
        selectedTour.distanceProperty().set(250.0);
        selectedTour.estimatedTimeProperty().set("3h");

        ObjectProperty<Tour> selected = new SimpleObjectProperty<>(selectedTour);
        when(mainViewModel.getSelectedTour()).thenReturn(selected);

        viewModel = new TourDetailsViewModel(mainViewModel, tourService);
    }

    @Test
        // Test 1: All fields filled = valid
    void testValidate_allFieldsValid_setsValidTrue() {
        viewModel.validate();
        assertTrue(viewModel.validFormProperty().get());
    }

    @Test
        // Test 2: Missing name = invalid
    void testValidate_missingFields_setsValidFalse() {
        selectedTour.tourNameProperty().set("");
        viewModel.validate();
        assertFalse(viewModel.validFormProperty().get());
    }

    @Test
        // Test 3: call removeTour if deleted
    void testDeleteTour_validSelection_callsRemoveTour() throws InterruptedException {
        when(tourService.deleteTourAsync(selectedTour)).thenReturn(CompletableFuture.completedFuture(true));

        viewModel.deleteTour();

        Thread.sleep(100); // Wait for Platform.runLater

        verify(mainViewModel).removeTour(selectedTour);
    }
}
