import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ReviewQuestionnaireUtilTest {
	ReviewQuestionnaireUtil obj=new ReviewQuestionnaireUtil();
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetNextReviewId()
	{
		
		int expectedResult = 5;
		int result=obj.getNextReviewId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityisSaveReviewQuestionnaireSuccessful()
	{
		
		boolean expectedResult = False;
		boolean result=obj.isSaveReviewQuestionnaireSuccessful();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityinsertReviewQuestionnaire()
	{
		
		boolean expectedResult = True;
		boolean result=obj.insertReviewQuestionnaire();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityupdateReviewQuestionnaire()
	{
		
		boolean expectedResult = true;
		boolean result=obj.updateReviewQuestionnaire();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitydeleteReviewQuestionnaire()
	{
		
		boolean expectedResult = true;
		boolean result=obj.deleteReviewQuestionnaire();
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
