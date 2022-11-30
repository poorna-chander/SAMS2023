import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class AssignedPaperTest {
	AssignedPapersUtil obj=new AssignedPapersUtil();
	@Test
	@DisplayName("Assert Equal")
	public void equalityGetAssignedPaperid()
	{
		
		int expectedResult = 5;
		int result=obj.getNextAssignedPaperId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityGetNextPaperid()
	{
		
		boolean expectedResult = False;
		boolean result=obj.insertAssignedPaper();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityGetUpdatePaper()
	{
		
		boolean expectedResult = True;
		boolean result=obj.updateAssignedPaper();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityGetDeletePaper()
	{
		
		boolean expectedResult = true;
		boolean result=obj.deleteAssignedPaper();
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