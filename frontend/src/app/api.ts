import { cookies } from 'next/headers';

interface Profile {
  // 생략
}

export async function signIn(): Promise<string> {
  // API 서버에 로그인 요청 fetch() 생략

  // JWT return
  return 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c';
}

export async function fetchProfile(): Promise<Profile | undefined> {
  try {
    const cookieStore = await cookies();
    const cookie = cookieStore.get('token');
    const token = cookie?.value;
    if (!token) {
      return undefined;
    }

    // const response = await fetch('http://localhost:3000/api/me', {
    //   headers: {
    //     Authorization: token,
    //   },
    // });
    // if (!response.ok) {
    //   throw new Error(`Failed to fetch profile: ${response.statusText}`);
    // }

    // return await response.json();
    return { name: 'test' };
  } catch (error) {
    // 요청 타임아웃의 경우
    if (error instanceof DOMException && error.name === 'AbortError') {
      throw new Error('Request timed out');
    }
    // 네트워크 혹은 JSON 파싱 에러의 경우
    if (error instanceof TypeError) {
      throw new Error('Network or parsing error');
    }

    // 그 외의 예외 처리
    throw error;
  }
}
