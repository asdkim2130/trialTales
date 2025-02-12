import { ImageBox, CampaignInfo, ApplicationInfo } from "./detailComponents";
import React from "react";
import { applicationSample } from "@/app/applications/admin/[applicationId]/memberData";

export default function DetailApplicationPage() {
  return (
    <div className="bg-white rounded-lg shadow-lg overflow-hidden max-w-[1000px] mx-auto flex min-h-screen items-center">
      <div className="w-1/2 p-6 flex flex-col">
        <ImageBox applicationSample={applicationSample} />
        <CampaignInfo applicationSample={applicationSample} />
      </div>
      <div className="w-1/2 p-6 border-l">
        <ApplicationInfo applicationSample={applicationSample} />
      </div>
    </div>
  );
}
