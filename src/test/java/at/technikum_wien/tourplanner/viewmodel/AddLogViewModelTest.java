package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourLogService;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddLogViewModelTest {

    private AddLogViewModel viewModel;
    private MainViewModel mainViewModel;
    private TourLogService tourLogService;
    private Tour selectedTour;

    static {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @BeforeEach
    void setUp() throws Exception {
        mainViewModel = mock(MainViewModel.class);
        tourLogService = mock(TourLogService.class);

        selectedTour = new Tour();
        selectedTour.setId(1L);

        // inject observableLogs field using reflection
        Field logsField = Tour.class.getDeclaredField("observableLogs");
        logsField.setAccessible(true);
        logsField.set(selectedTour, FXCollections.observableArrayList());

        ObjectProperty<Tour> selectedTourProp = new SimpleObjectProperty<>(selectedTour);
        when(mainViewModel.getSelectedTour()).thenReturn(selectedTourProp);
        when(mainViewModel.selectedLogProperty()).thenReturn(new SimpleObjectProperty<>());

        viewModel = new AddLogViewModel(mainViewModel, tourLogService);
    }

    @Test
        // Test 1: All fields valid → validation passes
    void testValidateFields_allValid_returnsTrue() {
        viewModel.dateProperty().set("2025-07-01");
        viewModel.commentProperty().set("Beautiful trip");
        viewModel.difficultyProperty().set("Easy");
        viewModel.distanceProperty().set("12.5");
        viewModel.ratingProperty().set("4");

        viewModel.durationDaysProperty().set(0);
        viewModel.durationHoursProperty().set(2);
        viewModel.durationMinutesProperty().set(30);

        assertTrue(viewModel.validFormProperty().get());
    }

    @Test
        // Test 2: Invalid distance format → validation fails
    void testValidateFields_invalidDistance_returnsFalse() {
        viewModel.dateProperty().set("2025-07-01");
        viewModel.commentProperty().set("Bad input");
        viewModel.difficultyProperty().set("Moderate");
        viewModel.distanceProperty().set("abc");  // Invalid
        viewModel.ratingProperty().set("3");

        viewModel.durationDaysProperty().set(0);
        viewModel.durationHoursProperty().set(1);
        viewModel.durationMinutesProperty().set(15);

        assertFalse(viewModel.validFormProperty().get());
    }

    @Test
        // Test 3: Add valid log → log is added to tour logs
    void testAddLogAsync_validLog_addsToTourLogs() throws Exception {
        // Arrange valid data
        viewModel.dateProperty().set("2025-07-01");
        viewModel.commentProperty().set("Nice ride");
        viewModel.difficultyProperty().set("Easy");
        viewModel.distanceProperty().set("10");
        viewModel.ratingProperty().set("5");
        viewModel.durationHoursProperty().set(1);
        viewModel.durationMinutesProperty().set(10);

        TourLog createdLog = new TourLog();
        createdLog.setId(101L);
        createdLog.setComment("Nice ride");

        when(tourLogService.createLogAsync(any(), eq(1L)))
                .thenReturn(CompletableFuture.completedFuture(createdLog));

        // Act
        CompletableFuture<Boolean> future = viewModel.addLogAsync();
        Thread.sleep(150); // Let Platform.runLater() execute

        // Assert
        assertTrue(future.get());
        ObservableList<TourLog> logs = selectedTour.getObservableLogs();
        assertEquals(1, logs.size());
        assertEquals("Nice ride", logs.get(0).getComment());
    }

    @Test
        // Test 4: Sets difficulty label correctly from numeric
    void testSetDifficultyFromNumeric_setsCorrectLabel() {
        viewModel.setDifficultyFromNumeric(1);
        assertEquals("Easy", viewModel.difficultyProperty().get());

        viewModel.setDifficultyFromNumeric(2);
        assertEquals("Moderate", viewModel.difficultyProperty().get());

        viewModel.setDifficultyFromNumeric(3);
        assertEquals("Hard", viewModel.difficultyProperty().get());

        viewModel.setDifficultyFromNumeric(0);
        assertEquals("", viewModel.difficultyProperty().get());
    }
}
