import {ApplicationInfo } from "./detailComponents";
import React from "react";
import ClientPage from "@/app/applications/admin/viewInfo/campaignComponet";
import InfoForm from "@/app/applications/admin/viewInfo/aplicationInfoComponent";




export default function DetailApplicationPage() {
  return (
    <div className="bg-white rounded-lg shadow-lg overflow-hidden max-w-[1000px] mx-auto flex min-h-screen items-center">
      <div className="w-1/2 p-6 flex flex-col">
        <ClientPage />
      </div>
      <div className="w-1/2 p-6 border-l">
        <InfoForm />
      </div>
    </div>
  );
}
