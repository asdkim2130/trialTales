interface tempData{
    id: number;
    imageSrc: string;
    campaignName: string;
    description: string;
    startDate: string;
    endDate: string;
    status: string;
    recruitmentLimit: number;
}

export const campaignSample: tempData = {
    id: 1,
    imageSrc: "https://www.seoulouba.co.kr/data/campaign_list/330867/thumb-1da2995982f2833c3f984ac85e2ca4bb_348211_260x260.jpg",
    campaignName: "[고양] 바로스튜디오 ",
    description: "고양 화정 증명/여권 & 프로필+증명사진 패키지 촬영권 제공",
    startDate: "2025-02-07",
    endDate: "2025-02-14",
    status: "모집 중",
    recruitmentLimit: 100,
}