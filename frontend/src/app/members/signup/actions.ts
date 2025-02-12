import {redirect} from "next/navigation";

export async function 회원가입처리(formData: FormData) {
    const username = formData.get("username");
    const password = formData.get("password");
    const nickname = formData.get("nickname");

    if (!username || !password) {
        console.error("아이디와 비밀번호는 필수 항목입니다.");
        return;
    }

    const 가입데이터 = { username, password, nickname };

    const response = await fetch("http://localhost:8080/members/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(가입데이터),
    });

    if (!response.ok) {
        console.error("회원가입 실패:", await response.text());
        return;
    }

    console.log("회원가입 성공!");
    return redirect("/members/login");
}
