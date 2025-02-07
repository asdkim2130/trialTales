"use client";

import { useState } from "react";
import { dummyUser } from "./dummyUser"; // 더미 데이터 가져오기
import styles from "./profile.module.css";

export default function UserProfilePage() {
    const [user, setUser] = useState(dummyUser);
    const [newNickname, setNewNickname] = useState(user.nickname);
    const [isEditingNickname, setIsEditingNickname] = useState(false);
    const [deleteInput, setDeleteInput] = useState(""); // 회원 탈퇴 확인용 입력 필드
    const [isEditingDelete, setIsEditingDelete] = useState(false);

    // 닉네임 변경 핸들러
    const handleNicknameUpdate = () => {
        if (newNickname.trim() === "") return;
        setUser({ ...user, nickname: newNickname });
        setIsEditingNickname(false);
    };

    // 닉네임 입력창 클릭 시 편집 모드
    const handleNicknameFocus = () => {
        setIsEditingNickname(true);
    };

    // 닉네임 변경 취소
    const handleResetNickname = () => {
        setNewNickname(user.nickname);
        setIsEditingNickname(false);
    };

    // 삭제 버튼 활성화 조건 (유저네임이 정확히 일치해야 함)
    const isUsernameMatch = deleteInput === user.username;

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>계정 관리</h1>

            {/* 유저 정보 */}
            <div className={styles.card}>
                <h2 className={styles.sectionTitle}>사용자 정보</h2>

                <div className={styles.inputGroup}>
                    <label>아이디</label>
                    <input type="text" value={user.username} className={styles.input} disabled />
                </div>

                {/* 닉네임 변경 UI */}
                <div className={styles.inputGroup}>
                    <label>닉네임 (변경하려면 새 닉네임을 입력하세요)</label>
                    <div className={styles.nicknameContainer}>
                        <div className={styles.inputWrapper}>
                            <input
                                type="text"
                                value={newNickname}
                                onChange={(e) => setNewNickname(e.target.value)}
                                onFocus={handleNicknameFocus}
                                className={`${styles.input} ${isEditingNickname ? styles.activeInput : ""}`}
                                placeholder="새 닉네임 입력"
                            />
                            {isEditingNickname && (
                                <button className={styles.resetButton} onClick={handleResetNickname}>
                                    ↺
                                </button>
                            )}
                        </div>

                        <button
                            onClick={handleNicknameUpdate}
                            className={`${styles.saveButton} ${
                                newNickname !== user.nickname && newNickname.trim() !== "" ? styles.activeButton : ""
                            }`}
                            disabled={newNickname === user.nickname || newNickname.trim() === ""}
                        >
                            저장
                        </button>
                    </div>
                </div>
            </div>

            {/* 회원 탈퇴 */}
            <div className={styles.card}>
                <h2 className={styles.sectionTitle}>회원 탈퇴</h2>
                <p className={styles.warning}>
                    계정을 삭제하려면, 아이디를 정확히 입력해야 합니다.
                </p>

                <div className={styles.inputGroup}>
                    <div className={styles.inputWrapper}>
                        <input
                            type="text"
                            value={deleteInput}
                            onChange={(e) => setDeleteInput(e.target.value)}
                            onFocus={() => setIsEditingDelete(true)}
                            placeholder="아이디 입력"
                            className={`${styles.input} ${isEditingDelete ? styles.activeInput : ""}`}
                        />
                        {isEditingDelete && deleteInput !== "" && (
                            <button className={styles.resetButton} onClick={() => setDeleteInput("")}>
                                ↺
                            </button>
                        )}
                    </div>
                </div>

                <button
                    className={`${styles.deleteButton} ${
                        isUsernameMatch ? styles.activeDeleteButton : ""
                    }`}
                    disabled={!isUsernameMatch}
                >
                    계정 삭제
                </button>
            </div>
        </div>
    );
}
