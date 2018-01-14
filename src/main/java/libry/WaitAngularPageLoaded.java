package libry;

import static com.codeborne.selenide.Selenide.*;

public class WaitAngularPageLoaded {

	public void waitUntilAngularPageLoaded() {
		String angularFullyLoaded = "false";
		while (angularFullyLoaded == "false") {
			angularFullyLoaded = executeJavaScript(
					"return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)")
							.toString();
		
		}
	}

}
