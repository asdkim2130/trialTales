// import React from "react";
//
// import { campaignSample } from "./tempData";
// import {CampaignInfo, ImageBox} from "@/app/applications/new/components";
// import {ApplicationRequest} from "@/app/applications/new/requestComponent";
//
// export default function ApplicationPage() {
//   const campaignId = 1;
//
//   const tempCampaignSample = campaignSample.find(
//     (campaign) => campaign.id === campaignId,
//   );
//
//   if (!tempCampaignSample) {
//     return <div>캠페인을 찾을 수 없습니다.</div>;
//   }
//
//   return (
//     <div className="bg-white rounded-lg shadow-lg overflow-hidden max-w-[1000px] mx-auto flex min-h-screen items-center">
//       <div className="w-1/2 p-6 flex flex-col">
//         <ImageBox campaignSample={tempCampaignSample} />
//         <CampaignInfo campaignSample={tempCampaignSample} />
//       </div>
//       <div className="w-1/2 p-6 border-l">
//         <ApplicationRequest />
//       </div>
//     </div>
//   );
// }
import { redirect } from "next/navigation";

// 자동으로 /campaigns로 이동
export default function ApplicationsPage() {
    redirect("/campaigns");
    return null;
}
