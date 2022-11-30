import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class PapersUtilTest {
	PapersUtil obj=new PapersUtil();
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetNextId()
	{
		
		int expectedResult = 5;
		int result=obj.getNextId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityisSavePapersSuccessful()
	{
		
		boolean expectedResult = False;
		boolean result=obj.isSavePapersSuccessful();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityinsertPaperDetails()
	{
		
		boolean expectedResult = True;
		boolean result=obj.insertPaperDetails();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityupdatePaperDetails()
	{
		
		boolean expectedResult = true;
		boolean result=obj.updatePaperDetails();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitydeletePaperDetails()
	{
		
		boolean expectedResult = true;
		boolean result=obj.deletePaperDetails();
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
