"use client";
import { campaignData } from "../tempData";
import { appList } from "./listData";

import { useState } from "react";

const ProductTable: React.FC = () => {
  const [filter, setFilter] = useState<"All" | "PENDING" | "APPROVED">("All");

  // 필터링된 데이터
  const filteredProducts =
    filter === "All"
      ? appList
      : appList.filter((list) => list.appStatus.toUpperCase() === filter);

  return (
    <div className="p-6">
      {/* 필터 메뉴 */}
      <div className="mb-4">
        <button
          onClick={() => setFilter("All")}
          className={`px-4 py-2 mr-2 ${filter === "All" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
        >
          All
        </button>
        <button
          onClick={() => setFilter("PENDING")}
          className={`px-4 py-2 mr-2 ${filter === "PENDING" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
        >
          PENDING
        </button>
        <button
          onClick={() => setFilter("APPROVED")}
          className={`px-4 py-2 mr-2 ${filter === "APPROVED" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
        >
          APPROVED
        </button>
      </div>

      {/* 테이블 */}
      <table className="w-full table-auto border-separate border-spacing-0">
        <thead>
          <tr className="text-left">
            <th className="p-3">Image</th>
            <th className="p-3">campaignName</th>
            <th className="p-3">Status</th>
            <th className="p-3">recruitmentLimit</th>
            <th className="p-3">Created</th>
            <th className="p-3">ApplicationDate</th>
            <th className="p-3">AppStatus</th>
            <th className="p-3">Actions</th>
          </tr>
        </thead>
        <tbody>
          {appList.map((app) => (
            <tr key={app.id} className="border-b">
              <td className="p-3">
                <img
                  src={app.campaign?.imageSrc}
                  alt={app.campaign?.campaignName}
                  className="w-16 h-16 object-cover"
                />
              </td>
              <td className="p-3">{app.campaign?.campaignName}</td>
              <td className="p-3">${app.campaign?.status}</td>
              <td className="p-3">{app.campaign?.recruitmentLimit}</td>
              <td className="p-3">{app.campaign?.startDate}</td>
              <td className="p-3">{app.applicationDate}</td>
              <td className="p-3">{app.appStatus}</td>
              <td className="p-3">
                <button className="text-blue-500 hover:text-blue-700">
                  Toggle menu
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProductTable;
