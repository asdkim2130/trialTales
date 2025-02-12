import React from "react";
import {
  ImageBox,
  CampaignInfo,
  ApplicationRequest,
} from "./applicationComponents";
import { campaignSample } from "./tempData";
import { useRouter } from "next/router";

export default function ApplicationPage() {
  const router = useRouter();
  const { campaignId } = router.query;

  if (!campaignId) {
    return <div>로딩중...</div>;
  }

  const tempCampaignSample = campaignSample.find(
    (campaign) => campaign.id === parseInt(campaignId as string, 10),
  );

  if (!tempCampaignSample) {
    return <div>캠페인을 찾을 수 없습니다.</div>;
  }

  return (
    <div className="bg-white rounded-lg shadow-lg overflow-hidden max-w-[1000px] mx-auto flex min-h-screen items-center">
      <div className="w-1/2 p-6 flex flex-col">
        <ImageBox campaignId={tempCampaignSample.id} />
        <CampaignInfo campaign={tempCampaignSample} />
      </div>
      <div className="w-1/2 p-6 border-l">
        <ApplicationRequest />
      </div>
    </div>
  );
}
