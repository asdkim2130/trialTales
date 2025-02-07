import Image from "next/image";
import { ICampaign } from "./interfaces";

export default function CampaignCard({ campaign }: { campaign: ICampaign }) {
    return (
        <div className="bg-white rounded-lg shadow-md overflow-hidden">
            {/* 🔹 이미지 박스 추가 */}
            <div className="w-full h-40">
                <Image
                    src={campaign.imageSrc}
                    alt={campaign.campaignName}
                    width={300}
                    height={160}
                    className="w-full h-full object-cover rounded-t-lg"
                />
            </div>

            {/* 🔹 기존 캠페인 정보 */}
            <div className="p-4 border-t">
                <h2 className="text-lg font-semibold">{campaign.campaignName}</h2>
                <p className="text-gray-500 text-sm">{campaign.description}</p>
                <p className="text-blue-500 font-semibold">{campaign.status}</p>
            </div>
        </div>
    );
}
