"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Image from "next/image";
import Navbar from "./Navbar"; // ✅ 네비게이션 추가
import { ICampaign } from "./interfaces";
import { mockCampaigns } from "./campaignFunctions";
import styles from "./page.module.css";

export default function CampaignList() {
  const [campaigns] = useState<ICampaign[]>(mockCampaigns);
  const [searchTerm, setSearchTerm] = useState(""); // 🔍 검색어 상태 추가
  const router = useRouter();

  return (
    <div className={styles.pageLayout}>
      <div
        className={styles.verticalBanner}
        onClick={() => router.push("/campaigns")}
      >
        <p className={styles.bannerText}>
          🔥 인기 캠페인 <br />
          모아보기
        </p>
        <button className={styles.bannerButton}>바로가기</button>
      </div>

      <div className={styles.mainContent}>
        <Navbar />

        <div
          className={styles.banner}
          onClick={() => router.push("/campaigns")}
        >
          <p className={styles.bannerText}>🎉 신규 회원 10% 할인 쿠폰 지급!</p>
          <button className={styles.bannerButton}>쿠폰 받기</button>
        </div>

        <div className={styles.container}>
          <h1 className="text-3xl font-extrabold text-blue-500 text-center drop-shadow-md">
            지금 가장 핫한 캠페인!
          </h1>

          {/* 🔍 검색 입력창 */}
          <div className={styles.searchContainer}>
            <input
              type="text"
              placeholder="캠페인 검색..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className={styles.searchInput}
            />
          </div>

          {/* 🔍 검색 결과 유지하면서 필터링 */}
          <div className={styles.grid}>
            {campaigns
              .filter(
                (campaign) =>
                  campaign.campaignName
                    .toLowerCase()
                    .includes(searchTerm.toLowerCase()) ||
                  campaign.description
                    .toLowerCase()
                    .includes(searchTerm.toLowerCase()),
              )
              .map((campaign) => {
                const isClosed = campaign.status === "모집 마감";

                return (
                  <div
                    key={campaign.id}
                    className={`${styles.card} ${isClosed ? styles.closed : ""}`}
                  >
                    <div className={styles.imageWrapper}>
                      <Image
                        src={campaign.imageSrc}
                        alt={campaign.campaignName}
                        width={260}
                        height={180}
                        className={styles.image}
                      />
                      {isClosed && (
                        <div className={styles.overlay}>모집마감</div>
                      )}
                    </div>
                    <h2 className={styles.cardTitle}>
                      {campaign.campaignName}
                    </h2>
                    <p className={styles.cardDesc}>{campaign.description}</p>
                    <p className={styles.cardStatus}>{campaign.status}</p>

                    <div className={styles.buttonContainer}>
                      {/* 신청 버튼 클릭 시 캠페인 ID 포함된 경로로 이동 */}
                      <button
                        className={`${styles.button} ${styles.buttonApply}`}
                        onClick={() =>
                          router.push(`/applications?campaignId=${campaign.id}`)
                        }
                        disabled={isClosed}
                      >
                        신청
                      </button>

                      {/* 리뷰 버튼 클릭 시 캠페인 ID 포함된 경로로 이동 */}
                      <button
                        className={`${styles.button} ${styles.buttonReview}`}
                        onClick={() => router.push(`/reviews/${campaign.id}`)}
                      >
                        리뷰
                      </button>
                    </div>
                  </div>
                );
              })}
          </div>
        </div>
      </div>
    </div>
  );
}
