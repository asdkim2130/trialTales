'use client';

import Link from "next/link";
import { AppList } from "@/app/applications/admin/listData";
import styles from "@/app/campaigns/page.module.css";
import {useRouter} from "next/navigation";

export function TableComponent(props: { list: AppList[] }) {
 const router = useRouter();


  return (
    <div>
      <table className="w-full table-auto border-separate border-spacing-0 bg-white rounded-lg shadow-lg max-w-[1000px] mx-auto">
        <thead>
          <tr className="text-center">
            <th className="p-3 text-center"> </th>
            <th className="p-3 text-center">캠페인</th>
            <th className="p-3 text-center">모집 상태</th>
            <th className="p-3 text-center">모집 인원</th>
            <th className="p-3 text-center">시작 날짜</th>
            <th className="p-3 text-center">제출 날짜</th>
            <th className="p-3 text-center">승인 상태</th>
            <th className="p-3 text-center"> </th>
          </tr>
        </thead>
        <tbody>
          {props.list.map((app) => (
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
              <td className="p-3 text-center">
                {app.campaign?.recruitmentLimit}
              </td>
              <td className="p-3 text-center">{app.campaign?.startDate}</td>
              <td className="p-3 text-center">{app.applicationDate}</td>
              <td className="p-3 text-center">{app.appStatus}</td>
              <td className="p-3">
                <div>
                  <Link href={`/applications/admin/viewInfo/?applicationId=${app.id}&campaignId=${app.campaign?.id}`}>
                    <button
                        className={`${styles.button} ${styles.buttonApply}`}
                        onClick={() => router.push(`/applications/admin/viewInfo/?applicationId=${app.id}&campaignId=${app.campaign?.id}`)}
                    >
                      상세조회
                    </button>
                  </Link>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
