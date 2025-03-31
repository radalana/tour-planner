package at.technikum_wien.tourplanner.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourDetailsViewModelTest {
    private MainViewModel mainViewModel;
    private TourDetailsViewModel viewModel;
    @BeforeEach
    void setUp() {
        mainViewModel = new MainViewModel();
        viewModel = new TourDetailsViewModel(mainViewModel);
        mainViewModel.setSelectedLog(null);
        mainViewModel.getSelectedTour().set(mainViewModel.getTours().get(0)); //first default tour
    }

    @Test
    public void testLoadTourDataLoadsValues() {
        viewModel.loadTourData();

        assertEquals("Vienna Bratislava", viewModel.nameProperty().get());
    }

    @Test
    public void testDeleteTourRemovesTour() {
        int sizeBefore = mainViewModel.getTours().size();
        viewModel.deleteTour();
        assertEquals(sizeBefore - 1, mainViewModel.getTours().size());
    }
    @Test
    public void testUpdateTourChangesName() {
        viewModel.loadTourData();
        viewModel.nameProperty().set("updated name");
        viewModel.updateTour();
        assertEquals("updated name", mainViewModel.getSelectedTour().get().getName());
    }
}