"use server";

import { cookies } from "next/headers";
import { signIn } from "../../api";
import { redirect } from "next/navigation";

export async function 로그인처리(formData: FormData) {
    const 아이디 = formData.get("text") as string;
    const 비밀번호 = formData.get("password") as string;

    if (!아이디 || !비밀번호) {
        console.error("아이디와 비밀번호 모두 필요합니다.");
        return;
    }

    // 백엔드 로그인 API 호출
    const token = await signIn(아이디, 비밀번호);

    if (!token) {
        console.error("로그인 실패: 잘못된 아이디 또는 비밀번호");
        return;
    }

    // Next.js의 cookies() API를 이용해 서버 사이드에서 쿠키 저장 (await 제거)
    (await cookies()).set("token", token, {
        httpOnly: true,  // 클라이언트 JS에서 접근 불가
        secure: process.env.NODE_ENV === "production", // HTTPS 환경에서만 사용
        path: "/",
        maxAge: 60 * 60 * 24 * 7, // 1주일 유지
    });

    console.log("로그인 성공, 토큰 저장됨");
    redirect("/");
}
