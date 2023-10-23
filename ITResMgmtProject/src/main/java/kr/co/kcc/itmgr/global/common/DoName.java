package kr.co.kcc.itmgr.global.common;

public enum DoName {
    서울("서울,서울특별시", 37.566535, 126.977969),
    인천("인천,인천광역시", 37.4562557, 126.7052062),
    경기도("경기도,경기", 37.567167, 127.190292),
    강원도("강원도,강원", 37.555837, 128.209315),
    충청남도("충청남도,충남", 36.557229, 126.779757),
    충청북도("충청북도,충북", 36.628503, 127.929344),
    경상북도("경상북도,경북", 36.248647, 128.664734),
    경상남도("경상남도,경남", 35.259787, 128.664734),
    전라북도("전라북도,전북", 35.716705, 127.144185),
    전라남도("전라남도,전남", 34.819400, 126.893113),
    제주도("제주도,제주", 33.364805, 126.542671),
	부산("부산,부산광역시", 35.1795543, 129.0756416),
	대구("대구,대구광역시", 35.8714354, 128.601445);

    private final String doName;
    private final double doLatitude;
    private final double doLongitude;

    DoName(String doName, double doLatitude, double doLongitude) {
        this.doName = doName;
        this.doLatitude= doLatitude;
        this.doLongitude = doLongitude;
    }

    public String getDoName() {
        return doName;
    }

    public double getDoLatitude() {
        return doLatitude;
    }

    public double getDoLongitude() {
        return doLongitude;
    }
}
