import React from "react";
import { campaignSample, campaignData } from "./tempData";
import { router } from "next/client";
import Link from "next/link";

export function ImageBox({ campaignId }: { campaignId: number }) {
  const campaign = campaignSample.find((c) => c.id === campaignId);

  // 데이터가 없을 경우 처리
  if (!campaign) {
    return (
      <div className="w-full h-56 flex items-center justify-center">
        Campaign not found
      </div>
    );
  }

  return (
    <div className="w-full h-56 overflow-hidden">
      <img
        src={campaign.imageSrc}
        alt={campaign.campaignName}
        width={400} // 가로 크기 400px로 설정
        height={400} // 비례에 맞는 높이 설정
        className="w-[400px] h-auto object-cover rounded-t-lg mx-auto"
      />
    </div>
  );
}

export function CampaignInfo({ campaign }: { campaign: campaignData }) {
  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold text-gray-800 mb-2">
        {campaign.campaignName}
      </h2>
      <p className="text-gray-600 mb-2">{campaign.description}</p>
      <div className="text-sm text-gray-500 mb-2">
        <p>시작일: {campaign.startDate}</p>
        <p>종료일: {campaign.endDate}</p>
        <p>모집인원: {campaign.recruitmentLimit}</p>
      </div>
      <p className="text-blue-500 font-semibold">{campaign.status}</p>
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
      <h2 className="text-2xl font-semibold text-gray-800 mb-6 text-center">
        체험단 신청하기
      </h2>
      <form action={submit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">
            E-mail
          </label>
          <input
            type="email"
            name="email"
            required
            placeholder="이메일"
            className="mt-1 p-2 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            SNS 계정
          </label>
          <input
            type="text"
            name="snsUrl"
            placeholder="SNS 계정 링크"
            className="mt-1 p-2 border border-gray-300 rounded-md w-full focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div>
          <Link href="/campaigns">
            <button
              type="submit"
              className="w-full mt-4 bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              제출
            </button>
          </Link>
        </div>
      </form>
    </div>
  );
}
