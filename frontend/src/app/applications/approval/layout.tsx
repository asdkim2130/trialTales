"use client";

import { useState } from "react";

export default function Layout({ children }: { children: React.ReactNode }) {
  const [filter, setFilter] = useState<"All" | "PENDING" | "APPROVED">("All");

  return (
    <div>
      {/*네비게이션바*/}
      <nav className="bg-gray-800 text-white p-4 max-w-[1000px] mx-auto rounded-2xl mt-4">
        <div className="flex justify-between items-center">
          <div className="text-lg font-bold">Application Dashboard</div>
          <div className="flex space-x-4">
            <button
              onClick={() => setFilter("All")}
              className={`px-4 py-2 ${filter === "All" ? "bg-blue-500 rounded hover:scale-105 hover:shadow-lg active:scale-95" : "bg-gray-500 rounded hover:scale-105 hover:shadow-lg active:scale-95"}`}
            >
              All
            </button>
            <button
              onClick={() => setFilter("PENDING")}
              className={`px-4 py-2 ${filter === "PENDING" ? "bg-blue-500 rounded hover:scale-105 hover:shadow-lg active:scale-95" : "bg-gray-500 rounded hover:scale-105 hover:shadow-lg active:scale-95"}`}
            >
              PENDING
            </button>
            <button
              onClick={() => setFilter("APPROVED")}
              className={`px-4 py-2 ${filter === "APPROVED" ? "bg-blue-500 rounded hover:scale-105 hover:shadow-lg active:scale-95" : "bg-gray-500 rounded hover:scale-105 hover:shadow-lg active:scale-95"}`}
            >
              APPROVED
            </button>
          </div>
        </div>
      </nav>
      {/* 필터링된 데이터를 자식 컴포넌트로 전달 */}
      <div className="p-6">{children}</div>
    </div>
  );
}
