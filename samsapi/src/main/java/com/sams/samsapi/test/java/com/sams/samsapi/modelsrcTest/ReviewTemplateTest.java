import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ReviewTemplateTest {
	ReviewTemplate obj=new ReviewTemplate();

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
	public void equalitygetId()
	{
		
		int expectedResult = 5;
		int result=obj.getId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetPaperId()
	{
		
		int expectedResult = 5;
		int result=obj.getPaperId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetPcmId()
	{
		
		int expectedResult = 5;
		int result=obj.getPcmId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetRating()
	{
		
		int expectedResult = 5;
		int result=obj.getRating();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetReviewId()
	{
		
		String expectedResult = "Any Sentence";
		String result=obj.getReviewId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetValue()
	{
		
		String expectedResult = "Any Sentence";
		String result=obj.getValue();
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
