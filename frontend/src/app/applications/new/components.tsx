import React from "react";
import { campaignData } from "../tempData";


export function ImageBox({ campaignSample }: { campaignSample: campaignData }) {
  return (
    <div className="w-full h-auto overflow-hidden">
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

export function CampaignInfo({
  campaignSample,
}: {
  campaignSample: campaignData;
}) {
  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold text-gray-800 mb-2">
        {campaignSample.campaignName}
      </h2>
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

