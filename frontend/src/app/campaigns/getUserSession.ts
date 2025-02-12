"use server";

import { cookies } from "next/headers";

export async function getUserSession() {
    const token = (await cookies()).get("token")?.value || null;
    return token ? {isLoggedIn: true} : {isLoggedIn: false};
}
