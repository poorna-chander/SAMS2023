import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class PaperChoicesUtilTest {
	PaperChoicesUtil obj=new PaperChoicesUtil();
	@Test
	@DisplayName("Assert Equal")
	public void equalityGetNextPaperChoiceid()
	{
		
		int expectedResult = 5;
		int result=obj.getNextPaperChoiceId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityisSavePaper()
	{
		
		boolean expectedResult = False;
		boolean result=obj.isSavePaperChoicesSuccessful();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityinsertPaperChoice()
	{
		
		boolean expectedResult = True;
		boolean result=obj.insertPaperChoice();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityupdatePaperChoice()
	{
		
		boolean expectedResult = true;
		boolean result=obj.updatePaperChoice();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitydeletePaperChoice()
	{
		
		boolean expectedResult = true;
		boolean result=obj.deletePaperChoice();
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
