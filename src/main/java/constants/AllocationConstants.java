package constants;

public class AllocationConstants {

	public static final String ENV_AD = "-ad";
	//base url
	public static final String ALLOCATION_URL = "http://allocationteamdata"+ENV_AD+".apps-np.homedepot.com/allocation/";
	//create
	public static final String ALLOCATION_CREATE_URL = ALLOCATION_URL+"createAllocation";

	//tables
	public static final String TRANSLOAD_ALLOCATION_HEADER = "TLD_ALLOC_HDR";
	public static final String TRANSLOAD_ALLOCATION_DETAIL = "TLD_ALLOC_DTL";

//DB Connection
	public static final String TRANSLOAD_DB = "jdbc:oracle:thin:@snpagor61-scan.homedepot.com:1521:dad19sss1";

}
