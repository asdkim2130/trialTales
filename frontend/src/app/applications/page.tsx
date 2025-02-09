import React from "react";
import { ImageBox, CampaignInfo, ApplicationRequest } from "./applicationComponents";
import { campaignSample } from "./tempData";


export default function ApplicationPage() {
    return (
        <div className="bg-white rounded-lg shadow-lg overflow-hidden max-w-[1000px] mx-auto flex min-h-screen items-center">
            <div className="w-1/2 p-6 flex flex-col">
                <ImageBox campaignSample={campaignSample} />
                <CampaignInfo campaignSample={campaignSample} />
            </div>
            <div className="w-1/2 p-6 border-l">
                <ApplicationRequest />
            </div>
        </div>
    );
}

