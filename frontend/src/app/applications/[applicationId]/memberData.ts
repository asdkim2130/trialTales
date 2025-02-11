export interface Member {
  username: string;
  nickname: string;
}

export interface Campaign {
  imageSrc: string;
  campaignName: string;
  description: string;
  startDate: string;
  endDate: String;
  status: string;
  recruitmentLimit: number;
}

export interface ApplicationSample {
  id: number;
  member: Member;
  campaign: Campaign;
  email: string;
  snsUrl: string;
  applicationDate: string;
  status: string;
}

// member
export const memberSample = {
  id: 1,
  username: "doraemon1",
  nickname: "도라에몽1",
};

// campaign
export const campaignSample = {
  id: 1,
  imageSrc:
    "https://www.seoulouba.co.kr/data/campaign_list/330867/thumb-1da2995982f2833c3f984ac85e2ca4bb_348211_260x260.jpg",
  campaignName: "[고양] 바로스튜디오 ",
  description: "고양 화정 증명/여권 & 프로필+증명사진 패키지 촬영권 제공",
  startDate: "2025-02-07",
  endDate: "2025-02-14",
  status: "모집 중",
  recruitmentLimit: 100,
};

export const applicationSample = {
  id: 1,
  member: {
    id: 1,
    username: "doraemon1",
    nickname: "도라에몽1",
  },
  campaign: {
    id: 1,
    imageSrc:
      "https://www.seoulouba.co.kr/data/campaign_list/330867/thumb-1da2995982f2833c3f984ac85e2ca4bb_348211_260x260.jpg",
    campaignName: "[고양] 바로스튜디오 ",
    description: "고양 화정 증명/여권 & 프로필+증명사진 패키지 촬영권 제공",
    startDate: "2025-02-07",
    endDate: "2025-02-14",
    status: "모집 중",
    recruitmentLimit: 100,
  },
  email: "email@example.com",
  snsUrl: "https://example.com",
  applicationDate: "2025-02-07",
  status: "PENDING",
};

export const memData = [
  { id: 1, username: "doraemon1", nickname: "도라에몽1" },
  { id: 2, username: "doraemon2", nickname: "도라에몽2" },
  { id: 3, username: "doraemon3", nickname: "도라에몽3" },
  { id: 4, username: "doraemon4", nickname: "도라에몽4" },
  { id: 5, username: "doraemon5", nickname: "도라에몽5" },
  { id: 6, username: "doraemon6", nickname: "도라에몽6" },
  { id: 7, username: "doraemon7", nickname: "도라에몽7" },
  { id: 8, username: "doraemon8", nickname: "도라에몽8" },
];
