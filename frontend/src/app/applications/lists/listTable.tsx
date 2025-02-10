import { appList } from "./listData";
import React, { useState } from "react";

export function ListTable({ list }: { list: appList }) {
  return (
    <div>
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
}
