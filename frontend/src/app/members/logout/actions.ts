"use server";

import { cookies } from "next/headers";
import { redirect } from "next/navigation";

// 🔹 로그아웃 기능: JWT 삭제 후 로그인 페이지로 리디렉트
export async function logout() {
    const cookieStore = await cookies(); // 쿠키 저장소 가져오기 (비동기)
    cookieStore.delete("token"); // JWT 토큰 삭제
    redirect("/members/login"); // 로그인 페이지로 이동
}
