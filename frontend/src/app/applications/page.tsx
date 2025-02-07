
interface campaign{
    id: number;
    imageSrc: string;
    campaignName: string;
    description: string;
    startDate: string;
    endDate: string;
    status: string;
    recruitmentLimit: number;
}

const campaignSample: campaign = {
    id: 1,
    imageSrc: "https://www.seoulouba.co.kr/data/campaign_list/330867/thumb-1da2995982f2833c3f984ac85e2ca4bb_348211_260x260.jpg",
    campaignName: "[고양] 바로스튜디오 ",
    description: "고양 화정 증명/여권 & 프로필+증명사진 패키지 촬영권 제공",
    startDate: "2025-02-07",
    endDate: "2025-02-14",
    status: "모집 중",
    recruitmentLimit: 100,
}


export default function ApplicationPage() {
    return (
        <div className="bg-white rounded-lg shadow-lg overflow-hidden max-w-[1000px] mx-auto flex min-h-screen items-center">
            <div className="w-1/2 p-6 flex flex-col">
                <ImageBox />
                <CampaignInfo />
            </div>
            <div className="w-1/2 p-6 border-l">
                <ApplicationRequest />
            </div>
        </div>
    );
}

export function ImageBox() {
    return (
        <div className="w-full h-56 overflow-hidden">
            <img
                src={campaignSample.imageSrc}
                alt={campaignSample.campaignName}
                width={400} // 가로 크기 400px로 설정
                height={400} // 비례에 맞는 높이 설정
                className="w-[400px] h-auto object-cover rounded-t-lg mx-auto"
            />
        </div>
    );
}

export function CampaignInfo() {
    return (
        <div className="p-6">
            <h2 className="text-2xl font-semibold text-gray-800 mb-2">{campaignSample.campaignName}</h2>
            <p className="text-gray-600 mb-2">{campaignSample.description}</p>
            <div className="text-sm text-gray-500 mb-2">
                <p>시작일: {campaignSample.startDate}</p>
                <p>종료일: {campaignSample.endDate}</p>
                <p>모집인원: {campaignSample.recruitmentLimit}</p>

            </div>
            <p className="text-blue-500 font-semibold">{campaignSample.status}</p>
        </div>
    );
}

export function ApplicationRequest() {
    async function submit(formData: FormData) {
        "use server";

        const rawFormData = {
            email: formData.get("email"),
            snsUrl: formData.get("snsUrl"),
        };
    }

    return (
        <div className="p-6">
            <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">체험단 신청하기</h2>
            <form action={submit} className="space-y-4">
                <div>
                    <label className="block text-sm font-medium text-gray-700">E-mail</label>
                    <input
                        type="email"
                        name="email"
                        required
                        placeholder="이메일"
                        className="mt-1 p-2 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>
                <div>
                    <label className="block text-sm font-medium text-gray-700">SNS 계정</label>
                    <input
                        type="text"
                        name="snsUrl"
                        placeholder="SNS 계정 링크"
                        className="mt-1 p-2 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>
                <div>
                    <button
                        type="submit"
                        className="w-full mt-4 bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        제출
                    </button>
                </div>
            </form>
        </div>
    );
}
