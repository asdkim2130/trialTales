'use client';

import {useSearchParams} from "next/navigation";
import {ApplicationInfo} from "@/app/applications/admin/viewInfo/detailComponents";
import {detailApplicationSample} from "@/app/applications/admin/viewInfo/memberData";
import React from "react";

export default function InfoForm() {
    const searchParams = useSearchParams();
    const applicationId = searchParams.get('applicationId'); // URL에서 campaignId를 가져옴

    console.log("applicationsID", applicationId);

    if (!applicationId) {
        return <div>신청서 ID가 제공되지 않았습니다.</div>;
    }

    const applicationIdNumber = parseInt(applicationId, 10);

    console.log("IdNumber", applicationIdNumber)


    const tempAppSample = detailApplicationSample.find(
        (app) => app.id === applicationIdNumber
    );

    console.log("Found application:", tempAppSample);

    if (!tempAppSample) {
        return <div>신청서를 찾을 수 없습니다.</div>;
    }

    return (
        <div>
            <ApplicationInfo detailAppSample={tempAppSample}/>
        </div>
    );
}