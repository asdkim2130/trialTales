import Image from "next/image";
import { ICampaign } from "./interfaces";
import styles from "./page.module.css";

export default function CampaignCard({ campaign }: { campaign: ICampaign }) {
    const isClosed = campaign.status === "모집 마감"; // 모집 마감 여부 체크

    return (
        <div className={`${styles.card} ${isClosed ? styles.closed : ""}`}>
            {/* 이미지 박스 */}
            <div className={styles.imageWrapper}>
                <Image
                    src={campaign.imageSrc}
                    alt={campaign.campaignName}
                    width={300}
                    height={200}
                    className={styles.image}
                />
                {isClosed && <div className={styles.overlay}>모집마감</div>} {/* 모집 마감일 때 오버레이 */}
            </div>

            {/* 캠페인 정보 */}
            <div className="p-4 flex flex-col items-center gap-2">
                <h2 className={styles.cardTitle}>{campaign.campaignName}</h2>
                <p className={styles.cardDesc}>{campaign.description}</p>
                <p className={styles.cardStatus}>{campaign.status}</p>

                {/* 버튼 추가 (수정) */}
                <div className={styles.buttonContainer}>
                    <button
                        className={`${styles.button} ${styles.buttonApply}`}
                        onClick={() => alert(`${campaign.campaignName} 신청이 완료되었습니다.`)}
                        disabled={isClosed} // 모집 마감일 경우 비활성화
                    >
                        신청
                    </button>
                    <button
                        className={`${styles.button} ${styles.buttonReview}`}
                        onClick={() => alert(`${campaign.campaignName} 리뷰 보기!`)}
                    >
                        리뷰
                    </button>
                </div>
            </div>
        </div>
    );
}
