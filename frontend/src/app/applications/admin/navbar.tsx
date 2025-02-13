"use client";

import { usePathname, useSearchParams, useRouter } from "next/navigation";
import Link from "next/link";

export default function Navbar() {
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const currentStatus = searchParams.get("status") || "All";
  console.log(`pathname: ${pathname}`)
  console.log(pathname.endsWith("/viewInfo"))

  const handleNavigation = (status: "All" | "PENDING" | "APPROVED") => {
    router.push(`${pathname}?status=${status}`);
  };

  return (
    <nav className="bg-gray-800 text-white p-4 max-w-[1000px] mx-auto rounded-2xl mt-4 h-20">
      <div className="flex justify-between items-center h-full">
        <div className="text-lg font-bold">
          <Link href="/applications/admin">
            [관리자모드] 캠페인 신청 현황
          </Link>
        </div>
        <div className="flex space-x-4">
          {!pathname.endsWith("viewInfo") && ["All", "PENDING", "APPROVED"].map((status) => (
              <button
              key={status}
              onClick={() =>
                handleNavigation(status as "All" | "PENDING" | "APPROVED")
              }
              className={`px-4 py-2 ${
                currentStatus === status
                  ? "bg-blue-500 rounded hover:scale-105 hover:shadow-lg active:scale-95"
                  : "bg-gray-500 rounded hover:scale-105 hover:shadow-lg active:scale-95"
              }`}
            >
              {status}
            </button>
          ))}
        </div>
      </div>
    </nav>
  );
}
