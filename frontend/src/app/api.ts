"use server";

import { cookies } from "next/headers";
import { redirect } from "next/navigation";

interface Profile {
  username: string;
  nickname: string;
}

// 로그인 처리
export async function signIn(username: string, password: string): Promise<string | null> {
  try {
    const response = await fetch("http://localhost:8080/members/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
      console.error("로그인 실패:", await response.text());
      return null;
    }

    const data = await response.json();
    return data.accessToken || data.token; // 백엔드 응답 확인 후 변경
  } catch (error) {
    console.error("로그인 요청 중 에러 발생:", error);
    return null;
  }
}

// 프로필 조회 (서버에서 실행)
export async function fetchProfile(): Promise<Profile | undefined> {
  try {
    const token = (await cookies()).get("token")?.value;

    if (!token) {
      return undefined;
    }

    const response = await fetch("http://localhost:8080/members/profile", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to fetch profile: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("프로필 조회 중 에러 발생:", error);
    return undefined;
  }
}

// 닉네임 변경 (서버 액션)
export async function updateNickname(newNickname: string): Promise<boolean> {
  try {
    const token = (await cookies()).get("token")?.value;
    if (!token) {
      console.error("인증 정보 없음: 닉네임 변경 불가");
      return false;
    }

    const cleanedNickname = newNickname.replace(/"/g, ""); // 따옴표 제거

    const response = await fetch("http://localhost:8080/members/profile", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(cleanedNickname),
    });

    if (!response.ok) {
      console.error("닉네임 변경 실패:", await response.text());
      return false;
    }

    console.log("닉네임 변경 성공!");
    return true;
  } catch (error) {
    console.error("닉네임 변경 중 에러 발생:", error);
    return false;
  }
}

// 계정삭제 (서버 액션)
export async function deleteAccount(username: string): Promise<boolean> {
  try {
    const token = (await cookies()).get("token")?.value;
    if (!token) {
      console.error("인증 정보 없음: 계정 삭제 불가");
      return false;
    }

    const response = await fetch(`http://localhost:8080/members/profile?username=${username}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      console.error("계정 삭제 실패:", await response.text());
      return false;
    }

    console.log("계정 삭제 성공!");

    // 1️⃣ 쿠키 삭제
    (await cookies()).delete("token");

    // 2️⃣ 클라이언트에서 리디렉트 처리하도록 `true` 반환
    return true;
  } catch (error) {
    console.error("계정 삭제 중 에러 발생:", error);
    return false;
  }
}
