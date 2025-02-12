import { ApplicationSample } from "./memberData";
import Link from "next/link";
import React from "react"; // memberData.ts에서 ApplicationSample import

interface ImageBoxProps {
  applicationSample: ApplicationSample;
}

export function ImageBox({ applicationSample }: ImageBoxProps) {
  return (
    <div className="w-full h-56 overflow-hidden">
      <img
        src={applicationSample.campaign.imageSrc}
        alt={applicationSample.campaign.campaignName}
        width={400} // 가로 크기 400px로 설정
        height={400} // 비례에 맞는 높이 설정
        className="w-[400px] h-auto object-cover rounded-t-lg mx-auto"
      />
    </div>
  );
}

export function CampaignInfo({ applicationSample }: ImageBoxProps) {
  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold text-gray-800 mb-2">
        {applicationSample.campaign.campaignName}
      </h2>
      <p className="text-gray-600 mb-2">
        {applicationSample.campaign.description}
      </p>
      <div className="text-sm text-gray-500 mb-2">
        <p>시작일: {applicationSample.campaign.startDate}</p>
        <p>종료일: {applicationSample.campaign.endDate}</p>
        <p>모집인원: {applicationSample.campaign.recruitmentLimit}</p>
      </div>
      <p className="text-blue-500 font-semibold">
        {applicationSample.campaign.status}
      </p>
    </div>
  );
}

export function ApplicationInfo({ applicationSample }: ImageBoxProps) {
  return (
    <div className="w-full p-6">
      <table className="w-full table-auto border-separate border-spacing-y-2">
        <tbody>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              사용자ID
            </td>
            <td className="border-b px-4 py-2">
              {applicationSample.member.username}
            </td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              닉네임
            </td>
            <td className="border-b px-4 py-2">
              {applicationSample.member.nickname}
            </td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              Email
            </td>
            <td className="border-b px-4 py-2">{applicationSample.email}</td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              SNS 계정
            </td>
            <td className="border-b px-4 py-2">{applicationSample.snsUrl}</td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              신청날짜
            </td>
            <td className="border-b px-4 py-2">
              {applicationSample.applicationDate}
            </td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              승인상태
            </td>
            <td className="border-b px-4 py-2">{applicationSample.status}</td>
          </tr>
        </tbody>
      </table>
      <div>
        <Link href="/campaigns">
          <button
            type="submit"
            className="w-full mt-4 bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Approve
          </button>
        </Link>
      </div>
    </div>
  );
}
