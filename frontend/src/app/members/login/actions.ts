"use server"

import { redirect } from "next/navigation";

export async function 로그인처리(formData: FormData) {
    const 아이디 = formData.get("text");
    const 비밀번호 = formData.get("password");

    if (!아이디 || !비밀번호) {
        console.log("아이디와 비밀번호 모두 필요합니다.");
        return;
    }

    const 입력데이터 = {
        아이디,
        비밀번호,
    };

    console.log(입력데이터);
    redirect('login');
}
