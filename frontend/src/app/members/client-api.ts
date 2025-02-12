// 클라이언트에서 서버 액션을 호출하는 API
"use client";

import {deleteAccount, updateNickname} from "@/app/api";

// 닉네임 변경 요청
export async function handleNicknameUpdate(newNickname: string) {
    const cleanedNickname = newNickname.replace(/"/g, ""); // 따옴표 제거
    const success = await updateNickname(cleanedNickname);
    if (success) {
        console.log("닉네임 변경 완료!", cleanedNickname);
        return true;
    } else {
        console.error("닉네임 변경 실패");
        return false;
    }
}

// 계정 삭제 요청
export async function handleAccountDeletion(username: string) {
    const success = await deleteAccount(username);
    if (success) {
        console.log("계정 삭제 완료!");
    } else {
        console.error("계정 삭제 실패");
    }
}
