import Image from "next/image";
import { ICampaign } from "./interfaces";
import styles from "./page.module.css";


interface CampaignCardProps {
    campaign: ICampaign;
}

export default function CampaignCardWithOverlay({ campaign }: CampaignCardProps) {
    const isClosed = campaign.status === "모집 마감"; // ✅ 모집 마감 여부 체크

    return (
        <div className={`${styles.card} ${isClosed ? styles.closed : ""}`}>
            {/* 이미지 섹션 */}
            <div className="relative w-full h-40">
                <Image
                    src={campaign.imageSrc}
                    alt={campaign.campaignName}
                    width={300}
                    height={160}
                    className="w-full h-full object-cover rounded-t-lg"
                />
                {isClosed && <div className={styles.overlay}>모집 마감</div>} {/* ✅ 모집 마감일 때 오버레이 */}
            </div>

            {/* 캠페인 정보 */}
            <div className="p-4 border-t">
                <h2 className="text-lg font-semibold">{campaign.campaignName}</h2>
                <p className="text-gray-500 text-sm">{campaign.description}</p>
                <p className="text-blue-500 font-semibold">{campaign.status}</p>
            </div>
        </div>
    );
}
