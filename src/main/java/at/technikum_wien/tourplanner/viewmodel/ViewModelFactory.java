package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.ServiceProvider;
import lombok.Getter;

public class ViewModelFactory {
    private final ServiceProvider serviceProvider;
    @Getter
    private final MainViewModel mainViewModel;

    public ViewModelFactory(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        this.mainViewModel = new MainViewModel();
    }

    public TourTableViewModel getTourTableViewModel() {
        return new TourTableViewModel(mainViewModel, serviceProvider.getTourService());
    }
    public NewTourViewModel getNewTourViewModel() {
        return new NewTourViewModel(mainViewModel, serviceProvider.getTourService());
    }
    public TourDetailsViewModel getTourDetailsViewModel() {
        return new TourDetailsViewModel(mainViewModel, serviceProvider.getTourService());
    }

    public TourLogViewModel getTourLogViewModel() {
        return new TourLogViewModel(mainViewModel);
    }

    public AddLogViewModel getAddLogViewModel() {
        return new AddLogViewModel(mainViewModel, serviceProvider.getTourLogService());
    }

    public HeaderViewModel getHeaderViewModel() {
        return new HeaderViewModel(mainViewModel, serviceProvider.getTourService());
    }
}
