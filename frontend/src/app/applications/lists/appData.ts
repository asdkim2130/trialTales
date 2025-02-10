interface AppData {
  id: number;
  applicationDate: string;
  appStatus: "PENDING" | "APPROVED";
}
export const appData = [
  { id: 1, applicationDate: "2025-02-07", appStatus: "APPROVED" },
  { id: 2, applicationDate: "2025-02-08", appStatus: "APPROVED" },
  { id: 3, applicationDate: "2025-02-09", appStatus: "APPROVED" },
  { id: 4, applicationDate: "2025-02-10", appStatus: "PENDING" },
  { id: 5, applicationDate: "2025-02-11", appStatus: "PENDING" },
  { id: 6, applicationDate: "2025-02-12", appStatus: "PENDING" },
  { id: 7, applicationDate: "2025-02-13", appStatus: "PENDING" },
  { id: 8, applicationDate: "2025-02-14", appStatus: "PENDING" },
];
