import { cookies } from 'next/headers';

interface Profile {
  username: string;
  nickname: string;
}


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

export async function fetchProfile(): Promise<Profile | undefined> {
  try {
    const cookieStore = await cookies();
    const token = cookieStore.get("token")?.value;

    if (!token) {
      return undefined;
    }

    const response = await fetch("http://localhost:8080/members/profile", {
      headers: {
        Authorization: `Bearer ${token}`, // 'Bearer ' 추가
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to fetch profile: ${response.statusText}`);
    }

    return await response.json();
  } catch (error) {
    if (error instanceof DOMException && error.name === "AbortError") {
      throw new Error("Request timed out");
    }
    if (error instanceof TypeError) {
      throw new Error("Network or parsing error");
    }

    throw error;
  }
}
