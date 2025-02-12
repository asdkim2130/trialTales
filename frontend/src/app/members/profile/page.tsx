import { redirect } from "next/navigation";
import {fetchProfile} from "@/app/api";
import NicknameEditor from "./NicknameEditor";
import DeleteAccountInput from "./DeleteAccountInput";
import styles from "./profile.module.css";

export default async function UserProfilePage() {

    const profile = await fetchProfile();

    if (!profile) {
        redirect("/members/login");
    }

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>계정 관리</h1>

            {/* 유저 정보 */}
            <div className={styles.card}>
                <h2 className={styles.sectionTitle}>사용자 정보</h2>

                <div className={styles.inputGroup}>
                    <label>아이디</label>
                    <input type="text" value={profile.username} className={styles.input} disabled />
                </div>

                {/* 닉네임 변경 UI */}
                <NicknameEditor currentNickname={profile.nickname} />
            </div>

            {/* 회원 탈퇴 */}
            <div className={styles.card}>
                <h2 className={styles.sectionTitle}>회원 탈퇴</h2>
                <p className={styles.warning}>
                    계정을 삭제하려면, 아이디를 정확히 입력해야 합니다.
                </p>

                {/* 회원 탈퇴 입력 UI */}
                <DeleteAccountInput username={profile.username} />
            </div>
        </div>
    );
}
