import { campaignSample } from "../tempData";
import { appData } from "./appData";

interface Applications {
  id: number;
  applicationDate: string;
  appStatus: "PENDING" | "APPROVED";
  campaign: Campaigns | null;
}
export interface Campaigns {
  id: number;
  imageSrc: string;
  campaignName: string;
  startDate: string;
  status: string;
  recruitmentLimit: number;
}

interface AppList extends Applications {
  campaign: Campaigns | null;
}

export const appList: AppList[] = appData.map((app) => {
  const campaignData = campaignSample.find(
    (campaign) => campaign.id === app.id,
  );

  return {
    ...app,
    campaign: campaignData || null,
  };
});
