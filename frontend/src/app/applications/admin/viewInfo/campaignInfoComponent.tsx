'use client';

import {useSearchParams} from "next/navigation";
import {campaignSample} from "@/app/applications/tempData";
import {CampaignInfo, ImageBox} from "@/app/applications/new/campaignInfoComponent";
import React from "react";

export default function ClientPage() {
    const searchParams = useSearchParams();
    const campaignId = searchParams.get('campaignId'); // URL에서 campaignId를 가져옴

    console.log("campaignID", campaignId);

    if (!campaignId) {
        return <div>캠페인 ID가 제공되지 않았습니다.</div>;
    }

    const campaignIdNumber = parseInt(campaignId, 10);

    console.log("IdNumber", campaignIdNumber)


    const tempCampaignSample = campaignSample.find(
        (campaign) => campaign.id === campaignIdNumber
    );

    console.log("Found campaign:", tempCampaignSample);

    if (!tempCampaignSample) {
        return <div>캠페인을 찾을 수 없습니다.</div>;
    }

    return (
        <div>
            <ImageBox campaignSample={tempCampaignSample} />
            <CampaignInfo campaignSample={tempCampaignSample} />
        </div>
    );
}