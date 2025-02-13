
import Link from "next/link";
import React, {useState} from "react";
import {campaignData} from "@/app/applications/tempData";
import {detailApp} from "@/app/applications/admin/viewInfo/memberData";

export interface CampaignProps {
  campaignSample: campaignData;
}

export interface ApplicationProps {
 detailAppSample: detailApp;
}

export function ApplicationInfo({ detailAppSample }: ApplicationProps) {

  const [appSample, setAppSample] = useState(detailAppSample);

  const handleApprove = () => {

    const updatedAppSample = { ...appSample, status: "APPROVED" };
    setAppSample(updatedAppSample);


  };


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
        <Link href="/applications/admin">
          <button
            type="submit"
            className={`w-full mt-4 py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 
        ${detailAppSample.status === "APPROVED"
                ? "bg-gray-400 cursor-not-allowed" 
                : "bg-blue-500 hover:bg-blue-600 text-white" 
            }`}
            disabled={detailAppSample.status === "APPROVED"}
            onClick={handleApprove}
          >
            Approve
          </button>
        </Link>
      </div>
    </div>
  );
}
