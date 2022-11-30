import org.junit.jupiter.api.Assertions;
public class UsersOpsTest {
	UsersOps obj=new UsersOps();
	public void equalitygetSubmitterName()
	{
		
		boolean expectedResult = true;
		boolean result=obj.getSubmitterName(3);
		Assertions.assertEquals(expectedResult, result);
	}
	
}
