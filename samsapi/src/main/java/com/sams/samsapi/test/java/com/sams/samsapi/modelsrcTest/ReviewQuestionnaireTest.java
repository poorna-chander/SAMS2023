import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ReviewQuestionnaireTest {
	ReviewQuestionnaire obj=new ReviewQuestionnaire();
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetId()
	{
		
		int expectedResult = 5;
		int result=obj.getId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetField()
	{
		
		String expectedResult = "Any Sentence";
		String result=obj.getField();
		Assertions.assertEquals(expectedResult, result);
	}
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
