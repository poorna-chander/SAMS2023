import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class UserUtilsTest {
	UserUtils obj=new UserUtils();
	@Test
	@DisplayName("Assert Equal")
	public void equalitygetNextUserId()
	{
		
		int expectedResult = 5;
		int result=obj.getNextUserId();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityisSaveUserSuccessful()
	{
		
		boolean expectedResult = False;
		boolean result=obj.isSaveUserSuccessful();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityinsertUserData()
	{
		
		boolean expectedResult = True;
		boolean result=obj.insertUserData();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalityupdateUserData()
	{
		
		boolean expectedResult = true;
		boolean result=obj.updateUserData();
		Assertions.assertEquals(expectedResult, result);
	}
	@Test
	@DisplayName("Assert Equal")
	public void equalitydeleteUserData()
	{
		
		boolean expectedResult = true;
		boolean result=obj.deleteUserData();
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
