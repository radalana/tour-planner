package at.technikum_wien.tourplanner.viewmodel;

import static org.junit.jupiter.api.Assertions.*;
import at.technikum_wien.tourplanner.model.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpRequest;
/*
class AddLogViewModelTest {
    private AddLogViewModel viewModel;
    @BeforeEach
    void setUp() {
        MainViewModel main = new MainViewModel();
        Tour tour = new Tour("Test", "desc", "from", "to", "car", 10.0, 1.1, "info");
        main.addTour(tour);
        main.getSelectedTour().set(tour);
        viewModel = new AddLogViewModel(main);
    }
    @Test
    public void testAddLogFailsWhenDateIsEmpty() {
        viewModel.durationProperty().set("1h");
        viewModel.distanceProperty().set("10");
        viewModel.commentProperty().set("test");
        viewModel.difficultyProperty().set("Easy");
        assertFalse(viewModel.addLog());
    }
    @Test
    public void testAddLogSucceedsWithValidData() {
        viewModel.dateProperty().set("2025-03-30");
        viewModel.durationProperty().set("1h");
        viewModel.distanceProperty().set("10");
        viewModel.commentProperty().set("test");
        viewModel.difficultyProperty().set("Easy");
        assertTrue(viewModel.addLog());}

    @Test
    public void testClearFormClearsAllFields() {
        viewModel.dateProperty().set("x");
        viewModel.clearForm();
        assertEquals("", viewModel.dateProperty().get());
    }
}

 */