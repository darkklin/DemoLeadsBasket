package libry;


import libry.MyReport;
import com.codeborne.selenide.testng.annotations.Report;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.ConstructorOrMethod;

/**
 * Reports for all method of annotated class in the suite.
 * Annotate any test class in your suite with {@code @Listeners({TextReport.class})}
 * Annotate test classes to be reported with {@code @{@link Report}}
 * @since Selenide 3.6
 *
 * Use either {@link TextReport} or {@link GlobalTextReport}, never both
 */
public class TextReport implements IInvokedMethodListener {
  protected MyReport report = new MyReport();

  public static boolean onFailedTest = true;
  public static boolean onSucceededTest = true;
  
  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    if (onFailedTest || onSucceededTest) {
      if (isClassAnnotatedWithReport(method)) {
        report.start();
      }
    }
  }

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    if (testResult.isSuccess() && onSucceededTest || !testResult.isSuccess() && onFailedTest) {
      if (isClassAnnotatedWithReport(method)) {
        report.finish(testResult.getName());
      }
    }
    report.clean();
  }

  private boolean isClassAnnotatedWithReport(IInvokedMethod method) {
    ConstructorOrMethod consOrMethod = method.getTestMethod().getConstructorOrMethod();
    Report annotation = consOrMethod.getDeclaringClass().getAnnotation(Report.class);
    return annotation != null;
  }

}