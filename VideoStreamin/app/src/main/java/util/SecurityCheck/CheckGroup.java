package util.SecurityCheck;

public class CheckGroup {
	
	public static Boolean checkright(int groupID){
		if(groupID < 5)
			return false;
		else 
			return true;
		
	}
	
	public static String getGroup(int groupID){
		String group ="";
		
		switch (groupID) {
		case 1: return "Basic";
		case 2: return "Premium";
		case 3: return "Gold";
		case 4: return "Discover";
		case 5: return "Admin";
		case 6: return "Enterprise";

		default:
			break;
		}
		
		return group;
	}

}
