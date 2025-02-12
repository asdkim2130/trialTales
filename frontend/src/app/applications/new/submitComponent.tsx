"use server";

//제출 입력처리
export async function submitApplication(formData: FormData) {
    const rawFormData = {
        email: formData.get("email"),
        name: formData.get("snsUrl"),
    };

    // 서버에서 실제 데이터 처리 (예: DB 저장)
    console.log("Received Data:", rawFormData);

    return { success: true, message: "제출완료! 당첨자는 개별 연락드립니다." };
}