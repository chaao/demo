namespace java com.evan.thrift.admodel

enum AdStatus {
    ACTIVE = 1,
    PAUSED = 2,
    DELETED = 3,
    ARCHIVED = 4;
}

enum AdMediaType {
	IMAGE=1,
    VIDEO=2,
    AUDIO=3,
}

enum AdMediaFormat {
	IMAGE_PNG = 1,
    IMAGE_JPEG = 2,
}

struct AdMedia {
	1:  optional i64 id,                //media ID
	2:  optional i64 accountId,         //ad account id that owns the campaign
	3:  optional string name,           //The filename of this media
	4:  optional i64 size,              //The size of the media.(KB)
	5:  optional AdMediaType type,
	6:  optional AdMediaFormat format,
	7:  optional string url,
	8:  optional i32 width,
	9:  optional i32 height,
	10: optional string picture,        //The thumbnail for the media.
	11: optional i32 length,            //Duration of this video or audio.
	12: optional string hash,           //The hash which uniquely identifies the media.
	13: optional AdStatus status,       //enum {ACTIVE, DELETED}
}


struct AdCreative {
	1:  optional i64 id,
	2:  optional i64 accountId,         //ad account id that owns the campaign
	3:  optional i64 campaignId,
	4:  optional i64 setId,             //ad_set id
	5:  optional string title,
	6:  optional string description,
	7:  optional AdMedia logo,
	8:  optional AdMedia image,
	9:  optional AdMedia video,
	10: optional string cta,            //call to action
	11: optional i32 placement,         //adSpaceId
	12: optional i32 userDailyImpCap,   //limit for daily frequency
	13: optional i32 userDailyClickCap, //limit for daily frequency
	14: optional AdStatus status,       //enum {ACTIVE, PAUSED, DELETED, ARCHIVED}
}


enum Gender {
	ALL = 1,
    MALE = 2,
    FEMALE = 3,
    NON_MALE = 4,       // female and unknown
    NON_FEMALE = 5,     // male and unknown
}

enum BillingEvent {
	CPM = 1,
	CPC = 2,
	CPI = 3,
	CPA = 4,
}

enum PacingType {
	UNIFORM = 1,                        //Show your ads throughout the lifetime
	ACCELERATED = 2,                    //Show your ads as quickly as possible
}
struct AdSet {
	1:  optional i64 id,                //ad set id
	2:  optional i64 accountId,         //ad account id that owns the ad set.
	3:  optional i64 campaignId,
	4:  optional string name,           //The name of this ad set.
	5:  optional string country,
	6:  optional string state,
	7:  optional string city,
	8:  optional i32 ageMin,
	9:  optional i32 ageMax,
	10: optional Gender gender,
	11: optional i32 language,
	12: optional list<i64> target,
	13: optional double totalBudget,
	14: optional double dailyBudget,
	15: optional double bidAmount,      //Bid amount for this ad set
	16: optional BillingEvent billingEvent,//The billing event that this adset is using
	17: optional PacingType pacingType, //delivery schedule used for this ad set.
	18: optional i32 userDailyImpCap,   //limit for daily frequency
    19: optional i32 userDailyClickCap, //limit for daily frequency
	20: optional AdStatus status,       //enum {ACTIVE, PAUSED, DELETED, ARCHIVED}
}

enum AdObjectType {
	ANDROID_APP = 1,
	IOS_APP = 2,
	WEBPAGE = 3,
}

struct AdObject {
	1:  optional i64 id,                    //object id
	2:  optional i64 accountId,             //ad account id that owns the ad set.
	3:  optional i64 campaignId,
	4:  optional AdObjectType type,         //The type of object that is being advertised.
	5:  optional string name,               //app/site name
	6:  optional string preview,            //google play/app store/web url
	7:  optional string trackinglink,       //original landing page
	8:  optional string templatelink,       //formatted landing page
	9:  optional list<string> imptrackers,  //Array of impression tracking URLs.
	10: optional list<string> clicktrackers,//Array of click tracking URLs.
	11: optional i32 category,              //iab content categories
	12: optional string domain,             //Domain of the site or app.
	13: optional string bundle,             //On Android, this should be a bundle or package name. On iOS, it is a numeric ID.
}


enum BudgetDistribution {
	AVERAGE = 1,    //Give each ad set the same amount of budget.
	BALANCE = 2,    //Use the audience size of an ad set to determine how much budget it receives. More of your budget will go to ad sets with larger audiences.
}

struct AdCampaign {
	1:  optional i64 id,                //Campaign's id
	2:  optional i64 accountId,         //ad account id that owns the campaign
	3:  optional string name,           //The name of this campaign
	4:  optional double totalBudget,
	5:  optional double dailyBudget,
	6:  optional BudgetDistribution budgetDistribution,
	7:  optional i64 startTime,
	8:  optional i64 stopTime,
	9:  optional AdStatus status,        //enum {ACTIVE, PAUSED, DELETED, ARCHIVED}

}


struct AdAccount {
	1:  optional i64 id,
	2:  optional i64 accountId,
	3:  optional string name,
	4:  optional string remark,
}