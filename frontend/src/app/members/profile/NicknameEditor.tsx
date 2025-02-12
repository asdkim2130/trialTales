"use client";

import { useState } from "react";
import styles from "./profile.module.css";

interface NicknameEditorProps {
    currentNickname: string;
}

export default function NicknameEditor({ currentNickname }: NicknameEditorProps) {
    const [newNickname, setNewNickname] = useState(currentNickname);
    const [isEditingNickname, setIsEditingNickname] = useState(false);

    const handleNicknameUpdate = () => {
        if (newNickname.trim() === "") return;
        console.log("닉네임 변경:", newNickname);
        setIsEditingNickname(false);
    };

    const handleNicknameFocus = () => {
        setIsEditingNickname(true);
    };

    const handleResetNickname = () => {
        setNewNickname(currentNickname);
        setIsEditingNickname(false);
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
                        newNickname !== currentNickname && newNickname.trim() !== "" ? styles.activeButton : ""
                    }`}
                    disabled={newNickname === currentNickname || newNickname.trim() === ""}
                >
                    저장
                </button>
            </div>
        </div>
    );
}

