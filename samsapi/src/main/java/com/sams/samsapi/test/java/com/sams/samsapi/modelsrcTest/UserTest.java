import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class UserTest {
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetName()
	{
		
		int expectedResult = 5;
		int result=obj.getName();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetId()
	{
		
		String expectedResult = "Any Sentence";
		String result=obj.getId();
		Assertions.assertEquals(expectedResult, result);
	}
}
