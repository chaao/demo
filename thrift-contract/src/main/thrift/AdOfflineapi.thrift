namespace cpp inveno
namespace java com.evan.thrift.offlineapi

const string MACROS_CHANNEL = "{channel}"
const string MACROS_USER_TOKEN = "{user_token}"
const string MACROS_ANDROID_ID = "{android_id}"
const string MACROS_IMEI = "{imei}"
const string MACROS_GOOGLE_AD_ID = "{gaid}"
const string MACROS_IDFA= "{idfa}"


enum Platform {
	ANDROID = 1,
	IOS = 2,
}

enum PayoutType {
	CPM = 1,
	CPC = 2,
	CPI = 3,
	CPA = 4,
}


struct Creative {
	1:  optional string url,
	2:  optional string type,
	3:  optional i32 width,
	4:  optional i32 height,
}

struct AppDetails{
	1:  optional string name,
	2:  optional string description,
	3:  optional string icon,
	4:  optional Platform platform,
	5:  optional string preview,
}

struct Data {
	1:  optional string adid,
	2:  optional double payout,
	3:  optional string payout_currency,
	4:  optional PayoutType payout_type,
	5:  optional string trackinglink,
	6:  optional i32 daily_max = -1,
	7:  optional i32 daily_remaining = -1,
	8:  optional list<string> countries,
	9:  optional AppDetails app_details,
	10: optional list<Creative> creative,
	11: optional list<string> impressiontrackers,
	12: optional list<string> clicktrackers,
}

struct OfflineapiRequest {
	1: optional i32 channel,
	2: optional i32 placement,
	3: optional i32 skip,
	4: optional i32 limit,
}

struct OfflineapiResponse {
	1: optional i32 status,
	2: optional string message,
	3: optional list<Data> data,
}

//  offlineapi服务
service AdOfflineapi {
	OfflineapiResponse request(1: OfflineapiRequest request),
	i32 reportImpression(1: i32 channel, 2: i32 placement, 3: string adid),
	i32 reportClick(1: i32 channel, 2: i32 placement, 3: string adid),
	i32 postback(1: map<string,string> param),
}

