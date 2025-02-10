"use client";

import { appList } from "./listData";
import { useState } from "react";
import { ListTable } from "@/app/applications/lists/listTable";

// interface FilterProps {
//     filter: "All" | "PENDING" | "APPROVED";
//     setFilter: (value: "All" | "PENDING" | "APPROVED") => void;
// }
//
// export const Filter: React.FC<FilterProps> = ({ filter, setFilter }) => {
//     return (
//         <div className="p-6">
//             {/* 필터 메뉴 */}
//             <div className="mb-4">
//                 <button
//                     onClick={() => setFilter("All")}
//                     className={`px-4 py-2 mr-2 ${filter === "All" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
//                 >
//                     All
//                 </button>
//                 <button
//                     onClick={() => setFilter("PENDING")}
//                     className={`px-4 py-2 mr-2 ${filter === "PENDING" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
//                 >
//                     PENDING
//                 </button>
//                 <button
//                     onClick={() => setFilter("APPROVED")}
//                     className={`px-4 py-2 mr-2 ${filter === "APPROVED" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
//                 >
//                     APPROVED
//                 </button>
//             </div>
//         </div>
//     );
// };

export default function ApplicationListPage({ filter }: { filter: "All" | "PENDING" | "APPROVED" }) {

    const filteredList = appList.filter((app) =>
        filter === "All" ? true : app.appStatus === filter
    );
    return (
        <div>
            {/*<Filter filter={filter} setFilter={setFilter} />*/}
            <ListTable list={filteredList} />
        </div>
    );
}


