
import Link from "next/link";
import React from "react";
import {campaignData} from "@/app/applications/tempData";
import {detailApp} from "@/app/applications/admin/viewInfo/memberData";

export interface CampaignProps {
  campaignSample: campaignData;
}

export interface ApplicationProps {
 detailAppSample: detailApp;
}

// export function ImageBox({ campaignSample }: CampaignProps) {
//   return (
//     <div className="w-full h-auto overflow-hidden">
//       <img
//         src={campaignSample.imageSrc}
//         alt={campaignSample.campaignName}
//         width={400} // 가로 크기 400px로 설정
//         height={400} // 비례에 맞는 높이 설정
//         className="w-[400px] h-auto object-cover rounded-t-lg mx-auto"
//       />
//     </div>
//   );
// }
//
// export function CampaignInfo({ campaignSample }: CampaignProps) {
//   return (
//     <div className="p-6">
//       <h2 className="text-2xl font-semibold text-gray-800 mb-2">
//         {campaignSample.campaignName}
//       </h2>
//       <p className="text-gray-600 mb-2">
//         {campaignSample.description}
//       </p>
//       <div className="text-sm text-gray-500 mb-2">
//         <p>시작일: {campaignSample.startDate}</p>
//         <p>종료일: {campaignSample.endDate}</p>
//         <p>모집인원: {campaignSample.recruitmentLimit}</p>
//       </div>
//       <p className="text-blue-500 font-semibold">
//         {campaignSample.status}
//       </p>
//     </div>
//   );
// }

export function ApplicationInfo({ detailAppSample }: ApplicationProps) {
  return (
    <div className="w-full p-6">
      <table className="w-full table-auto border-separate border-spacing-y-2">
        <tbody>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              사용자ID
            </td>
            <td className="border-b px-4 py-2">
              {detailAppSample.member.username}
            </td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              닉네임
            </td>
            <td className="border-b px-4 py-2">
              {detailAppSample.member.nickname}
            </td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              Email
            </td>
            <td className="border-b px-4 py-2">{detailAppSample.email}</td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              SNS 계정
            </td>
            <td className="border-b px-4 py-2">{detailAppSample.snsUrl}</td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              신청날짜
            </td>
            <td className="border-b px-4 py-2">
              {detailAppSample.applicationDate}
            </td>
          </tr>
          <tr>
            <td className="border-b px-4 py-2 font-semibold w-1/3 text-center">
              승인상태
            </td>
            <td className="border-b px-4 py-2">{detailAppSample.status}</td>
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
