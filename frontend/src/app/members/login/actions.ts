"use server";

import { cookies } from "next/headers";
import { signIn } from "../../api";
import { redirect } from "next/navigation";

export async function 로그인처리(formData: FormData) {
    const 아이디 = formData.get("text");
    const 비밀번호 = formData.get("password");

    if (!아이디 || !비밀번호) {
        console.log("아이디와 비밀번호 모두 필요합니다.");
        return;
    }

    const token = await signIn();
    const cookieStore = await cookies();
    cookieStore.set("token", token, {
        httpOnly: true,
    });

    console.log("로그인 성공, 토큰 저장됨");
    redirect("/");
}