package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TourDetailsViewModelTest {
    private MainViewModel mockMainViewModel;
    private TourService mockTourService;
    private TourDetailsViewModel viewModel;
    @BeforeEach
    void setUp() {
        mockMainViewModel = mock(MainViewModel.class);
        mockTourService = mock(TourService.class);
        viewModel = new TourDetailsViewModel(mockMainViewModel, mockTourService);
        mockMainViewModel.setSelectedLog(null);
        mockMainViewModel.getSelectedTour().set(mockMainViewModel.getTours().get(0)); //first default tour
    }

    @Test
    public void testLoadTourDataLoadsValues() {
        viewModel.loadTourData();

        assertEquals("Vienna Bratislava", viewModel.nameProperty().get());
    }

    @Test
    public void testDeleteTourRemovesTour() {
        int sizeBefore = mockMainViewModel.getTours().size();
        viewModel.deleteTour();
        assertEquals(sizeBefore - 1, mockMainViewModel.getTours().size());
    }
    @Test
    public void testUpdateTourChangesName() {
        viewModel.loadTourData();
        viewModel.nameProperty().set("updated name");
        viewModel.updateTour();
        assertEquals("updated name", mockMainViewModel.getSelectedTour().get().getTourName());
    }
}