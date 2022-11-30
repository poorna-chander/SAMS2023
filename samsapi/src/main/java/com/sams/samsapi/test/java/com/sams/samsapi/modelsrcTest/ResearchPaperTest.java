import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ResearchPaperTest {
	ResearchPaper obj=new ResearchPaper();
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
	public void equalitygetSubmitterId()
	{
		
		int expectedResult = 5;
		int result=obj.getSubmitterId();
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
	public void equalitygetRevisionNo()
	{
		
		int expectedResult = 5;
		int result=obj.getRevisionNo();
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
	public void equalitygetTitle()
	{
		
		String expectedResult = "Any Sentence";
		String result=obj.getTitle();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetContact()
	{
		
		String expectedResult = "Any Sentence";
		String result=obj.getContact();
		Assertions.assertEquals(expectedResult, result);
	}@Test
	@DisplayName("Assert Equal")
	public void equalityFileName()
	{
		
		String expectedResult = "Any Sentence";
		String result=obj.FileName();
		Assertions.assertEquals(expectedResult, result);
	}@Test
	@DisplayName("Assert Equal")
	public void equalityFileExtension()
	{
		
		String expectedResult = "Any Sentence";
		String result=obj.FileExtension();
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
