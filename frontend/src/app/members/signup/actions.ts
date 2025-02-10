"use server"

import { redirect } from "next/navigation";

export async function 회원가입처리(formData: FormData) {
    const username = formData.get("username");
    const password = formData.get("password");
    const nickname = formData.get("nickname");

    // 필수 항목 체크 (아이디, 비밀번호)
    if (!username || !password) {
        console.error("아이디와 비밀번호는 필수 항목입니다.");
        return;
    }

    const 가입데이터 = {
        username,
        password,
        nickname, // 선택값: 입력하지 않으면 백엔드에서 username을 대체할 수 있음
    };

    console.log("회원가입 데이터:", 가입데이터);

    redirect("/members/login");
}
