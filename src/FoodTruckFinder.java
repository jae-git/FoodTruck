
public class FoodTruckFinder {

	public static void main(String[] args) {
 		try {
			DirectAPICallPresenter presenter = new DirectAPICallPresenter();

			presenter.interactivePresentation();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	

	/*
	public static void main(String[] args) {
 		try {
			CachedPresenter presenter = new CachedPresenter();

			presenter.interactivePresentation();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}	
	*/
}

