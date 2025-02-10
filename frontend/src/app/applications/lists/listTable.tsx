import { appList } from "./listData";
import React, { useState } from "react";

export function ListTable({ list }: { list: appList }) {
  return (
    <div>
      <table className="w-full table-auto border-separate border-spacing-0 bg-white rounded-lg shadow-lg max-w-[1000px] mx-auto">
        <thead>
          <tr className="text-center">
            <th className="p-3 text-center">Image</th>
            <th className="p-3 text-center">campaignName</th>
            <th className="p-3 text-center">Status</th>
            <th className="p-3 text-center">recruitmentLimit</th>
            <th className="p-3 text-center">Created</th>
            <th className="p-3 text-center">ApplicationDate</th>
            <th className="p-3 text-center">AppStatus</th>
            <th className="p-3 text-center">Actions</th>
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
              <td className="p-3 text-center">{app.campaign?.campaignName}</td>
              <td className="p-3 text-center">{app.campaign?.status}</td>
              <td className="p-3 text-center">{app.campaign?.recruitmentLimit}</td>
              <td className="p-3 text-center">{app.campaign?.startDate}</td>
              <td className="p-3 text-center">{app.applicationDate}</td>
              <td className="p-3 text-center">{app.appStatus}</td>
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
}
