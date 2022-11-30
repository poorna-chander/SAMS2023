import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class PcmOpsTest {
	PcmOps obj=new PcmOps();

	@Test
	@DisplayName("Assert Equal")
	public void equalityassignPaperToPCM()
	{
		
		boolean expectedResult = true;
		boolean result=obj.assignPaperToPCM(2,3);
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert NotNull")
	public void addassertNotNull()
	{
		Assertions.assertNotNull(obj);
	}
	@Test
	@DisplayName("Assert Null")
	public void addassertNull()
	{
		Assertions.assertNull(obj);
	}
}
