"use client";

import {useEffect, useState} from "react";
import styles from "./profile.module.css";
import {handleNicknameUpdate} from "@/app/members/client-api";

interface NicknameEditorProps {
    currentNickname: string;
}

export default function NicknameEditor({ currentNickname }: NicknameEditorProps) {
    // 따옴표가 있으면 제거한 상태로 초기값 설정
    const cleanedNickname = currentNickname.replace(/"/g, "");
    const [newNickname, setNewNickname] = useState(cleanedNickname);
    const [isEditingNickname, setIsEditingNickname] = useState(false);
    const [savedNickname, setSavedNickname] = useState(cleanedNickname);

    useEffect(() => {
        // 닉네임이 바뀌었을 때 따옴표 없이 업데이트
        setNewNickname(cleanedNickname);
        setSavedNickname(cleanedNickname);
    }, [currentNickname]);

    const handleUpdate = async () => {
        if (newNickname.trim() === "" || newNickname === savedNickname) return;

        const success = await handleNicknameUpdate(newNickname);
        if (success) {
            setSavedNickname(newNickname); // 변경된 닉네임 저장
            setIsEditingNickname(false); // 입력 필드 비활성화
        }
    };

    const handleReset = () => {
        setNewNickname(savedNickname); // 기존 닉네임으로 복원
        setIsEditingNickname(false); // 입력 필드 비활성화
    };

    return (
        <div className={styles.inputGroup}>
            <label>닉네임 (변경하려면 새 닉네임을 입력하세요)</label>
            <div className={styles.nicknameContainer}>
                <div className={styles.inputWrapper}>
                    <input
                        type="text"
                        value={newNickname}
                        onChange={(e) => setNewNickname(e.target.value)}
                        onFocus={() => setIsEditingNickname(true)} // 입력 시 활성화
                        className={`${styles.input} ${isEditingNickname ? styles.activeInput : ""}`}
                        placeholder="새 닉네임 입력"
                    />
                    {isEditingNickname && (
                        <button className={styles.resetButton} onClick={handleReset}>
                            ↺
                        </button>
                    )}
                </div>
                <button
                    onClick={handleUpdate}
                    className={`${styles.saveButton} ${newNickname.trim() !== "" && newNickname !== savedNickname ? styles.activeButton : ""}`}
                    disabled={newNickname.trim() === "" || newNickname === savedNickname}
                >
                    저장
                </button>
            </div>
        </div>
    );
}
