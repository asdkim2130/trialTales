"use client";

import { appList } from "./listData";
import { useSearchParams } from "next/navigation";
import { useState, useEffect } from "react";
import { ListTableComponent } from "@/app/applications/admin/listTableComponent";

export default function ApplicationListPage() {
  const [filteredList, setFilteredList] = useState(appList);
  const searchParams = useSearchParams();
  const filter =
    (searchParams.get("status") as "All" | "PENDING" | "APPROVED") || "All";

  useEffect(() => {
    console.log("현재 필터 값:", filter); // filter 값 확인 (디버깅)
    console.log("appList 원본 데이터:", appList);

    const filtered = appList.filter((app) =>
      filter === "All" ? true : app.appStatus === filter,
    );

    setFilteredList(filtered);
  }, [filter]);

  return (
    <div>
      <ListTableComponent list={filteredList} />
    </div>
  );
}
