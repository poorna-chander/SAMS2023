import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class PaperPoolTest {
	PaperPool obj=new PaperPool();
	ResearchPaper obj2=new ResearchPaper();
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetFinalRatingByPaperID()
	{
		
		int expectedResult = 5;
		int result=obj.getFinalRatingByPaperID(5);
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityupdatePaper()
	{
		
		boolean expectedResult = False;
		boolean result=obj.updatePaper(obj2);
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
