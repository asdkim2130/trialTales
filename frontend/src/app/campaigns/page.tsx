"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Image from "next/image";  // ✅ Next.js Image 컴포넌트 추가
import { ICampaign } from "./interfaces";
import { mockCampaigns } from "./campaignFunctions";
import styles from "./page.module.css";

export default function CampaignList() {
    const [campaigns] = useState<ICampaign[]>(mockCampaigns);
    const router = useRouter();

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>지금 가장 핫한 캠페인 !</h1>
            <div className={styles.grid}>
                {campaigns.map((campaign) => (
                    <div
                        key={campaign.id}
                        className={styles.card}
                        onClick={() => router.push(`/campaigns/${campaign.id}`)}
                    >
                        {/* ✅ 이미지 추가 */}
                        <div className={styles.imageWrapper}>
                            <Image
                                src={campaign.imageSrc}
                                alt={campaign.campaignName}
                                width={260}
                                height={180}
                                className={styles.image}
                            />
                        </div>

                        {/* ✅ 기존 캠페인 정보 */}
                        <h2 className={styles.cardTitle}>{campaign.campaignName}</h2>
                        <p className={styles.cardDesc}>{campaign.description}</p>
                        <p className={styles.cardStatus}>{campaign.status}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}
